package com.example.btl.fragment.weather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl.R;
import com.example.btl.adapter.NewsViewAdapter;
import com.example.btl.model.News;
import com.example.btl.model.Weather;
import com.example.btl.model.WeatherItem;
import com.example.btl.service.NewsAPIService;
import com.example.btl.service.WeatherAPIService;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherFragment extends Fragment {

    // texview address, updated_at, status, temp, temp_min, temp_max
    // sunrise, sunset, wind, pressure, humidity, about
    private TextView address, status, temp, temp_min, temp_max, sunrise, sunset, wind, pressure, humidity, about, updated_at;
    private TextInputLayout txtSearch;
    private Button btnSearch;
    private Weather weather;
    private String q;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather, container, false);
        txtSearch = v.findViewById(R.id.txtSearch);
        btnSearch = v.findViewById(R.id.btnSearch);
        address = v.findViewById(R.id.address);
        status = v.findViewById(R.id.status);
        temp = v.findViewById(R.id.temp);
        temp_min = v.findViewById(R.id.temp_min);
        temp_max = v.findViewById(R.id.temp_max);
        sunrise= v.findViewById(R.id.sunrise);
        sunset= v.findViewById(R.id.sunset);
        wind = v.findViewById(R.id.wind);
        pressure = v.findViewById(R.id.pressure);
        humidity = v.findViewById(R.id.humidity);
        about = v.findViewById(R.id.about);
        updated_at = v.findViewById(R.id.updated_at);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q = txtSearch.getEditText().getText().toString();
                callAPI(q);
            }
        });
        return v;
    }

    private void callAPI(String q) {
        WeatherAPIService.weatherService.getWeather(q).enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if(response.body() != null) {
                    weather = response.body();
                    address.setText(weather.getName() + ", " + weather.getSys().getCountry());
                    List<WeatherItem> items = weather.getWeather();
                    status.setText(items.get(0).getMain());
                    double doC = (double) Math.round((weather.getMain().getTemp()*10)/100);
                    double doCMax = (double) Math.round((weather.getMain().getTemp_max()*10)/100);
                    double doCMin = (double) Math.round((weather.getMain().getTemp_min()*10)/100);
                    double feels = (double) Math.round((weather.getMain().getFeels_like()*10)/100);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date date = new Date();
                    String dateTime = formatter.format(date);
                    updated_at.setText(dateTime);
                    temp.setText(doC + "°C");
                    temp_max.setText("Max Temp: " + doCMax + "°C");
                    temp_min.setText("Min Temp: " + doCMin + "°C");
                    sunrise.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(weather.getSys().getSunrise()*1000)));
                    sunset.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(weather.getSys().getSunset()*1000)));
                    wind.setText(weather.getWind().getSpeed().toString());
                    pressure.setText(weather.getMain().getPressure().toString());
                    humidity.setText(weather.getMain().getHumidity().toString());
                    about.setText(feels + "°C");
                }
            }
            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}