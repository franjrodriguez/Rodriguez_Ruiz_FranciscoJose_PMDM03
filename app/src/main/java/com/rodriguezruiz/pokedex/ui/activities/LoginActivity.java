package com.rodriguezruiz.pokedex.ui.activities;

import static com.rodriguezruiz.pokedex.utils.Constants.TAG;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rodriguezruiz.pokedex.R;

import java.util.Arrays;
import java.util.List;

/**
 * Actividad que maneja el proceso de autenticación de usuarios utilizando FirebaseUI.
 * Esta actividad permite a los usuarios iniciar sesión con correo electrónico o Google.
 * Si el usuario ya está autenticado, redirige a la actividad principal (MainActivity).
 */
public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 11011;
    private FirebaseAuth auth;

    /**
     * Método llamado cuando se crea la actividad.
     * Verifica si el usuario ya está autenticado. Si es así, redirige a MainActivity.
     * Si no, inicia el proceso de autenticación con FirebaseUI.
     *
     * @param savedInstanceState Estado guardado de la actividad, si existe.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // El usuario se ha autenticado, se ha conseguido la UID y redirige el proceso a MainActivity
            Log.i(TAG, "LoginActivity -> Usuario logueado");

            MyApplication.setUserUID(currentUser.getUid());
            goToMainActivity();
        } else {
            // Usuario no autenticado, se inicia FirebaseUI
            Log.i(TAG, "LoginActivity -> El usuario NO estaba logueado. Comienza a solicitar user-pwd");
            startFirebaseUI();
        }
    }

    /**
     * Redirige a la actividad principal (MainActivity) y finaliza la actividad actual.
     */
    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Inicia el proceso de autenticación con FirebaseUI.
     * Configura los proveedores de autenticación (correo electrónico y Google) y lanza la actividad de inicio de sesión.
     */
    private void startFirebaseUI() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );
        // Inicia FirebaseUI
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.PokemonTheme)
                .setLogo(R.drawable.logopokemon)
                .build();

        // Inicia la activity de autenticacion
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * Método llamado cuando se obtiene un resultado de una actividad lanzada con startActivityForResult.
     * Maneja el resultado del proceso de autenticación.
     *
     * @param requestCode Código de solicitud que se utilizó para iniciar la actividad.
     * @param resultCode  Código de resultado devuelto por la actividad.
     * @param data        Datos adicionales devueltos por la actividad.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Usuario logueado exitosamente
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    MyApplication.setUserUID(user.getUid());    // Recoge el UID y lo deja en MyApplication para despues
                    goToMainActivity();                         // Nos vamos al MainActivity
                }
            } else {
                // Fallo en la autenticacion, nos quedamos aquí
                if (response != null) {
                    String msg = R.string.login_invalido + "";
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * Redirige a la actividad de inicio de sesión (LoginActivity) y finaliza la actividad actual.
     */
    private void gotoLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

