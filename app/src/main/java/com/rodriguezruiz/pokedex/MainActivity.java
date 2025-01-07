package com.rodriguezruiz.pokedex;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rodriguezruiz.pokedex.api.PokedexApiService;
import com.rodriguezruiz.pokedex.data.PokedexListName;
import com.rodriguezruiz.pokedex.databinding.ActivityMainBinding;
import com.rodriguezruiz.pokedex.models.PokedexResponse;
import com.rodriguezruiz.pokedex.ui.fragment.CapturedFragment;
import com.rodriguezruiz.pokedex.ui.fragment.PokedexFragment;
import com.rodriguezruiz.pokedex.ui.fragment.SettingsFragment;

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

    private NavController navController = null;
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

        // Configurar el controlador de navegacion para los fragment
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
            NavigationUI.setupActionBarWithNavController(this, navController);
        }
        binding.bottomNavigation.setOnItemSelectedListener(this::selectedBottomMenu);
        configureActionBar();

        // Detalle del proceso:
        // 1.- Se inicia FirebaseAuth
        // 2.- Si es válido se continua, y en otro caso se avisa del error y se solicita nuevamente
        // Iniciar la instancia FirebaseAuth;
        //firebaseAuth = FirebaseAuth.getInstance();

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
    public void onStart() {
        super.onStart();
//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        if (currentUser != null) {
//            currentUser.reload();
//        }
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

    public void userClickedListPokemon(PokedexListName currentPokemon, View view) {
        // Se ha seleccionado un Pokemon de la lista cargada de la API.
        // Con este Pokemon, debemos hacer un segundo consumo de API indicando el Pokemon a cargar
        // Los datos cargados,
    }
}