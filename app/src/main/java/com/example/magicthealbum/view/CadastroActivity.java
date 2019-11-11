package com.example.magicthealbum.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.magicthealbum.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

public class CadastroActivity extends AppCompatActivity {

    private TextInputEditText nickEditText;
    private TextInputEditText emailEditText;
    private TextInputEditText senhaEditText;
    private Button cadastrarBotao;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nickEditText = findViewById(R.id.nick_cadastro_edit_text);
        emailEditText = findViewById(R.id.email_cadastro_edit_text);
        senhaEditText = findViewById(R.id.senha_cadastro_edit_text);
        cadastrarBotao = findViewById(R.id.cadastrar_botao);

        cadastrarBotao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realizarCadastro();
            }
        });

        changeStatusBarColor();
    }

    private void realizarCadastro() {

        android.view.inputmethod.InputMethodManager teclado = (android.view.inputmethod.InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if (teclado.isAcceptingText()) {
            teclado.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
        }

        if (Objects.requireNonNull(nickEditText.getText()).toString().equals("")) {
            nickEditText.setError("Digite um Nick");

        }
        if (Objects.requireNonNull(emailEditText.getText()).toString().equals("")) {
            emailEditText.setError("Digite seu Email");

        }
        if (Objects.requireNonNull(senhaEditText.getText()).toString().equals("")) {
            senhaEditText.setError("Digite uma Senha");

        } else {
            cadastrar();

        }
    }

    private void cadastrar() {

        String email = emailEditText.getEditableText().toString();
        String password = senhaEditText.getEditableText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        voltarParaLogin();
                        Toast.makeText(this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
                        atualizarPerfil();

                    } else {
                        Toast.makeText(this, "Verifique seu email ou o número de caracteres da senha", Toast.LENGTH_SHORT).show();


                    }

                });

    }

    public void atualizarPerfil() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(nickEditText.getEditableText().toString())
                .build();

        assert user != null;
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    public void voltarParaLogin() {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorAccent));


        }
    }


}
