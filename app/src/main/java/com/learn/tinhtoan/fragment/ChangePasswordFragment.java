package com.learn.tinhtoan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.learn.tinhtoan.R;
import com.learn.tinhtoan.activity.LoginActivity;
import com.learn.tinhtoan.activity.MainActivity;
import com.learn.tinhtoan.model.User;

public class ChangePasswordFragment extends Fragment {

    View view;
    EditText edtOldPassword, edtNewPassword, edtNewPasswordAgain;
    Button btnChanePassword;
    User user = MainActivity.currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_change_password, container, false);
        mapping();

        btnChanePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });

        return view;
    }

    private void changePassword() {
        String oldPass = edtOldPassword.getText().toString();
        if(!oldPass.equals(user.getPassword())){
            Toast.makeText(getActivity(), "Mật khẩu cũ không đúng.", Toast.LENGTH_SHORT).show();
            return;
        }
        String newPass = edtNewPassword.getText().toString();
        String newPassAgain = edtNewPasswordAgain.getText().toString();
        if(!newPass.equals(newPassAgain)){
            Toast.makeText(getActivity(), "Nhập lại mật khẩu không khớp.", Toast.LENGTH_SHORT).show();
            return;
        }
        user.setPassword(newPass);
        LoginActivity.database.updateUser(user);
        Toast.makeText(getActivity(), "Đổi mật khẩu thành công.", Toast.LENGTH_SHORT).show();

        edtNewPasswordAgain.setText("");
        edtNewPassword.setText("");
        edtOldPassword.setText("");
    }

    private void mapping() {
        edtOldPassword = view.findViewById(R.id.editTextOldPassword);
        edtNewPassword = view.findViewById(R.id.editTextNewPassword);
        edtNewPasswordAgain = view.findViewById(R.id.editTextNewPasswordAgain);
        btnChanePassword = view.findViewById(R.id.buttonChangePassword);
    }

}
