package com.rodriguezruiz.pokedex.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.rodriguezruiz.pokedex.R;
import com.rodriguezruiz.pokedex.models.Pokedex;
import com.rodriguezruiz.pokedex.databinding.FragmentSettingsBinding;
import com.rodriguezruiz.pokedex.ui.activities.LoginActivity;

import java.util.ArrayList;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private FragmentSettingsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        // Configurar listener para la preferencia de "Acerca de..."
        Preference aboutPreference = findPreference("about_preference");
        if (aboutPreference != null) {
            aboutPreference.setOnPreferenceClickListener(preference -> {
                // Mostrar diálogo con información del desarrollador y versión
                new AlertDialog.Builder(getContext())
                        .setTitle("Acerca de...")
                        .setMessage("Desarrollador: Tu Nombre\nVersión: 1.0.0")
                        .setPositiveButton("OK", null)
                        .show();
                return true;
            });
        }

        // Configurar listener para la preferencia de "Cerrar sesión"
        Preference logoutPreference = findPreference("logout_preference");
        if (logoutPreference != null) {
            logoutPreference.setOnPreferenceClickListener(preference -> {
                // Cerrar sesión de Firebase
                FirebaseAuth.getInstance().signOut();
                // Redirigir a la actividad de inicio de sesión
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                return true;
            });
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, @Nullable String key) {

    }
}