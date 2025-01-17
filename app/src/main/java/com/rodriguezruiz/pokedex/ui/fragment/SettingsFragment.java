package com.rodriguezruiz.pokedex.ui.fragment;

import static com.rodriguezruiz.pokedex.utils.Constants.SETTING_ABOUT;
import static com.rodriguezruiz.pokedex.utils.Constants.SETTING_DELETE;
import static com.rodriguezruiz.pokedex.utils.Constants.SETTING_LANGUAGE;
import static com.rodriguezruiz.pokedex.utils.Constants.SETTING_LOGOUT;
import static com.rodriguezruiz.pokedex.utils.Constants.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;
import com.rodriguezruiz.pokedex.ui.activities.MainActivity;
import com.rodriguezruiz.pokedex.R;
import com.rodriguezruiz.pokedex.utils.Constants;

import java.util.Locale;
import java.util.Objects;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        // Configurar las preferencias
        setupLanguagePreference();
        setupDeletePokemonPreference();
        setupAboutPreference();
        setupLogoutPreference();
    }

    private void setupLanguagePreference() {
        // Configurar listener para la preferencia de Cambio de idioma
        ListPreference languagePreference = findPreference(SETTING_LANGUAGE);
        if (languagePreference != null) {
            languagePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                // Se procede a cambiar el idioma
                String languageCode = (String) newValue;
                updateLanguage(languageCode);
                return true;
            });
        }
    }

    private void updateLanguage(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        requireActivity().getResources().updateConfiguration(configuration, requireActivity().getResources().getDisplayMetrics());
        // Reiniciar la activity para que tengan lugar los cambios de idioma
        requireActivity().recreate();
    }

    private void setupDeletePokemonPreference() {
        // Configurar listener para la preferencia de permitir Borrar pokemon
        SwitchPreferenceCompat deletePreference = findPreference(SETTING_DELETE);
        if (deletePreference != null) {
            deletePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean isEnabledDeletePokemon = (boolean) newValue;
                SharedPreferences.Editor editor = getPreferenceManager().getSharedPreferences().edit();
                editor.putBoolean(SETTING_DELETE, isEnabledDeletePokemon);
                editor.apply();
                return true;
            });
        }
    }

    private void setupAboutPreference() {
        // Configurar listener para la preferencia de "Acerca de..."
        Preference aboutPreference = findPreference(Constants.SETTING_ABOUT);
        if (aboutPreference == null) {
            Log.e(TAG, "SettingsFragment -> no se pudo encontrar la preferencia About");
        }

        aboutPreference.setOnPreferenceClickListener(preference -> {
            Log.d(TAG, "SettingsFragment -> Click en preferencia About");
            showAboutDialog();
            return true;
        });
    }

    private void showAboutDialog() {
        Context context = getActivity();
        if (context == null) {
            Log.e(TAG, "SettingsFragment -> Contexto no disponible");
            return;
        }
        // Mostrar diálogo
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            String message = String.format("%s\n\n%s",
                    getString(R.string.copyright),
                    getString(R.string.version));

            builder.setTitle(R.string.about)
                    .setMessage(message)
                    .setIcon(R.drawable.logopokemon)
                    .setPositiveButton(R.string.ok_button, (dialog, which) -> {
                        Log.d(TAG, "SharedPreferences -> Dialogo cerrado");
                        dialog.dismiss();
                    })
                    .create()
                    .show();
        } catch (Exception e) {
            Log.e(TAG, "SharedPreferences -> Error al mostrar el dialogo About: " + e.getMessage());
        }
    }

    private void setupLogoutPreference() {
        // Configurar listener para la preferencia de "Cerrar sesión"
        Preference logoutPreference = findPreference(SETTING_LOGOUT);
        if (logoutPreference != null) {
            logoutPreference.setOnPreferenceClickListener(preference -> {
                showLogoutConfirmationDialog();
                return true;
            });
        }
    }

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Cerrar sesión")
                .setMessage("¿Seguro que quieres cerrar la sesión?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    MainActivity mainActivity = (MainActivity) requireActivity();
                    mainActivity.logout();                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    dialog.dismiss();
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, @Nullable String key) {
        // Maneja cambios en las preferencias
        assert key != null;
        if (key.equals(Constants.SETTING_LANGUAGE)) {
            String languageCode = sharedPreferences.getString(key, "es");
            updateLanguage(languageCode);
        }
    }
}