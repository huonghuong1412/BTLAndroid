package com.example.btl.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl.R;
import com.example.btl.model.User;
import com.example.btl.sqlite.TodoSqliteHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout email, password, txtFullName, txtPhone, txtAddress;
    private Button btnRegister;
    private TextView btnCancel;
    private FirebaseAuth firebaseAuth;
    private TodoSqliteHelper sqliteHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initView();
        firebaseAuth = FirebaseAuth.getInstance();
        sqliteHelper = new TodoSqliteHelper(this);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e = email.getEditText().getText().toString();
                String pass = password.getEditText().getText().toString();
                if(e.isEmpty()) {
                    email.setError("Email is not blank!");
                    Toast.makeText(getApplicationContext(), "Email is not blank", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pass.isEmpty()) {
                    password.setError("Password is not blank!");
                    Toast.makeText(getApplicationContext(), "Password is not blank", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pass.length() < 7) {
                    email.setError("Password is longger than 6!");
                    Toast.makeText(getApplicationContext(), "Password is longger than 6", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(e, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    User user = new User(txtFullName.getEditText().getText().toString(),
                                                        e,
                                                        txtPhone.getEditText().getText().toString(),
                                                        txtAddress.getEditText().getText().toString(),
                                                        FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    sqliteHelper.addUser(user);

                                    Toast.makeText(getApplicationContext(), "Register Success", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Failured", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        btnCancel=findViewById(R.id.btCancel);
        btnRegister=findViewById(R.id.btRegister);
        email=findViewById(R.id.txtEmail);
        password=findViewById(R.id.txtPass);
        txtFullName = findViewById(R.id.txtFullName);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);
    }
}