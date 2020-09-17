package com.learn.tinhtoan.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.learn.tinhtoan.Database;
import com.learn.tinhtoan.ExerciseActivity;
import com.learn.tinhtoan.MainActivity;
import com.learn.tinhtoan.R;
import com.learn.tinhtoan.User;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends androidx.fragment.app.Fragment {

    View view;
    ImageView imgIcon;
    ImageButton ibtnTinhToan, ibtnTriNho;
    CircleImageView imgAvatar;
    TextView txtScore, txtName, txtTitle, txtTemp, txtTempMaxMin, txtHumidity, txtStatus, txtWind, txtCloud, txtCity, txtDay;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mapping();

        getCurrentWeatherData("Hanoi");
        setUserInfor();

        ibtnTinhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ExerciseActivity.class);

                intent.putExtra("soCau", 15);
                intent.putExtra("soGiay", 30);
                intent.putExtra("phepCong", true);
                intent.putExtra("phepTru", true);
                intent.putExtra("phepNhan", true);
                intent.putExtra("phepChia", true);
                intent.putExtra("easy", true);
                intent.putExtra("normal", false);
                intent.putExtra("hard", false);

                startActivity(intent);

            }
        });

        return view;
    }

    private void setUserInfor() {
        User user = MainActivity.currentUser;
        Cursor cursor = Database.findUserData(user.getId());
        txtName.setText(user.getName());

        byte[] avatar = user.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
        imgAvatar.setImageBitmap(bitmap);

        if(cursor.moveToFirst() && cursor.getCount() > 0){
            int score = cursor.getInt(1);
            txtScore.setText("Điểm: " + score);
        }

    }

    public void getCurrentWeatherData(String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + data + "&units=metric&lang=vi&appid=9821056b15bf9477cf459fb22bed848b";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            String city = jsonObject.getString("name");


                            long l = Long.valueOf(day);
                            Date date = new Date(l * 1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
                            String toDay = simpleDateFormat.format(date);

                            txtDay.setText("Ngày cập nhật: " + toDay);

                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObjectWeather.getString("description");
                            String icon = jsonObjectWeather.getString("icon");

                            Picasso.get().load("http://openweathermap.org/img/wn/" + icon + ".png").into(imgIcon);
                            txtStatus.setText(status);

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietdo = jsonObjectMain.getString("temp");
                            String doAm = jsonObjectMain.getString("humidity");
                            String nhietDoMax = jsonObjectMain.getString("temp_max");
                            String nhietDoMin = jsonObjectMain.getString("temp_min");

                            txtTempMaxMin.setText(nhietDoMax + "°/ " + nhietDoMin + "°");
                            txtTemp.setText(nhietdo + "°C");
                            txtHumidity.setText(doAm + "%");

                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                            String gio = jsonObjectWind.getString("speed");
                            txtWind.setText(gio + " m/s");

                            JSONObject jsonObjectClould = jsonObject.getJSONObject("clouds");
                            String may = jsonObjectClould.getString("all");
                            txtCloud.setText(may + "%");

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String country = jsonObjectSys.getString("country");
                            txtCity.setText("Tên thành phố: " + city + ", " + country);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }

    private void mapping() {
        txtName     = view.findViewById(R.id.textViewName);
        txtCity     = view.findViewById(R.id.textViewCity);
        txtTemp     = view.findViewById(R.id.textViewTemp);
        txtTempMaxMin = view.findViewById(R.id.textViewTempMaxMin);
        txtHumidity = view.findViewById(R.id.textViewHumidity);
        txtWind     = view.findViewById(R.id.textViewWind);
        txtStatus   = view.findViewById(R.id.textViewStatus);
        txtTitle    = view.findViewById(R.id.textViewTitle);
        txtCloud    = view.findViewById(R.id.textViewCloud);
        txtDay      = view.findViewById(R.id.textViewDay);
        txtScore    = view.findViewById(R.id.textViewScore);
        imgIcon     = view.findViewById(R.id.imageViewWeatherIcon);
        imgAvatar   = view.findViewById(R.id.circleImageViewAvatar);
        ibtnTriNho  = view.findViewById(R.id.imageButtonTriNho);
        ibtnTinhToan = view.findViewById(R.id.imageButtonTinhToan);
    }
}
