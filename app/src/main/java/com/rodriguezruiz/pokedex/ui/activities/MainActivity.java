package com.rodriguezruiz.pokedex.ui.activities;

import static com.rodriguezruiz.pokedex.utils.Constants.TAG;
import static com.rodriguezruiz.pokedex.utils.Constants.SETTING_LANGUAGE;
import static com.rodriguezruiz.pokedex.utils.Constants.SETTING_LANGUAGE_DEFAULT;
import static com.rodriguezruiz.pokedex.utils.Constants.SETTING_DELETE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.rodriguezruiz.pokedex.R;
import com.rodriguezruiz.pokedex.data.model.PokemonData;
import com.rodriguezruiz.pokedex.data.model.PokemonResponse;
import com.rodriguezruiz.pokedex.data.repository.PokemonRepository;
import com.rodriguezruiz.pokedex.listener.OnPokemonLoadedListener;
import com.rodriguezruiz.pokedex.listener.OperationCallBack;
import com.rodriguezruiz.pokedex.listener.PokemonCallBack;
import com.rodriguezruiz.pokedex.listener.PokemonListCallback;
import com.rodriguezruiz.pokedex.ui.adapter.PokedexAdapter;
import com.rodriguezruiz.pokedex.data.api.GetApiPokedex;
import com.rodriguezruiz.pokedex.databinding.ActivityMainBinding;
import com.rodriguezruiz.pokedex.data.model.PokedexData;
import com.rodriguezruiz.pokedex.listener.OnPokedexLoadedListener;
import com.rodriguezruiz.pokedex.ui.fragment.DetailPokemonFragment;
import com.rodriguezruiz.pokedex.viewmodel.PokedexViewModel;
import com.rodriguezruiz.pokedex.viewmodel.PokemonViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private NavController navController = null;
    private ActivityMainBinding binding;
    private FirebaseAuth firebaseAuth;
    private Toolbar toolbar;
    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private PokedexAdapter listaPokemonAdapter;
    private PokemonRepository repository;
    private ArrayList<PokemonData> listPokemon;
    private ArrayList<PokedexData> listPokedex;
    private PokedexViewModel pokedexViewModel;
    private PokemonViewModel pokemonViewModel;
    private ProgressBar progressBar;
    private CountDownLatch latch = new CountDownLatch(2);  // Para esperar a 2 tareas

    private boolean isPossibleDeletePokemon = false;
    public String userUID;
    //public boolean isPossibleDeletePokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflar el binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inicializa el ViewMOdel
        pokemonViewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        pokedexViewModel = new ViewModelProvider(this).get(PokedexViewModel.class);

        // Recogemos el UID del usuario para acceder a sus Pokemons
        userUID = MyApplication.getUserUID();
        Log.i(TAG, "MainActivity -> userUID: " + userUID);

        // Inicializa la interfaz de usuario
        initializeUI();

        // Instanciamos el repositorio para acceder a la BD y empezamos a cargar API y BD documents
        repository = new PokemonRepository(userUID);        // Instanciamos PokemonRepository pasandole el UID del usuario

        // Verificar que el userUID se encuentra dado de alta en la base de datos
        repository.checkTrainerExist(userUID);
        
        // Llamada al método que sincroniza la carga y la actualización
        loadDataAndUpdatePokedex();
    }

    private void loadDataAndUpdatePokedex() {
        // Ejecuta la carga de Pokémon capturados en un hilo separado
        new Thread(() -> {
            loadCapturedPokemonFromFirestore();  // Carga desde Firestore
            latch.countDown();  // Marca la tarea como terminada
        }).start();

        // Ejecuta la carga de la Pokedex desde la API en otro hilo separado
        new Thread(() -> {
            loadPokedexFromApi();  // Carga desde la API
            latch.countDown();  // Marca la tarea como terminada
        }).start();

        // Hilo que espera la finalización de ambas tareas para ejecutar la actualización
        new Thread(() -> {
            try {
                latch.await();  // Espera que ambas tareas terminen
                runOnUiThread(this::updatePokedexWithCapturedPokemons);  // Actualiza la UI en el hilo principal
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void initializeUI() {
        // Cargar el lenguaje almacenado en la SharedPreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String languageCode = preferences.getString(SETTING_LANGUAGE, SETTING_LANGUAGE_DEFAULT);
        setLocale(languageCode);

        // Lee el estado del DELETE_POKEMON (Si aún no existe se asigna false -> NO se pueden borrar)
        isPossibleDeletePokemon = preferences.getBoolean(SETTING_DELETE, false);

        // Configura la Toolbar
        configureToolBar();

        // Configurar el controlador de navegacion para los fragment
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
            NavigationUI.setupActionBarWithNavController(this, navController);
        } else {
            Log.e(TAG, "NavHostFragment no encontrado");
        }
        binding.bottomNavigation.setOnItemSelectedListener(this::selectedBottomMenu);

    }

    public void loadCapturedPokemonFromFirestore() {
        Log.i(TAG, "loadCapturedPokemonFromFirestore -> Estoy dentro");
        if (!isNetworkAvailable()) {
            Log.e(TAG, "No hay conexión a Internet");
            Toast.makeText(this, R.string.no_conection, Toast.LENGTH_LONG).show();
            return;
        }
        // *****
        Log.i(TAG, "MainActivity -> Leyendo los pokemoncapturados en Firestore");
        readAllPokemon();
        Log.i(TAG, "MainActivity -> De regreso de readAllPokemon()");
        Log.i(TAG, "MainActivity -> Quita el esperador...");
    }

    public void loadPokedexFromApi() {
        Log.i(TAG, "loadPokedexFromApi -> Estoy dentro");
        if (!isNetworkAvailable()) {
            Toast.makeText(this, R.string.no_conection, Toast.LENGTH_LONG).show();
            return;
        }

        GetApiPokedex getApiPokedex = new GetApiPokedex();
        getApiPokedex.gettingListPokedex(new OnPokedexLoadedListener() {
            @Override
            public void onLoaded(ArrayList<PokedexData> pokedex) {
                if (pokedex != null && !pokedex.isEmpty()) {
                    Log.d(TAG, "loadPokedexFromApi -> Datos leido correctamente: " + pokedex.size() + " Pokemon. SALIENDO");
                    // Los datos devueltos por la API se agregan al adaptador
                    pokedexViewModel.setPokedexData(pokedex);        // Almacena los datos consumidos en el ViewModel
                } else {
                    Log.e(TAG, "La lista de Pokemon está vacía o es null");
                }
            }
        });
    }

    public void updatePokedexWithCapturedPokemons() {
        Log.i(TAG, "updatePokedexWithCapturedPokemons -> Estoy dentro");

        // Prepara los ViewModel para cargarlos
        ArrayList<PokemonData> capturedPokemons = pokemonViewModel.getPokemonData().getValue();
        ArrayList<PokedexData> pokedexList = pokedexViewModel.getPokedexData().getValue();

        if (capturedPokemons != null ) {
            if (pokedexList != null) {
                for (PokemonData captured : capturedPokemons) {
                    for (PokedexData pokedex : pokedexList) {
                        if (captured.getId().equals(pokedex.getId())) {
                            pokedex.setCaptured(true);
                            break; // Corto el bucle interno para que pase al siguiente capturado
                        }
                    }
                }
                listaPokemonAdapter.notifyDataSetChanged();
            }
        }
    }

    public void loadPokemonFromApi(String IdPokemon, PokemonCallBack callback) {
        Log.i(TAG, "loadPokemonFromApi -> Estoy dentro");
        if (!isNetworkAvailable()) {
            Log.e(TAG, "No hay conexión a Internet");
            return;
        }

        GetApiPokedex getApiPokedex = new GetApiPokedex();
        getApiPokedex.gettingPokemonDetail(IdPokemon, new OnPokemonLoadedListener() {
            @Override
            public void onLoaded(PokemonResponse pokemonResponse) {
                if (pokemonResponse != null) {
                    Log.d(TAG, "loadPokemonFromApi -> Datos obtenidos correctamente: " + pokemonResponse.getName() + " SALIENDO");
                    callback.onSuccess(pokemonResponse);  // Llama al callback con el resultado
                } else {
                    Log.e(TAG, "loadPokemonFromApi -> No se encontraron datos para: " + IdPokemon);
                    callback.onSuccess(null);  // Devuelve null si no hay datos
                }
            }
        });
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @SuppressLint("NonConstantResourceId")
    private boolean selectedBottomMenu(MenuItem menuItem) {
        int selectedOptions = menuItem.getItemId();

        if (selectedOptions == R.id.navigation_captured) {
            navController.navigate(R.id.capturedFragment);
        } else if (selectedOptions == R.id.navigation_pokedex) {
            navController.navigate(R.id.pokedexFragment);
        } else if (selectedOptions == R.id.navigation_settings) {
            navController.navigate(R.id.settingsFragment);
        } else {
            return false; // Si no se maneja, devuelve false
        }
        return true;
    }

    private void configureToolBar() {
        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    public void setToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    public void userClickedListPokemon(PokedexData pokemonToCapture) {
        // Actualiza el estado tambien en el array del ViewModel
        ArrayList<PokedexData> pokedexDataList = pokedexViewModel.getPokedexData().getValue();
        if (pokedexDataList != null) {
            for (PokedexData data : pokedexDataList) {
                if (data.getId() == pokemonToCapture.getId()) {
                    data.setCaptured(true);
                    break;
                }
            }
            pokedexViewModel.setPokedexData(pokedexDataList);
        }
        // Obtiene el ID para consumir la API y cargar en una clase PokemonData los datos del pokemon seleccionado
        String IdPokemonToCapturate = pokemonToCapture.getId();
        loadPokemonFromApi(IdPokemonToCapturate, new PokemonCallBack() {
            @Override
            public void onSuccess(PokemonResponse pokemonGetted) {
                // Se almacena en la base de datos
                if (pokemonGetted != null) {
                    // Se convierte el objeto consumido a la clase PokemonData
                    List<String> types = new ArrayList<>();
                    for (PokemonResponse.Type type : pokemonGetted.getTypes()) {
                        types.add(type.getType().getName());
                    }
                    PokemonData newPokemon = new PokemonData(
                            pokemonGetted.getId() + "",
                            pokemonGetted.getName(),
                            pokemonGetted.getHeight(),
                            pokemonGetted.getWeight(),
                            pokemonGetted.getSprites().getFrontDefault(),
                            types
                    );
                    String msg = newPokemon.getId() + " | " + newPokemon.getName() + " | " + newPokemon.getHeight() + " | " + newPokemon.getWeight();
                            Log.i(TAG, "userclickedpokemon -> Datos Pokemon capturado: " + msg);
                    // Almacenamos en BD Firestore
                    addPokemon(newPokemon);
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, "Problemas al consumir pokemon");
            }
        });
    }

    public void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
    }

    public void logout() {
        // Cerrar sesión de Firebase
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, R.string.sesion_finalizada, Toast.LENGTH_SHORT).show();

        // Redirigir a la actividad de inicio de sesión
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void addPokemon(PokemonData newPokemon) {
        repository.addPokemon(userUID, newPokemon, new OperationCallBack() {
            @Override
            public void onSuccess() {
                // Se añadió bien a la BD -> SE añade al ViewModel y RecyclerView)
                pokemonViewModel.getPokemonData().getValue().add(newPokemon);
                pokemonViewModel.setPokemonData(pokemonViewModel.getPokemonData().getValue()); // Notifica el cambio
                Toast.makeText(MainActivity.this, R.string.captured_pokemons, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deletePokemon(String pokemonId) {
        repository.deletePokemon(userUID, pokemonId, new OperationCallBack() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, R.string.msgDeleted, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(MainActivity.this, R.string.error + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void readAllPokemon() {
        repository.getAllPokemons(userUID, new PokemonListCallback() {
            @Override
            public void onSuccess(ArrayList<PokemonData> capturedListPokemons) {
                Log.d(TAG, "readAllPokemons -> Pokemons recibidos desde BD: " + capturedListPokemons.size());
                int numberCaptured = capturedListPokemons.size();
                if (numberCaptured < 1) {
                    Toast.makeText(MainActivity.this, R.string.any_captured, Toast.LENGTH_SHORT).show();
                    return;
                }
                // Se almacena en el ViewModel para su muestreo en recyclerview
                pokemonViewModel.setPokemonData(capturedListPokemons);

                Log.i(TAG, "readAllPokemon -> HA LEIDO POKEMONS CAPTURADOS");
                String msg = getString(R.string.have) + numberCaptured + getString(R.string.captured_pokemons);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Exception e) {
                //Log.e(TAG + "=>MAIN_ACTIVITY", "Error getting pokemons: ", e);
                String msg_error = R.string.error + e.getMessage();
                Toast.makeText(MainActivity.this, msg_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void userClickedDetailPokemon(PokemonData itemPokemonData) {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        Bundle bundle = new Bundle();
        bundle.putParcelable("pokemon", itemPokemonData);
        navController.navigate(R.id.detailPokemonFragment);
    }

    public boolean getPermissionToDelete() {
        return isPossibleDeletePokemon;
    }
}