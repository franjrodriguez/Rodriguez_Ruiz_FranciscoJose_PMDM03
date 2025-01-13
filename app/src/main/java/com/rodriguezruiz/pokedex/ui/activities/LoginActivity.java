package com.rodriguezruiz.pokedex.ui.activities;

import android.content.Intent;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 11011;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_login);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // El usuario se ha autenticado, se ha conseguido la UID y redirige el proceso a MainActivity
            MyApplication.setUserUID(currentUser.getUid());
            goToMainActivity();
        } else {
            // Usuario no autenticado, se inicia FirebaseUI
            startFirebaseUI();
        }
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
    }

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
    }

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
                    Toast.makeText(this, "Usuario inválido. Verifique email y password", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

