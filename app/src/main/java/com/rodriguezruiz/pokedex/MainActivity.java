package com.rodriguezruiz.pokedex;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rodriguezruiz.pokedex.api.PokemonApiService;
import com.rodriguezruiz.pokedex.data.PokemonData;
import com.rodriguezruiz.pokedex.data.PokemonListName;
import com.rodriguezruiz.pokedex.databinding.ActivityMainBinding;
import com.rodriguezruiz.pokedex.models.PokemonResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "FRANTAG";
    private final static String URL_API ="http://pokeapi.co/api/v2/";
    private final static int OFFSET = 0;
    private final static int LIMIT = 150;

    private NavController navController;
    private ActivityMainBinding binding;
    private FirebaseAuth firebaseAuth;
    private Toolbar toolbar;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflar el binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Establece contenido de la toolbar
        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        // Iniciarlar la instalcia FirebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();

        // Activar el biewBinding para acceder a los objetos de las vistas
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setOnItemSelectedListener(this::selectedBottomMenu);

        setSupportActionBar(binding.toolbar);

        // Configurar el controlador de navegacion para los fragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
            NavigationUI.setupActionBarWithNavController(this, navController);
        }

        // Crea la instancia para usar Retrofit con el objetivo de leer la API
        retrofit = new Retrofit.Builder()
                .baseUrl(URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Procede con la carga de la lista de pokemon desde la API para ser mostrada en el recyclerview del pokemonlistfragment
        getListPokemon(OFFSET);
    }

    private void getListPokemon(int offset) {
        PokemonApiService service = retrofit.create(PokemonApiService.class);
        Call<PokemonResponse> pokemonResponseCall = service.getPokemonList(LIMIT, OFFSET);

        pokemonResponseCall.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if (response.isSuccessful()) {
                    PokemonResponse pokemonResponse = response.body();
                    ArrayList<PokemonListName> listaPokemon = pokemonResponse.getResults();

                    // Mostrarlos de momento en consola para verificacion
//                    for (int i = 0; i <= listaPokemon.size(); i++) {
//                        PokemonListName p = listaPokemon.get(i);
//                        Log.i(TAG, p.getName());
//                    }
                } else {
                    // Ventana para mostrar el error y que presionen OK
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                // Ventana para mostrar error y presionen OK
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
        }
    }

    private boolean selectedBottomMenu(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.navigation_pokedex)
            navController.navigate(R.id.pokedexListFragment);

        else if (menuItem.getItemId() == R.id.navigation_pokemon)
            navController.navigate(R.id.pokemonListFragment);

        else
            navController.navigate(R.id.settingsFragment);

        return true;
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

    public void userClickedListPokemon(PokemonListName currentPokemon, View view) {
        // Se ha seleccionado un Pokemon de la lista cargada de la API.
        // Con este Pokemon, debemos hacer un segundo consumo de API indicando el Pokemon a cargar
        // Los datos cargados,
    }
}