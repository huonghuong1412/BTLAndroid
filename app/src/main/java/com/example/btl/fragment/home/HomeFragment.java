package com.example.btl.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.example.btl.activity.AddCategoryActivity;
import com.example.btl.activity.AddTagActivity;
import com.example.btl.activity.TagActivity;
import com.example.btl.adapter.TodoCategoryAdapter;
import com.example.btl.model.Category;
import com.example.btl.sqlite.TodoSqliteHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private TodoCategoryAdapter adapter;
    private TodoSqliteHelper sqliteHelper;
    private FloatingActionButton floatingActionButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = v.findViewById(R.id.recyclerView);
        floatingActionButton = v.findViewById(R.id.fab);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(v.getContext(), 2));
        recyclerView.setAdapter(new TodoCategoryAdapter());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        adapter = new TodoCategoryAdapter();
        sqliteHelper = new TodoSqliteHelper(v.getContext());
        List<Category> list = sqliteHelper.getAllByUser(firebaseUser.getUid());
        adapter.setList(list);
        recyclerView.setAdapter(adapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddCategoryActivity.class);
                intent.putExtra("userId", firebaseUser.getUid());
                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        List<Category> list = sqliteHelper.getAllByUser(firebaseUser.getUid());
        adapter.setList(list);
        recyclerView.setAdapter(adapter);
        super.onResume();
    }
}