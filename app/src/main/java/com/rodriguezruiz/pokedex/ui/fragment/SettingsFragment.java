package com.rodriguezruiz.pokedex.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.rodriguezruiz.pokedex.MainActivity;
import com.rodriguezruiz.pokedex.R;
import com.rodriguezruiz.pokedex.ui.activities.LoginActivity;

import java.util.Locale;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        // Cargar el archivo de preferencias
        setPreferencesFromResource(R.xml.settings, rootKey);

        // Cambiar el titulo en la Toolbar
        if (getActivity() != null) {
            ((MainActivity) getActivity()).setToolbarTitle(getString(R.string.configuration));
        }

        // Configurar accion para 'Acerca de'
        Preference aboutPreference = findPreference("about");
        if (aboutPreference != null) {
            aboutPreference.setOnPreferenceClickListener(preference -> {
                showAboutDialog();
                return true;
            });
        }

        // Configurar acción para Cerrar la sesion
        Preference logoutPreference = findPreference("logout");
        if (logoutPreference != null) {
            logoutPreference.setOnPreferenceClickListener(preference -> {
                logout();
                return true;
            });
        }

        // Configurar accion para cambio de idioma
        ListPreference languagePreference = findPreference("language");
        if (languagePreference != null) {
            languagePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                changeLanguage((String) newValue);
                return true;
            });
        }
    }

    private void logout() {
        AuthUI.getInstance()
                .signOut(requireContext())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // Muestra mensaje y devuelve el control a LoginActivity
                        showLogout();
                        gotoLoginActivity();
                    }
                });
    }

    private void gotoLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }

    private void showLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setTitle(R.string.logout_title)
                .setMessage(R.string.logout_message)
                .setIcon(R.drawable.logout)
                .setPositiveButton(R.string.ok_button, (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void changeLanguage(String languageCode) {
        // Crear un nuevo objeto locate con el código del idioma
        Locale locale = new Locale(languageCode);

        // Establecer la configuracion del idioma de la aplicacion
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);

        // Actualizar los recursos de la app
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        // Actualizar la nueva preferencia del idioma
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        sharedPreferences.edit().putString("language", languageCode).apply();

        // Reiniciar la activity para que tengan lugar los cambios de idioma
        requireActivity().recreate();
    }

    public void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        String message = String.format("%s\n\n%s", getString(R.string.copyright), getString(R.string.version));

        builder.setTitle(R.string.about)
                .setMessage(message)
                .setIcon(R.drawable.logopokemon)
                .setPositiveButton(R.string.ok_button, (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}