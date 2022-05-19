package com.learn.tinhtoan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.learn.tinhtoan.model.DataUser;
import com.learn.tinhtoan.Database;
import com.learn.tinhtoan.R;
import com.learn.tinhtoan.model.User;
import com.learn.tinhtoan.model.UserAchievement;
import com.learn.tinhtoan.model.UserProfile;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnRegister;
    EditText edtUsername, edtPassword;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        database = new Database(this);

        mapping();

        //chuyen sang homeactivity
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString().toLowerCase();
                String password = edtPassword.getText().toString();
                if(username.length() == 0 || password.length() == 0){
                    Toast.makeText(LoginActivity.this, "Vui lòng điền đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
                } else {
                    Cursor cursor = database.findUserName(username);
                    if(cursor.moveToFirst() && cursor.getCount() > 0){ ///neu tim thay
                        if (cursor.getString(2).equals(password)){
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            User user = new User(
                                    cursor.getInt(0),
                                    cursor.getString(1),
                                    cursor.getString(2),
                                    cursor.getBlob(3)
                            );

                            DataUser userData = createDataUser(user);
                            UserAchievement userAchievement = createUserAchievement(user);
                            UserProfile userProfile = createUserProfile(user);
                            
                            intent.putExtra("user", user);
                            intent.putExtra("userData", userData);
                            intent.putExtra("userAchievement", userAchievement);
                            intent.putExtra("userProfile", userProfile);

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu." ,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //chuyen sang dang ki
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }

    private UserProfile createUserProfile(User user) {
        Cursor cursor3 = database.findUserProfile(user.getId());
        cursor3.moveToFirst();
        UserProfile userProfile = new UserProfile(
                cursor3.getInt(0),
                cursor3.getString(1),
                cursor3.getString(2),
                cursor3.getString(4),
                cursor3.getInt(3),
                cursor3.getString(5),
                cursor3.getString(6)
        );
        return userProfile;
    }

    private UserAchievement createUserAchievement(User user) {
        Cursor cursor2 = database.findUserAchievement(user.getId());
        cursor2.moveToFirst();
        UserAchievement userAchievement = new UserAchievement(
                cursor2.getInt(0),
                cursor2.getInt(1),
                cursor2.getString(2),
                cursor2.getInt(3),
                cursor2.getInt(4),
                cursor2.getInt(5),
                cursor2.getInt(6),
                cursor2.getInt(7),
                cursor2.getInt(8),
                cursor2.getInt(9),
                cursor2.getInt(10),
                cursor2.getInt(11),
                cursor2.getInt(12),
                cursor2.getInt(13)
        );
        return userAchievement;
    }

    private DataUser createDataUser(User user){
        Cursor cursor1 = database.findUserData(user.getId());
        cursor1.moveToFirst();
        DataUser userData = new DataUser(
                cursor1.getInt(0),
                cursor1.getInt(1),
                cursor1.getInt(2),
                cursor1.getInt(3),
                cursor1.getInt(4),
                cursor1.getInt(5),
                cursor1.getInt(6),
                cursor1.getInt(7),
                cursor1.getInt(8),
                cursor1.getInt(9),
                cursor1.getInt(10),
                cursor1.getInt(11),
                cursor1.getInt(12)
        );
        return userData;
    }

    private void mapping() {
        btnLogin    = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
    }

}