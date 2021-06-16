package com.example.btl.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.example.btl.activity.ReadNewsActivity;
import com.example.btl.model.News;
import com.example.btl.model.Weather;
import com.example.btl.model.WeatherDay;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>{

    private List<WeatherDay> list;
    private Context context;

    public WeatherAdapter() {
        list = new ArrayList<>();
    }

    public void setNews(List<WeatherDay> list){ this.list=list; }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.weather_item,parent,false);
        return new WeatherAdapter.WeatherViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        WeatherDay s = list.get(position);
        holder.txtDateTime.setText(new SimpleDateFormat("dd-MM-yyyy").format(new Date(s.getDt()*1000)));
        holder.txtStatus.setText(s.getWeather().get(0).getMain());
        holder.txtTemp.setText(s.getTemp().getDay().toString());
        holder.txtMaxTemp.setText(s.getTemp().getMax().toString());
        holder.txtMinTemp.setText(s.getTemp().getMin().toString());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), ReadNewsActivity.class);
//                intent.putExtra("link", link);
//                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {

        private TextView txtDateTime, txtStatus, txtTemp, txtMaxTemp, txtMinTemp;
        private CardView cardView;

        public WeatherViewHolder(@NonNull View v) {
            super(v);
            txtDateTime = v.findViewById(R.id.txtDateTime);
            txtStatus = v.findViewById(R.id.txtStatus);
            txtTemp = v.findViewById(R.id.txtTemp);
            txtMaxTemp = v.findViewById(R.id.txtMaxTemp);
            txtMinTemp = v.findViewById(R.id.txtMinTemp);
            cardView = v.findViewById(R.id.cardView);
        }
    }
}
