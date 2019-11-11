package com.example.magicthealbum.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.magicthealbum.R;
import com.example.magicthealbum.interfaces.CardListener;
import com.example.magicthealbum.model.Card;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private List<Card> listaCard;
    private CardListener cardListener;


    public CardAdapter(CardListener cardListener) {
        listaCard = new ArrayList<>();
        this.cardListener = cardListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_celula, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Card card = listaCard.get(position);

        if (card.getMultiverseid() == null) {
            listaCard.remove(card);
        } else {

            Picasso.get()
                    .load("https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=" + card.getMultiverseid() + "&type=card")
                    .into(holder.imageCard);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardListener.onCardClicado(card);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listaCard.size();
    }

    public void adicionarListaCard(List<Card> cardList) {
        listaCard.addAll(cardList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageCard = itemView.findViewById(R.id.foto_card_image_view);
        }
    }
}
