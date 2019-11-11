package com.example.magicthealbum.view;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.magicthealbum.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    private Button irParaCadastroBotao;
    private TextView nickTextView;
    private TextView aviso2TextView;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;


    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        irParaCadastroBotao = view.findViewById(R.id.perfil_ir_para_cadastro_botao);
        nickTextView = view.findViewById(R.id.perfil_nick_text_view);
        aviso2TextView = view.findViewById(R.id.aviso2_text_view);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        getUser();
        changeStatusBarColor();


        irParaCadastroBotao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irParaLogin();
            }
        });


        return view;
    }

    private void getUser() {

        if (firebaseUser != null) {
            String nick = firebaseUser.getDisplayName();

            nickTextView.setText(nick);
            aviso2TextView.setVisibility(View.GONE);
            irParaCadastroBotao.setText("Sair");
        }

    }

    private void irParaLogin() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getContext(),R.color.colorAccent));


        }
    }

}
