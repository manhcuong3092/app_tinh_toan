package com.learn.tinhtoan.fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.learn.tinhtoan.Database;
import com.learn.tinhtoan.R;
import com.learn.tinhtoan.activity.LoginActivity;
import com.learn.tinhtoan.activity.MainActivity;
import com.learn.tinhtoan.model.User;
import com.learn.tinhtoan.model.UserProfile;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    final int REQUEST_CODE_CAMERA = 123;
    final int REQUEST_CODE_FOLDER = 456;

    User user = MainActivity.currentUser;
    UserProfile userProfile = MainActivity.currentUserProfile;
    View view;
    EditText edtFullName, edtDateOfBirth, edtPhone, edtAddress, edtEmail;
    RadioGroup rgGender;
    RadioButton rbMale, rbFemale;
    ImageButton ibtnEdit, ibtnCamera, ibtnGallery;
    ImageView imgAvatar;
    Button btnFinish;
    Database database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        mapping();
        database = new Database(container.getContext());
        //Ẩn các button khi chưa nhấn edit
        loadUserProfile();
        setDisableView();

        ibtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEnableView();
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUserProfile();
                Toast.makeText(getActivity(), "Thay đổi thông tin thành công.", Toast.LENGTH_SHORT).show();
                setDisableView();
            }
        });

        ibtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }
        });

        ibtnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            }
        });

        edtDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseDate();
            }
        });

        return view;
    }

    private void chooseDate() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                edtDateOfBirth.setText(sdf.format(calendar.getTime()));
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void loadUserProfile() {
        //avatar
        byte[] avatar = user.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
        imgAvatar.setImageBitmap(bitmap);
        //profile
        edtAddress.setText(userProfile.getAddress());
        edtDateOfBirth.setText(userProfile.getDateOfBirth());
        edtEmail.setText(userProfile.getEmail());
        edtFullName.setText(userProfile.getFullname());
        edtPhone.setText(userProfile.getPhone());
        if(userProfile.getGender() == 0){
            rbMale.setChecked(true);
        } else rbFemale.setChecked(false);
    }


    private void setUserProfile() {
        //Xu ly bitmap
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgAvatar.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
        byte[] hinhAnh = byteArray.toByteArray();
        user.setImage(hinhAnh);

        userProfile.setFullname(edtFullName.getText().toString());
        userProfile.setDateOfBirth(edtDateOfBirth.getText().toString());
        userProfile.setEmail(edtEmail.getText().toString());
        userProfile.setPhone(edtPhone.getText().toString());
        userProfile.setAddress(edtAddress.getText().toString());
        int gender = 0;
        if(rbFemale.isChecked()){
            gender = 1;
        } else {
            gender = 0;
        }
        userProfile.setGender(gender);
        database.updateUser(user);
        database.updateUserProfile(userProfile);
    }

    private void setEnableView() {
        ibtnCamera.setVisibility(View.VISIBLE);
        ibtnGallery.setVisibility(View.VISIBLE);
        btnFinish.setVisibility(View.VISIBLE);
        edtFullName.setFocusableInTouchMode(true);
        edtPhone.setFocusableInTouchMode(true);
        edtEmail.setFocusableInTouchMode(true);
        edtDateOfBirth.setClickable(true);
        edtAddress.setFocusableInTouchMode(true);
        rbMale.setClickable(true);
        rbFemale.setClickable(true);
    }

    private void setDisableView() {
        ibtnCamera.setVisibility(View.INVISIBLE);
        ibtnGallery.setVisibility(View.INVISIBLE);
        btnFinish.setVisibility(View.INVISIBLE);
        edtFullName.setFocusable(false);
        edtPhone.setFocusable(false);
        edtEmail.setFocusable(false);
        edtDateOfBirth.setFocusable(false);
        edtDateOfBirth.setClickable(false);
        edtAddress.setFocusable(false);
        rbMale.setClickable(false);
        rbFemale.setClickable(false);
    }

    private void mapping() {
        edtAddress = view.findViewById(R.id.editTextAddress);
        edtDateOfBirth = view.findViewById(R.id.editTextDateOfBirth);
        edtEmail = view.findViewById(R.id.editTextEmailProfile);
        edtPhone = view.findViewById(R.id.editTextPhoneProfile);
        edtFullName = view.findViewById(R.id.editTextFullname);
        rgGender = view.findViewById(R.id.radioGroupGender);
        rbFemale = view.findViewById(R.id.radioButtonFemale);
        rbMale = view.findViewById(R.id.radioButtonMale);
        imgAvatar = view.findViewById(R.id.imageViewAvatarProfile);
        ibtnCamera = view.findViewById(R.id.imageButtonCameraProfile);
        ibtnGallery = view.findViewById(R.id.imageButtonGalleryProfile);
        ibtnEdit = view.findViewById(R.id.imageButtonEditProfile);
        btnFinish = view.findViewById(R.id.buttonFinishEdit);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("AAA", requestCode + " + " + resultCode + " + " );
        //set bit map cho hinh
        if(requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgAvatar.setImageBitmap(bitmap);
        } else if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgAvatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
