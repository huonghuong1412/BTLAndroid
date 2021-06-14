package com.example.btl.fragment.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.btl.R;
import com.example.btl.model.User;
import com.example.btl.sqlite.TodoSqliteHelper;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountFragment extends Fragment {

    private TextInputLayout txtName, txtEmail, txtPhone, txtAddress;
    private EditText txtId;
    private Button btnEditAccount;
    private TodoSqliteHelper sqliteHelper;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_account, container, false);
        btnEditAccount=v.findViewById(R.id.btnEditAccount);
        txtId=v.findViewById(R.id.txtId);
        txtEmail=v.findViewById(R.id.txtEmail);
        txtName = v.findViewById(R.id.txtFullName);
        txtPhone = v.findViewById(R.id.txtPhone);
        txtAddress = v.findViewById(R.id.txtAddress);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        sqliteHelper = new TodoSqliteHelper(v.getContext());
        User user = sqliteHelper.getUserById(firebaseUser.getUid());

        txtId.setText(user.getId().toString());
        txtEmail.getEditText().setText(user.getEmail());
        txtName.getEditText().setText(user.getName());
        txtPhone.getEditText().setText(user.getPhone());
        txtAddress.getEditText().setText(user.getAddress());

        btnEditAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer id = Integer.parseInt(txtId.getText().toString());
                String name = txtName.getEditText().getText().toString();
                String phone = txtPhone.getEditText().getText().toString();
                String address = txtAddress.getEditText().getText().toString();

                User user = new User(id, name, txtEmail.getEditText().getText().toString(), phone, address, firebaseUser.getUid());
                sqliteHelper.updateUser(user);
                Toast.makeText(v.getContext(), "Update Info Success", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

}