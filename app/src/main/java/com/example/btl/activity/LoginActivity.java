package com.example.btl.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl.MainActivity;
import com.example.btl.R;
import com.example.btl.model.User;
import com.example.btl.sqlite.TodoSqliteHelper;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.signin.internal.SignInClientImpl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout email, password;
    private Button btnLogin;
    private TextView btnRegister;
    private LoginButton btnLoginFaebook;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private CallbackManager callbackManager;
    private TodoSqliteHelper sqliteHelper;
    String TAG = "Facebook Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        initView();
        sqliteHelper = new TodoSqliteHelper(this);
        initActions();
        firebaseAuth = FirebaseAuth.getInstance();
        setupLoginFaceBook();
    }

    private void initView() {
        btnLogin=findViewById(R.id.btLogin);
        btnRegister=findViewById(R.id.btRegister);
        email=findViewById(R.id.txtEmail);
        password=findViewById(R.id.txtPass);
        btnLoginFaebook = findViewById(R.id.login_button);
    }

    private void initActions() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e = email.getEditText().getText().toString();
                String pass = password.getEditText().getText().toString();
                if(e.isEmpty()) {
                    email.setError("Email is not blank!");
                    return;
                }
                if(pass.isEmpty()) {
                    password.setError("Password is not blank!");
                    return;
                }
                if(pass.length() < 7) {
                    email.setError("Password is longger than 6!");
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(e, pass)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("name", "User");
                                intent.putExtra("email", e);
                                intent.putExtra("image", "https://i.pinimg.com/564x/e6/57/55/e65755e73d8085e30aedfa21fde07f1b.jpg");
                                intent.putExtra("type", "email");
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Failured", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            // name, email, phone, address, userId
//                            User user = new User(firebaseUser.getDisplayName(), firebaseUser.getEmail(), "", "", firebaseUser.getUid());
//                            sqliteHelper.addUser(user);
                            intent.putExtra("name", firebaseUser.getDisplayName());
                            intent.putExtra("email", firebaseUser.getEmail());
                            intent.putExtra("image", firebaseUser.getPhotoUrl().toString());
                            intent.putExtra("type", "facebook");
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Login failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void setupLoginFaceBook() {
        callbackManager = CallbackManager.Factory.create();
        btnLoginFaebook.setReadPermissions("email", "public_profile");
        btnLoginFaebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null){
            firebaseAuth.signOut();
        }
    }
}