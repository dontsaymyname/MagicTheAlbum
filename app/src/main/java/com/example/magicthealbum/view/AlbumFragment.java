package com.example.magicthealbum.view;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.magicthealbum.R;
import com.example.magicthealbum.adapter.CardAdapter;
import com.example.magicthealbum.interfaces.CardListener;
import com.example.magicthealbum.model.Card;
import com.example.magicthealbum.model.CardResponse;
import com.example.magicthealbum.service.CardApi;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment implements CardListener {

    private static final String TAG = "MAGIC";

    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private int page;

    public AlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_album, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        cardAdapter = new CardAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(cardAdapter);

        getRetrofit();
        page = 1;
        getDados(page);
        changeStatusBarColor();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (recyclerView.canScrollVertically(1)) {
                    page += 1;

                    getDados(page);
                }
            }
        });
        return view;
    }

    public Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            clientBuilder.connectTimeout(30, TimeUnit.SECONDS);
            clientBuilder.readTimeout(30, TimeUnit.SECONDS);
            clientBuilder.writeTimeout(30, TimeUnit.SECONDS);

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.magicthegathering.io/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(clientBuilder.build())
                    .build();


        }
        return retrofit;
    }

    private void getDados(int page) {

        CardApi cardApi = retrofit.create(CardApi.class);
        Call<CardResponse> cardResponseCall = cardApi.getListaCards(page);

        cardResponseCall.enqueue(new Callback<CardResponse>() {
            @Override
            public void onResponse(Call<CardResponse> call, Response<CardResponse> response) {

                if (response.isSuccessful()) {
                    CardResponse cardResponse = response.body();
                    List<Card> cardList = cardResponse.getCards();

                    cardAdapter.adicionarListaCard(cardList);

                } else {
                    Log.e(TAG, "onResponse: " + response.errorBody());

                }
            }

            @Override
            public void onFailure(Call<CardResponse> call, Throwable t) {

                Log.e(TAG, " onFailure: " + t.getMessage());

            }
        });
    }


    @Override
    public void onCardClicado(Card card) {
        Intent intent = new Intent(getContext(), DetalhesCardActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("CARD", card);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getContext(),R.color.colorYellow));


        }
    }
}
