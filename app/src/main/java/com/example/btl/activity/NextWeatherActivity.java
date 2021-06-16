package com.example.btl.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.btl.R;
import com.example.btl.adapter.TodoTagAdapter;
import com.example.btl.adapter.WeatherAdapter;
import com.example.btl.model.Tag;
import com.example.btl.model.Task;
import com.example.btl.model.Weather;
import com.example.btl.model.WeatherDay;
import com.example.btl.model.WeatherItem;
import com.example.btl.model.WeatherList;
import com.example.btl.service.WeatherAPIService;
import com.example.btl.sqlite.TodoSqliteHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NextWeatherActivity extends AppCompatActivity {

    private List<WeatherDay> list;
    private RecyclerView recyclerView;
    private ImageButton imageButton;
    private WeatherAdapter adapter;
    private String q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_weather);
        init();
        getSupportActionBar().hide();

        Intent intent = getIntent();
        if(intent != null) {
            q = intent.getStringExtra("q");
        }

        callAPI(q);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WeatherAdapter();
        adapter.setNews(list);
        recyclerView.setAdapter(adapter);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void callAPI(String q) {
        WeatherAPIService.weatherService.getWeather7Days(q).enqueue(new Callback<WeatherList>() {
            @Override
            public void onResponse(Call<WeatherList> call, Response<WeatherList> response) {
                if(response.body() != null) {
                    WeatherList weatherList = response.body();
                    list = weatherList.getList();
                    adapter.setNews(list);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<WeatherList> call, Throwable t) {
                Toast.makeText(NextWeatherActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  void init() {
        recyclerView = findViewById(R.id.recyclerView);
        imageButton = findViewById(R.id.imageButton);
    }

}