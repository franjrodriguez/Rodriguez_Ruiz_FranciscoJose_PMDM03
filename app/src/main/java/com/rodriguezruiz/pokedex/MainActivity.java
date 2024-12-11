package com.rodriguezruiz.pokedex;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.rodriguezruiz.pokedex.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
}