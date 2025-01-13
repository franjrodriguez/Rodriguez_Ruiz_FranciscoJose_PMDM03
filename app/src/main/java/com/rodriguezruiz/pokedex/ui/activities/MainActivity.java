package com.rodriguezruiz.pokedex.ui.activities;

import static com.rodriguezruiz.pokedex.utils.Constants.TAG;
import static com.rodriguezruiz.pokedex.utils.Constants.LANGUAGE;
import static com.rodriguezruiz.pokedex.utils.Constants.LANGUAGE_DEFAULT;

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
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.rodriguezruiz.pokedex.R;
import com.rodriguezruiz.pokedex.data.model.PokemonData;
import com.rodriguezruiz.pokedex.data.repository.PokemonRepository;
import com.rodriguezruiz.pokedex.listener.PokemonListCallback;
import com.rodriguezruiz.pokedex.ui.adapter.PokedexAdapter;
import com.rodriguezruiz.pokedex.data.api.GetApiPokedex;
import com.rodriguezruiz.pokedex.databinding.ActivityMainBinding;
import com.rodriguezruiz.pokedex.data.model.PokedexData;
import com.rodriguezruiz.pokedex.listener.OnPokedexLoadedListener;
import com.rodriguezruiz.pokedex.viewmodel.PokedexViewModel;

