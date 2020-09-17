package com.learn.tinhtoan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.learn.tinhtoan.ExerciseActivity;
import com.learn.tinhtoan.R;

public class ExerciseFragment extends androidx.fragment.app.Fragment {

    View view;

    CheckBox cbAdd, cbSubtract, cbMultiple, cbDivide;
    RadioGroup radioGroup;
    RadioButton rbEasy, rbNormal, rbHard;
    Button btn10Cau, btn15Cau, btn20Cau, btn20Giay, btn30Giay, btn45Giay, btnStart;
    EditText edtSoCau, edtSoGiay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exercise, container, false);
        mapping();

        btn10Cau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSoCau.setText("10");
            }
        });

        btn15Cau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSoCau.setText("15");
            }
        });

        btn20Cau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSoCau.setText("20");
            }
        });

        btn20Giay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSoGiay.setText("20");
            }
        });

        btn30Giay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSoGiay.setText("30");
            }
        });

        btn45Giay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSoGiay.setText("45");
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processForm();
            }
        });

        return view;
    }

    private void processForm() {
        if(checkForm()) {
            Intent intent = new Intent(getActivity(), ExerciseActivity.class);

            String soCauString = edtSoCau.getText().toString();
            String soGiayString = edtSoGiay.getText().toString();

            int soCau = Integer.parseInt(soCauString);
            int soGiay = Integer.parseInt(soGiayString);

            intent.putExtra("soCau", soCau);
            intent.putExtra("soGiay", soGiay);

            if(cbAdd.isChecked()){
                intent.putExtra("phepCong", true);
            } else {
                intent.putExtra("phepCong", false);
            }

            if(cbSubtract.isChecked()){
                intent.putExtra("phepTru", true);
            } else {
                intent.putExtra("phepTru", false);
            }

            if(cbMultiple.isChecked()){
                intent.putExtra("phepNhan", true);
            } else {
                intent.putExtra("phepNhan", false);
            }

            if(cbDivide.isChecked()){
                intent.putExtra("phepChia", true);
            } else {
                intent.putExtra("phepChia", false);
            }

            if(rbEasy.isChecked()){
                intent.putExtra("easy", true);
            } else {
                intent.putExtra("easy", false);
            }

            if(rbNormal.isChecked()){
                intent.putExtra("normal", true);
            } else {
                intent.putExtra("normal", false);
            }

            if(rbHard.isChecked()){
                intent.putExtra("hard", true);
            } else {
                intent.putExtra("hard", false);
            }
            startActivity(intent);
        }
    }

    private void mapping() {
        radioGroup  = view.findViewById(R.id.radioGroupExercise);
        cbAdd       = view.findViewById(R.id.checkBoxAdd);
        cbSubtract  = view.findViewById(R.id.checkBoxSubtract);
        cbMultiple  = view.findViewById(R.id.checkBoxMultiple);
        cbDivide    = view.findViewById(R.id.checkBoxDivide);
        rbEasy      = view.findViewById(R.id.radioButtonEasy);
        rbNormal    = view.findViewById(R.id.radioButtonNormal);
        rbHard      = view.findViewById(R.id.radioButtonHard);
        btn10Cau    = view.findViewById(R.id.button10Cau);
        btn15Cau    = view.findViewById(R.id.button15Cau);
        btn20Cau    = view.findViewById(R.id.button20Cau);
        btn20Giay   = view.findViewById(R.id.button20Giay);
        btn30Giay   = view.findViewById(R.id.button30Giay);
        btn45Giay   = view.findViewById(R.id.button45Giay);
        btnStart    = view.findViewById(R.id.buttonStart);
        edtSoCau    = view.findViewById(R.id.editTextSoCauHoi);
        edtSoGiay   = view.findViewById(R.id.editTextSoGiay);
    }

    private boolean checkForm(){
        String soCau = edtSoCau.getText().toString();
        String soGiay = edtSoGiay.getText().toString();

        //kiem tra dien editext
        if(soCau.length() == 0 || soGiay.length() == 0){
            Toast.makeText(getActivity(), "Vui lòng chọn số câu hoặc số giây.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(Integer.parseInt(soCau) > 200 || Integer.parseInt(soCau) > 200){
            Toast.makeText(getActivity(), "Tối đa 200 câu hoặc 200 giây.", Toast.LENGTH_SHORT).show();
        }

        //kiem tra checkbox
        if(!cbAdd.isChecked() && !cbSubtract.isChecked() && !cbMultiple.isChecked() && !cbDivide.isChecked()){
            Toast.makeText(getActivity(), "Vui lòng chọn ít nhất 1 phép tính.", Toast.LENGTH_SHORT).show();
            return false;
        }

        //form hop le
        return true;
    }
}
