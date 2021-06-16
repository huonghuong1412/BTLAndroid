package com.example.btl.service;

import com.example.btl.model.News;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface NewsAPIService {

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

    // http://10.0.2.2:3001/news

    NewsAPIService newsAPIService = new Retrofit.Builder()
//            .baseUrl("http://10.0.2.2:3001")
            .baseUrl("https://60bc864cb8ab37001759f30b.mockapi.io")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(NewsAPIService.class);

    @GET("/news")
    Call<List<News>> getAllNews();

}