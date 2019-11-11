package com.example.magicthealbum.service;

import com.example.magicthealbum.model.CardResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CardApi {

    @GET("cards")
    Call<CardResponse> getListaCards(@Query("page") int page);
}
