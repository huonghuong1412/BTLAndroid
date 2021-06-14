package com.example.btl.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.example.btl.model.Task;
import com.example.btl.sqlite.TodoSqliteHelper;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{

    private List<Task> list;
    private Context context;
    private TodoSqliteHelper sqliteHelper;

    public TaskAdapter(Context context, TodoSqliteHelper sqliteHelper) {
        this.context = context.getApplicationContext();
        this.sqliteHelper = new TodoSqliteHelper(context);
    }

    public TaskAdapter() {
        this.list = new ArrayList<>();
        sqliteHelper = new TodoSqliteHelper(context);
    }

    public void setList(List<Task> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public void deleteTask(int positon) {
        Task task = list.get(positon);
        sqliteHelper.deleteTodo(task.getId());
        list.remove(positon);
        notifyItemRemoved(positon);
    }



    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.task_item,parent,false);
        return new TaskAdapter.TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = list.get(position);
        holder.checkBox.setText(task.getName());
        holder.checkBox.setChecked(task.getStatus() == 0 ? false : true);
        if (task.getStatus() == 1) {
            holder.checkBox.setPaintFlags(holder.checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                context = buttonView.getContext();
                sqliteHelper = new TodoSqliteHelper(context);
                if(isChecked) {
                    task.setStatus(1);
                    sqliteHelper.updateTodo(task);
                    list.get(position).setStatus(1);
                    sqliteHelper.getAllTodoByTag(task.getTagId());
                    notifyDataSetChanged();
                } else {
                    task.setStatus(0);
                    sqliteHelper.updateTodo(task);
                    list.get(position).setStatus(0);
                    sqliteHelper.getAllTodoByTag(task.getTagId());
                    notifyDataSetChanged();
                }
            }
        });
        holder.btnDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemRemoved(position);
                context = v.getContext();
                sqliteHelper = new TodoSqliteHelper(context);
                sqliteHelper.deleteTodo(task.getId());
                Toast.makeText(v.getContext(), "Xoá thành công", Toast.LENGTH_SHORT).show();
                list.remove(position);
                sqliteHelper.getAllTodoByTag(task.getTagId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBox;
        private ImageButton btnDeleteTask;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.todoCheckBox);
            btnDeleteTask = itemView.findViewById(R.id.btnDeleteTask);
        }
    }
}
