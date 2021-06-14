package com.example.btl.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.example.btl.activity.TagActivity;
import com.example.btl.activity.UpdateCategoryActivity;
import com.example.btl.activity.UpdateTagActivity;
import com.example.btl.model.Category;
import com.example.btl.sqlite.TodoSqliteHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TodoCategoryAdapter extends RecyclerView.Adapter<TodoCategoryAdapter.TodoViewHolder>{

    private List<Category> list;
    private Context context;
    private TodoSqliteHelper sqliteHelper;

    public TodoCategoryAdapter() {
        this.list = new ArrayList<>();
    }

    public void setList(List<Category> list){ this.list=list; }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.category_item,parent,false);
        this.sqliteHelper = new TodoSqliteHelper(v.getContext());
        return new TodoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        int[] androidColors = {Color.parseColor("#039BE5"), Color.parseColor("#3D51B3"), Color.parseColor("#689f38"), Color.parseColor("#FD7044"), Color.parseColor("#FF03DAC5"), Color.parseColor("#FF018786"), Color.parseColor("#607D8B"), Color.parseColor("#6ABAE1"), Color.parseColor("#EC47BE"), Color.parseColor("#F1A3E9"), Color.parseColor("#71EF6C"), Color.parseColor("#327030"), Color.parseColor("#EC5144"), Color.parseColor("#D51808"), Color.parseColor("#79BF28"), Color.parseColor("#C442E8"), Color.parseColor("#D62727"), Color.parseColor("#EC90AF"), Color.parseColor("#11A3EA"), Color.parseColor("#D1BD0F")};
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        holder.cardView.setCardBackgroundColor(randomAndroidColor);
        Category category = list.get(position);
        holder.txtName.setText(category.getName());
        holder.txtDescription.setText(category.getDescription());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = v.getContext();
                Intent intent = new Intent(v.getContext(), TagActivity.class);
                intent.putExtra("categoryId", category.getId());
                context.startActivity(intent);
            }
        });
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = view.getContext();
                PopupMenu popup = new PopupMenu(view.getContext(),view );
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_card_category, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.update:
                                Intent intent = new Intent(context, UpdateCategoryActivity.class);
                                intent.putExtra("category", category);
                                context.startActivity(intent);
                                break;
                            case R.id.delete:
                                notifyItemRemoved(position);
                                sqliteHelper = new TodoSqliteHelper(context);
                                sqliteHelper.deleteCategory(category.getId());
                                Toast.makeText(context, "Xoá danh sách thành công", Toast.LENGTH_SHORT).show();
                                list.remove(position);
                                sqliteHelper.getAllByUser(category.getUserId());
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TodoViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName, txtDescription;
        private CardView cardView;
        private ImageButton imageButton;
        public TodoViewHolder(@NonNull View v) {
            super(v);
            txtName = v.findViewById(R.id.txtName);
            txtDescription = v.findViewById(R.id.txtDescription);
            cardView = v.findViewById(R.id.cardView);
            imageButton = v.findViewById(R.id.imageButton);
        }
    }

}
