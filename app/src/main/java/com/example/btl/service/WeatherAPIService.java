package com.example.btl.service;

import com.example.btl.model.Weather;
import com.example.btl.model.WeatherList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPIService {

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

    // http://api.openweathermap.org/data/2.5/weather?q=Hanoi&appid=06c921750b9a82d8f5d1294e1586276f
    // http://api.openweathermap.org/data/2.5/forecast?q=hanoi&units=metric&cnt=7&appid=06c921750b9a82d8f5d1294e1586276f

    WeatherAPIService weatherService = new Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(WeatherAPIService.class);

    @GET("/data/2.5/weather?appid=06c921750b9a82d8f5d1294e1586276f&units=metric")
    Call<Weather> getWeather(@Query("q") String q);

    @GET("data/2.5/forecast?units=metric&cnt=7&appid=53fbf527d52d4d773e828243b90c1f8e")
    Call<WeatherList> getWeather7Days(@Query("q") String q);
}
