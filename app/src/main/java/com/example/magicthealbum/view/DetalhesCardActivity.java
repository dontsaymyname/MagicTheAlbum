package com.example.magicthealbum.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.magicthealbum.R;
import com.example.magicthealbum.model.Card;
import com.squareup.picasso.Picasso;

public class DetalhesCardActivity extends AppCompatActivity {

    private ImageView imagemCard;
    private TextView nomeTextView;
    private TextView tipoTextView;
    private TextView raridadeTextView;
    private TextView artistaTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_card);

        imagemCard = findViewById(R.id.card_detalhes_image_view);
        nomeTextView = findViewById(R.id.nome_text_view);
        tipoTextView = findViewById(R.id.tipo_text_view);
        raridadeTextView = findViewById(R.id.raridade_text_view);
        artistaTextView = findViewById(R.id.artista_text_view);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Card card = (Card) bundle.getSerializable("CARD");

        Picasso.get().load("https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=" + card.getMultiverseid() + "&type=card")
                .into(imagemCard);

        nomeTextView.setText(card.getName());
        tipoTextView.setText(card.getType());
        raridadeTextView.setText(card.getRarity());
        artistaTextView.setText(card.getArtist());

        changeStatusBarColor();

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorYellow));


        }
    }
}
