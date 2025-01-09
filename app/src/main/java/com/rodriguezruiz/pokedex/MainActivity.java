package com.rodriguezruiz.pokedex;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseAuth;
import com.rodriguezruiz.pokedex.databinding.ActivityMainBinding;
import com.rodriguezruiz.pokedex.models.Pokemon;
import com.rodriguezruiz.pokedex.ui.activities.LoginActivity;
import com.rodriguezruiz.pokedex.ui.fragment.SettingsFragment;

import java.util.Locale;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "FRANTAG";
    private final static String URL_API ="http://pokeapi.co/api/v2/";
    private final static int OFFSET = 0;
    private final static int LIMIT = 150;
    private final static String LANGUAGE = "language_preference";
    private final static String LANGUAGE_DEFAULT = "es";

    private NavController navController = null;
    private ActivityMainBinding binding;
    private FirebaseAuth firebaseAuth;
    private Toolbar toolbar;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Cargar el lenguaje almacenado en la SharedPreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String languageCode = preferences.getString(LANGUAGE, LANGUAGE_DEFAULT);
        setLocale(languageCode);

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

    public void userClickedListPokemon(Pokemon currentPokemon, View view) {
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
}