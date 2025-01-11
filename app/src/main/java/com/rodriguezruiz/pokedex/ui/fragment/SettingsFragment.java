package com.rodriguezruiz.pokedex.ui.fragment;

import static com.rodriguezruiz.pokedex.utils.Constants.SETTING_ABOUT;
import static com.rodriguezruiz.pokedex.utils.Constants.SETTING_DELETE;
import static com.rodriguezruiz.pokedex.utils.Constants.SETTING_LANGUAGE;
import static com.rodriguezruiz.pokedex.utils.Constants.SETTING_LOGOUT;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.rodriguezruiz.pokedex.ui.activities.MainActivity;
import com.rodriguezruiz.pokedex.R;
import com.rodriguezruiz.pokedex.databinding.FragmentSettingsBinding;
import java.util.Locale;
import java.util.Objects;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {


    private FragmentSettingsBinding binding;
    private SharedPreferences sharedPreferences;
  //  private final Context context;

    public SettingsFragment() {
        super();
    }

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

        // Configurar las preferencias
        setupLanguagePreference();
        setupDeletePokemonPreference();
        setupAboutPreference();
        setupLogoutPreference();
    }

    private void setupLogoutPreference() {
        // Configurar listener para la preferencia de "Cerrar sesión"
        Preference logoutPreference = findPreference(SETTING_LOGOUT);
        if (logoutPreference != null) {
            logoutPreference.setOnPreferenceClickListener(preference -> {
                MainActivity mainActivity = (MainActivity) requireActivity();
                mainActivity.logout();
                return true;
            });
        }
    }

    private void setupAboutPreference() {
        // Configurar listener para la preferencia de "Acerca de..."
        Preference aboutPreference = findPreference(SETTING_ABOUT);
        if (aboutPreference != null) {
            aboutPreference.setOnPreferenceClickListener(preference -> {
                showAbout();
                return true;
            });
        }
    }

    private void showAbout() {
        // Mostrar diálogo con información del desarrollador y versión
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        String message = String.format("%s\n\n%s", getString(R.string.copyright), getString(R.string.version));

        builder.setTitle(R.string.about)
                .setMessage(message)
                .setIcon(R.drawable.logopokemon)
                .setPositiveButton(R.string.ok_button, (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setupDeletePokemonPreference() {
        // Configurar listener para la preferencia de permitir Borrar pokemon
        SwitchPreferenceCompat deletePreference = findPreference(SETTING_DELETE);
        if (deletePreference != null) {
            deletePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean isEnabledDeletePokemon = (boolean) newValue;
                return true;
            });
        }
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

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getPreferenceScreen().getSharedPreferences()).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Objects.requireNonNull(getPreferenceScreen().getSharedPreferences()).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, @Nullable String key) {

    }
}