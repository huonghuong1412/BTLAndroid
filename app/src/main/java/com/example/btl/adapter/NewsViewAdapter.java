package com.example.btl.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.example.btl.activity.ReadNewsActivity;
import com.example.btl.model.News;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsViewAdapter extends RecyclerView.Adapter<NewsViewAdapter.NewsViewHolder>{

    private List<News> list;
    private Context context;

    public NewsViewAdapter() {
        list = new ArrayList<>();
    }

    public void setNews(List<News> list){ this.list=list; }


    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.news_item,parent,false);
        return new NewsViewAdapter.NewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News s = list.get(position);
        Picasso.get().load(Uri.parse(s.getImg())).into(holder.imageNews);
        holder.txtTitle.setText(s.getTitle());
        holder.txtTime.setText(s.getTime());
        String link = s.getLink();
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ReadNewsActivity.class);
                intent.putExtra("link", link);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTitle, txtTime;
        private ImageView imageNews;
        private CardView cardView;

        public NewsViewHolder(@NonNull View v) {
            super(v);
            txtTitle = v.findViewById(R.id.txtTitle);
            txtTime = v.findViewById(R.id.txtTime);
            imageNews = v.findViewById(R.id.imageNews);
            cardView = v.findViewById(R.id.cardView);
        }
    }

}