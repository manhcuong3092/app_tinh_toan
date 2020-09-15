package com.learn.tinhtoan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class ExerciseActivity extends AppCompatActivity {

    ImageButton imgRetry, imgBack;
    Button btnSubmit;
    RecyclerView recyclerView;
    int exactAnswerCount, heSo;
    ArrayList<Operation> opList;
    Intent intent;
    OperationAdapter adapter;
    User user = MainActivity.currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        mapping();

        intent = getIntent();
        //khoi tao list
        initOperationList();
        adapter = new OperationAdapter(opList, this);

        //khởi tạo recycler view
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exactAnswerCount = 0;
                for (int i = 0; i < opList.size(); i++){
                    checkResultProcess(i);
                }
            }
        });

        imgRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opList.clear();
                initOperationList();
                Log.d("AAA", opList.size() + "");
                btnSubmit.setEnabled(true);
                adapter = new OperationAdapter(opList, ExerciseActivity.this);
                recyclerView.setAdapter(adapter);
            }
        });

    }

    //ham kiểm tra kết quả
    private void checkResultProcess(int i) {
        String answerString = opList.get(i).getAnswer();
        if(answerString.equals("")){
            return;
        }
        int answer = Integer.parseInt(answerString);
        int operator = opList.get(i).getOperator();
        int a = opList.get(i).getA();
        int b = opList.get(i).getB();

        //kiểm tra dấu
        switch (operator) {
            case Operation.ADD:
                if (a + b == answer) {
                    opList.get(i).setStatus(Operation.EXACT);
                    exactAnswerCount++;
                } else {
                    opList.get(i).setStatus(Operation.WRONG);
                }
                break;
            case Operation.SUBTRACT:
                if (a - b == answer) {
                    opList.get(i).setStatus(Operation.EXACT);
                    exactAnswerCount++;
                } else {
                    opList.get(i).setStatus(Operation.WRONG);
                }
                break;
            case Operation.MULTIPLE:
                if (a * b == answer) {
                    opList.get(i).setStatus(Operation.EXACT);
                    exactAnswerCount++;
                } else {
                    opList.get(i).setStatus(Operation.WRONG);
                }
                break;
            case Operation.DIVIDE:
                if (a / b == answer) {
                    opList.get(i).setStatus(Operation.EXACT);
                    exactAnswerCount++;
                } else {
                    opList.get(i).setStatus(Operation.WRONG);
                }
                break;
        }
        adapter.notifyDataSetChanged();

        resultHandle(exactAnswerCount);
    }

    //xủe lý kết quả
    private void resultHandle(int countExactAnswer) {
        Cursor cursor = Database.findUserData(user.getId());
        int score = 0, userScore = 0;
        if(cursor.moveToFirst() && cursor.getCount() > 0){
            userScore = cursor.getInt(1);
        }
        //diem = socaudung*heso - socausai*5
        score = countExactAnswer*heSo - (opList.size() - countExactAnswer)*5;
        userScore += score;

        showDiaglogResult(countExactAnswer, score, userScore);
        btnSubmit.setEnabled(false);
    }

    //hiện dialog kết quả
    private void showDiaglogResult(int exactAnswerCount, int score, int userScore) {
        //Dialog
        final Dialog dialog = new Dialog(ExerciseActivity.this);
        dialog.setContentView(R.layout.dialog_result);
        dialog.setCanceledOnTouchOutside(false);

        TextView txtName, txtScore, txtUserScore, txtResultTime, txtResultDate, txtResultCount;
        Button btnOk;
        //anh xa
        txtName         = dialog.findViewById(R.id.textViewNameResult);
        txtResultCount  = dialog.findViewById(R.id.textViewResultCount);
        txtScore        = dialog.findViewById(R.id.textViewScore);
        txtUserScore    = dialog.findViewById(R.id.textViewUserScore);
        txtResultTime   = dialog.findViewById(R.id.textViewResultTime);
        txtResultDate   = dialog.findViewById(R.id.textViewResultDate);
        btnOk           = dialog.findViewById(R.id.buttonOk);

        txtName.setText(user.getName());
        txtResultCount.setText("Số câu đúng: " + exactAnswerCount + "/" + opList.size());
        txtScore.setText("Điểm: " + score);
        txtUserScore.setText("Tổng điểm: " + userScore);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HH:mm");
        Calendar calendar = Calendar.getInstance();
        String date = sdf.format(calendar.getTime());
        txtResultDate.setText("Vào lúc: " + date);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //khởi tạo các biểu thức
    private void initOperationList() {
        opList = new ArrayList<>();

        int soCau = intent.getIntExtra("soCau", 0);
        int soGiay = intent.getIntExtra("soGiay", 0);
        boolean phepCong = intent.getBooleanExtra("phepCong", false);
        boolean phepTru = intent.getBooleanExtra("phepTru", false);
        boolean phepNhan = intent.getBooleanExtra("phepNhan", false);
        boolean phepChia = intent.getBooleanExtra("phepChia", false);
        boolean easy = intent.getBooleanExtra("easy", false);
        boolean normal = intent.getBooleanExtra("normal", false);
        boolean hard = intent.getBooleanExtra("hard", false);

        //tao muc do
        int max = 5;
        if (easy) {
            heSo = 3;
            max = 50;
        } else if (normal) {
            heSo = 10;
            max = 1000;
        } else if (hard) {
            heSo = 25;
            max = 20000;
        }


        Random random = new Random();
        int operator, a, b;
        int i = 0;


        while (i < soCau) {
            operator = random.nextInt(4);
            switch (operator) {
                case Operation.ADD:
                    if (phepCong) {
                        a = random.nextInt(max) + 1;
                        b = random.nextInt(max) + 1;
                        opList.add(new Operation(a, b, operator));
                        i++;
                    }
                    break;
                case Operation.SUBTRACT:
                    if (phepTru) {
                        a = random.nextInt(max) + 1;
                        b = random.nextInt(max) + 1;
                        opList.add(new Operation(a, b, operator));
                        i++;
                    }
                    break;
                case Operation.MULTIPLE:
                    if (phepNhan) {
                        a = random.nextInt(max) + 1;
                        b = random.nextInt(max) + 1;
                        opList.add(new Operation(a, b, operator));
                        i++;
                    }
                    break;
                case Operation.DIVIDE:
                    if (phepChia) {
                        a = random.nextInt(max) + 1;;
                        b = Math.round((random.nextInt(max) + 1)/10) + 1;
                        opList.add(new Operation(a, b, operator));
                        i++;
                    }
                    break;
            }
        }
    }

    private void mapping() {
        imgRetry = findViewById(R.id.imageButtonRetry);
        imgBack = findViewById(R.id.imageButtonBack);
        btnSubmit = findViewById(R.id.buttonSubmit);
        recyclerView = findViewById(R.id.recyclerViewExercise);
        exactAnswerCount = 0;
    }

//    public boolean check(int a, int b, int answer, String operator){
//        switch (operator){
//            case "+":
//                if(a + b == answer) return true;
//                else return false;
//            case "-":
//                if(a - b == answer) return true;
//                else return false;
//            case "x":
//                if(a * b == answer) return true;
//                else return false;
//            case "÷":
//                if(Math.round(((double) a/b)*100)/100 == answer) return true;
//                else return false;
//        }
//        return false;
//    }
}