package com.example.btl.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
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
import com.example.btl.activity.TodoActivity;
import com.example.btl.activity.UpdateTagActivity;
import com.example.btl.model.Tag;
import com.example.btl.model.Task;
import com.example.btl.sqlite.TodoSqliteHelper;

import java.util.ArrayList;
import java.util.List;

public class TodoTagAdapter extends RecyclerView.Adapter<TodoTagAdapter.TodoTagViewHoler>{

    private List<Tag> list;
    private List<Task> listTask;
    private Context context;
    private TodoSqliteHelper sqliteHelper;

    public TodoTagAdapter() {
        this.list = new ArrayList<>();
    }

    public void setList(List<Tag> list){ this.list=list; }

    @NonNull
    @Override
    public TodoTagViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.tag_item,parent,false);
        this.sqliteHelper = new TodoSqliteHelper(v.getContext());
        return new TodoTagAdapter.TodoTagViewHoler(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoTagViewHoler holder, int position) {
        Tag tag = list.get(position);
        listTask = new ArrayList<>();
        listTask = sqliteHelper.getAllTodoByTag(tag.getId());
        int count = 0;
        for(Task item : listTask) {
            if(item.getStatus() == 1) {
                count++;
            }
        }

        holder.txtName.setText(tag.getName());
        holder.txtDescription.setText(tag.getDescription());
        holder.txtDate.setText(tag.getDate());
        holder.txtTime.setText(tag.getTime());
        holder.txtCategory.setText(count + "/" + listTask.size());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = v.getContext();
                Intent intent = new Intent(context, TodoActivity.class);
                intent.putExtra("tagId", tag.getId());
                context.startActivity(intent);
            }
        });
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = view.getContext();
//                Intent intent = new Intent(context, UpdateTagActivity.class);
//                intent.putExtra("tag", tag);
//                context.startActivity(intent);

                PopupMenu popup = new PopupMenu(view.getContext(),view );
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_card_tag, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.updateTag:
                                Intent intent = new Intent(context, UpdateTagActivity.class);
                                intent.putExtra("tag", tag);
                                context.startActivity(intent);
                                break;
                            case R.id.deleteTag:
                                notifyItemRemoved(position);
                                sqliteHelper = new TodoSqliteHelper(context);
                                sqliteHelper.deleteTag(tag.getId());
                                Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                                list.remove(position);
                                sqliteHelper.getAllTagByCategory(tag.getCategoryId());
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
//        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                notifyItemRemoved(position);
//                context = v.getContext();
//                sqliteHelper = new TodoSqliteHelper(context);
//                sqliteHelper.deleteTodo(tag.getId());
//                Toast.makeText(v.getContext(), "Xoá thẻ thành công", Toast.LENGTH_SHORT).show();
//                list.remove(position);
//                sqliteHelper.getAllTagByCategory(tag.getCategoryId());
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TodoTagViewHoler extends RecyclerView.ViewHolder {

        private TextView txtName, txtDescription, txtCategory, txtDate, txtTime;
        private CardView cardView;
        private ImageButton imageButton, btnDelete;
        public TodoTagViewHoler(@NonNull View v) {
            super(v);
            txtName = v.findViewById(R.id.txtName);
            txtDescription = v.findViewById(R.id.txtDescription);
            txtDate = v.findViewById(R.id.txtDate);
            txtTime = v.findViewById(R.id.txtTime);
            txtCategory = v.findViewById(R.id.txtCategory);
            cardView = v.findViewById(R.id.cardView);
            imageButton = v.findViewById(R.id.imageButton);
//            btnDelete = v.findViewById(R.id.btnDelete);
        }
    }

}