import java.util.ArrayList;
import java.util.Locale;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private NavController navController = null;
    private ActivityMainBinding binding;
    private FirebaseAuth firebaseAuth;
    private Toolbar toolbar;
    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private PokedexAdapter listaPokemonAdapter;
    private PokedexViewModel pokedexViewModel;
    private PokemonRepository repository;
    private String userUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflar el binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Recogemos el UID del usuario para acceder a sus Pokemons
        userUID = MyApplication.getUserUID();

        pokedexViewModel = new ViewModelProvider(this).get(PokedexViewModel.class);
        repository = new PokemonRepository();        // Instanciamos PokemonRepository pasandole el UID del usuario

        // Inicializa la interfaz de usuario
        initializeUI();

        // Muestra un indicador de carga
        showLoadingIndicator();

        // Cargar los datos desde Firestore y la API de forma asincrona
        loadCapturedPokemonFromFirestore();
        loadPokedexFromApi();

        hideLoadingIndicator();
    }

    private void showLoadingIndicator() {
        // *****
        Log.i(TAG, "MainActivity -> Mostrando el indicador de carga");
        // Obtener referencia al indicador de carga
        View loadingIndicator = binding.loadingIndicator;

        // Mostrar el indicador
        loadingIndicator.setVisibility(View.VISIBLE);

        // Deshabilitar interacciones mientras se carga
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        );
    }

    private void hideLoadingIndicator() {
        Log.i(TAG, "MainActivity -> Ocultando el indicador de carga");

        // Obtener referencia al indicador de carga
        View loadingIndicator = binding.loadingIndicator;

        // Ocultar el indicador
        loadingIndicator.setVisibility(View.GONE);

        // Rehabilitar interacciones
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void loadPokedexFromApi() {
        if (!isNetworkAvailable()) {
            Log.e(TAG, "No hay conexión a Internet");
            Toast.makeText(this, R.string.no_conection, Toast.LENGTH_LONG).show();
            hideLoadingIndicator();
            return;
        }
        GetApiPokedex getApiPokedex = new GetApiPokedex();
        getApiPokedex.gettingListPokedex(new OnPokedexLoadedListener() {
            @Override
            public void onLoaded(ArrayList<PokedexData> pokedex) {
                if (pokedex != null && !pokedex.isEmpty()) {
                    Log.d(TAG, "loadPokedexFromApi -> Datos leido correctamente: " + pokedex.size() + " Pokemon");
                    // Los datos devueltos por la API se agregan al adaptador
                    pokedexViewModel.setPokedexData(pokedex);           // Almacena los datos consumidos en el ViewModel
                } else {
                    Log.e(TAG, "La lista de Pokemon está vacía o es null");
                }
                hideLoadingIndicator();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void loadCapturedPokemonFromFirestore() {
        if (!isNetworkAvailable()) {
            Log.e(TAG, "No hay conexión a Internet");
            Toast.makeText(this, R.string.no_conection, Toast.LENGTH_LONG).show();
            hideLoadingIndicator();
            return;
        }
        // *****
        Log.i(TAG, "MainActivity -> Leyendo los pokemoncapturados en Firestore");
        readAllPokemon();

    }

    private void initializeUI() {
        // *****
        Log.i(TAG, "MainActivity -> Inicializar la UI");

        // Cargar el lenguaje almacenado en la SharedPreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String languageCode = preferences.getString(LANGUAGE, LANGUAGE_DEFAULT);
        setLocale(languageCode);
        // ******
        Log.i(TAG, "MainActivity -> Acabo de preparar la app con el idioma");

        // Establece contenido de la toolbar
        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        // Configurar el controlador de navegacion para los fragment
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
            NavigationUI.setupActionBarWithNavController(this, navController);
        }
        binding.bottomNavigation.setOnItemSelectedListener(this::selectedBottomMenu);
        configureActionBar();
    }

    private boolean selectedBottomMenu(MenuItem menuItem) {
        int selectedOptions = menuItem.getItemId();

        if (selectedOptions == R.id.navigation_captured)
            navController.navigate(R.id.capturedFragment);
        else if (selectedOptions == R.id.navigation_pokedex)
            navController.navigate(R.id.pokedexFragment);
        else
            navController.navigate(R.id.settingsFragment);
        return true;
    }

    private void configureActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue_pokemon)));
            getSupportActionBar().hide();
        }
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

    public void userClickedListPokemon(PokedexData pokemonToCapture, View view) {
        // Se ha seleccionado un Pokemon de la lista cargada de la API.
        // Con este Pokemon, debemos hacer un segundo consumo de API indicando el Pokemon a cargar
        // Los datos cargados,
    }

    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
    }

    public void logout() {
        // Cerrar sesión de Firebase
        FirebaseAuth.getInstance().signOut();
        // Redirigir a la actividad de inicio de sesión
        startActivity(new Intent(this, LoginActivity.class));
        Toast.makeText(this, R.string.sesion_finalizada, Toast.LENGTH_SHORT).show();
    }

    private void capturePokemon() {
//        PokemonData newPokemon = new PokemonData(
//                binding.editTextId.getText().toString(),
//                binding.editTextName.getText().toString(),
//                Double.parseDouble(binding.editTextWeight.getText().toString()),
//                Double.parseDouble(binding.editTextHeight.getText().toString()),
//                binding.editTextUrlImage.getText().toString(),
//                Arrays.asList(binding.editTextTypes.getText().toString().split(",\\s*"))
//        );
//
//        repository.addPokemon(newPokemon, new OperationCallBack() {
//            @Override
//            public void onSuccess() {
//                Toast.makeText(MainActivity.this, "Pokemon Capturado", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void deletePokemon() {

//        String pokemonId = binding.editTextId.getText().toString();
//        repository.deletePokemon(pokemonId, new OperationCallBack() {
//            @Override
//            public void onSuccess() {
//                Toast.makeText(MainActivity.this, "Pokemon eliminado", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void readAllPokemon() {
        repository.getAllPokemons(userUID, new PokemonListCallback() {
            @Override
            public void onSuccess(ArrayList<PokemonData> capturedListPokemons) {
              //  Log.d(TAG + "=>MAIN_ACTIVITY", "Pokemons received: " + pokemons.size());
                int numberCaptured = capturedListPokemons.size();
                if (numberCaptured < 1) {
                    Toast.makeText(MainActivity.this, R.string.any_captured, Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(MainActivity.this, "Tienes " + numberCaptured + " Pokemons capturados", Toast.LENGTH_SHORT).show();
                //
            }

            @Override
            public void onFailure(Exception e) {
                //Log.e(TAG + "=>MAIN_ACTIVITY", "Error getting pokemons: ", e);
                String msg_error = R.string.error + e.getMessage();
                Toast.makeText(MainActivity.this, msg_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

}