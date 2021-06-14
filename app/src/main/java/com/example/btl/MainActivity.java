package com.example.btl;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btl.model.User;
import com.example.btl.sqlite.TodoSqliteHelper;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth firebaseAuth;
    private TextView txtName, txtEmail;
    private ImageView imageView;
    private ProfilePictureView pictureView;
    private TodoSqliteHelper sqliteHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();
        sqliteHelper = new TodoSqliteHelper(this);
        Intent intent = getIntent();
        String typeLogin = intent.getStringExtra("type");
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        Uri image = Uri.parse(intent.getStringExtra("image"));

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_news, R.id.nav_account, R.id.nav_weather)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        user = sqliteHelper.getUserById(firebaseAuth.getCurrentUser().getUid());

        View hView =  navigationView.inflateHeaderView(R.layout.nav_header_main);
        imageView = (ImageView)hView.findViewById(R.id.imageView);
        pictureView = (ProfilePictureView) hView.findViewById(R.id.profilePicture);
        txtName = (TextView) hView.findViewById(R.id.txtName);
        txtEmail = (TextView) hView.findViewById(R.id.txtEmail);

        txtEmail.setText(email);
        if ((typeLogin.equalsIgnoreCase("facebook"))) {
            txtName.setText(name);
            imageView.setVisibility(View.INVISIBLE);
            pictureView.setVisibility(View.VISIBLE);
            pictureView.setProfileId(Profile.getCurrentProfile().getId());
        } else {
            txtName.setText(user.getName());
            pictureView.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
            Picasso.get().load(image).into(imageView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.action_settings).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        firebaseAuth.signOut();
                        finish();
                        break;
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}