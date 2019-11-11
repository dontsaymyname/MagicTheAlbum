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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private Button irParaCadastroBotao;
    private Button logarBotao;
    private TextInputEditText emailEditText;
    private TextInputEditText senhaeditText;
    private FirebaseAuth firebaseAuth;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        irParaCadastroBotao = findViewById(R.id.login_ir_para_cadastro_botao);
        logarBotao = findViewById(R.id.entrar_login_botao);
        emailEditText = findViewById(R.id.email_login_edit_text);
        senhaeditText = findViewById(R.id.senha_login_edit_text);
        
        irParaCadastroBotao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irParaCadastro();
            }
        });
        
        logarBotao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logar();
            }
        });

        changeStatusBarColor();
    }

    private void logar() {

        android.view.inputmethod.InputMethodManager teclado = (android.view.inputmethod.InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if (teclado.isAcceptingText()) {
            teclado.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        if (emailEditText.getText().toString().equals("")) {
            emailEditText.setError("Digite um email");

        }

        if (senhaeditText.getText().toString().equals("")) {
            senhaeditText.setError("Digite uma senha");
        } else {
            autenticar();

        }
    }

    private void autenticar() {
        String email = emailEditText.getEditableText().toString();
        String senha = senhaeditText.getEditableText().toString();

        firebaseAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            irParaPerfil();
                        } else {
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void irParaPerfil() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void irParaCadastro() {
        Intent intent = new Intent(this, CadastroActivity.class);
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
