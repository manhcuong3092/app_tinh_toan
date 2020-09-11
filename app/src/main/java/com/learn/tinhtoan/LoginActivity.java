package com.learn.tinhtoan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnRegister;
    EditText edtUsername, edtPassword;
    public static Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mapping();

        initDB();

        //chuyen sang homeactivity
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString().toLowerCase();
                String password = edtPassword.getText().toString();
                if(username.length() == 0 || password.length() == 0){
                    Toast.makeText(LoginActivity.this, "Vui lòng điền đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
                } else {
                    Cursor cursor = Database.findUserName(username);
                    if(cursor.moveToFirst() && cursor.getCount() > 0){ ///neu tim thay
                        if (cursor.getString(2).equals(password)){
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            User user = new User(
                                    cursor.getInt(0),
                                    cursor.getString(1),
                                    cursor.getString(2),
                                    cursor.getBlob(3)
                            );
                            intent.putExtra("user", user);
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

    private void initDB() {
        database = new Database(this, "User.sqlite", null, 1);
        database.queryData("CREATE TABLE IF NOT EXISTS User(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " Username VARCHAR(150) NOT NULL, Password VARCHAR(150) NOT NULL, Image BLOB)");

        database.queryData("CREATE TABLE IF NOT EXISTS DataUser(IdUser INTEGER PRIMARY KEY, " +
                "Diem INTEGER NOT NULL, SoCauTraLoi INTEGER NOT NULL, SoCauDung INTEGER NOT NULL," +
                " SoCauCong INTEGER NOT NULL, SoCauTru INTEGER NOT NULL, SoCauNhan INTEGER NOT NULL," +
                " SoCauChia INTEGER NOT NULL)");
    }


    private void mapping() {
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
    }

}