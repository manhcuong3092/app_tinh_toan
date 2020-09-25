package com.learn.tinhtoan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.learn.tinhtoan.model.DataUser;
import com.learn.tinhtoan.R;
import com.learn.tinhtoan.model.User;
import com.learn.tinhtoan.model.UserAchievement;
import com.learn.tinhtoan.fragment.AchievementFragment;
import com.learn.tinhtoan.fragment.ChangePasswordFragment;
import com.learn.tinhtoan.fragment.ChartFragment;
import com.learn.tinhtoan.fragment.ExerciseFragment;
import com.learn.tinhtoan.fragment.HomeFragment;
import com.learn.tinhtoan.fragment.NotificationFragment;
import com.learn.tinhtoan.fragment.ProfileFragment;
import com.learn.tinhtoan.fragment.TodoFragment;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomNavigationView bottomNav;
    Toolbar toolbar;
    ImageView imgAvatar;
    TextView txtName, txtEmail;
    public static User currentUser;
    public static DataUser currentDataUser;
    public static UserAchievement currentUserAchievement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapping();

        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("user");
        currentDataUser = (DataUser) intent.getSerializableExtra("userData");
        currentUserAchievement = (UserAchievement) intent.getSerializableExtra("userAchievement");

        //bottom navbar
        bottomNav.setOnNavigationItemSelectedListener(bottomNavListener);

        //side bar
        navigationView.setNavigationItemSelectedListener(navListener);

        //Sửa theme là noActionBar
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //set cac gia tri cho navbar
        byte[] avatar = currentUser.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
        imgAvatar.setImageBitmap(bitmap);
        txtName.setText(currentUser.getName());

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    private void mapping() {
        navigationView  = findViewById(R.id.navigationView);
        toolbar         = findViewById(R.id.toolbar);
        bottomNav       = findViewById(R.id.bottomNav);
        drawerLayout    = findViewById(R.id.drawerLayout);

        View headerView = navigationView.getHeaderView(0);
        imgAvatar       = headerView.findViewById(R.id.navigation_avatar);
        txtName         = headerView.findViewById(R.id.navigation_name);
        txtEmail        = headerView.findViewById(R.id.navigation_email);
    }

    private NavigationView.OnNavigationItemSelectedListener navListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.nav_profile:
                    selectedFragment = new ProfileFragment();
                    break;
                case R.id.nav_statistic:
                    selectedFragment = new ChartFragment();
                    break;
                case R.id.nav_todo:
                    selectedFragment = new TodoFragment();
                    break;
                case R.id.nav_notification:
                    selectedFragment = new NotificationFragment();
                    break;
                case R.id.nav_changePassword:
                    selectedFragment = new ChangePasswordFragment();
                    break;
                case R.id.nav_logout:
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    //Xoa tat cac activity truoc do
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                case R.id.nav_share:
                    Toast.makeText(MainActivity.this, "Tính năng đang phát triển", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_send:
                    Toast.makeText(MainActivity.this, "Tính năng đang phát triển", Toast.LENGTH_SHORT).show();
                    break;
            }
            if(selectedFragment != null){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            }
            return true;
        }
    };

    //hàm xử lý bottom navigation view
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;   //gán fragment theo item
            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_exercise:
                    selectedFragment = new ExerciseFragment();
                    break;
                case R.id.nav_achievement:
                    selectedFragment = new AchievementFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}