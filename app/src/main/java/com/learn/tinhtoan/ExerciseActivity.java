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
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class ExerciseActivity extends AppCompatActivity {

    ImageButton ibtnRetry, ibtnBack;
    TextView txtTime;
    Button btnSubmit;
    RecyclerView recyclerView;
    int score, doKho, soGiay, exactAnswerCount;
    long timeConLai;
    CountDownTimer countDownTimer;
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
                score = 0;
                exactAnswerCount = 0;
                for (int i = 0; i < opList.size(); i++) {
                    checkResultProcess(i);
                }
                cancelCountDownTimer();
                resultHandle(exactAnswerCount);
            }
        });

        ibtnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opList.clear();
                initOperationList();
                btnSubmit.setEnabled(true);
                adapter = new OperationAdapter(opList, ExerciseActivity.this);
                recyclerView.setAdapter(adapter);
                //đặt countdown timer
                cancelCountDownTimer();
                setCountDownTimer(soGiay * opList.size() * 1000);
            }
        });

        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    //Đặt thời gian đếm ngược
    private void setCountDownTimer(long timer) {
        timeConLai = timer;
        countDownTimer = new CountDownTimer(timer, 1000) {
            @Override
            public void onTick(long l) {
                timeConLai = timeConLai - 1000;
                txtTime.setText(millisecondsToMinutes(timeConLai));
            }

            @Override
            public void onFinish() {
                Toast.makeText(ExerciseActivity.this, "Nộp bài", Toast.LENGTH_LONG).show();
                score = 0;
                for (int i = 0; i < opList.size(); i++) {
                    checkResultProcess(i);
                }
                resultHandle(score);
            }
        };
        countDownTimer.start();
    }

    //Hàm đổi milisecond sang phút giây
    private String millisecondsToMinutes(long milliseconds) {
        long minutes = (milliseconds / 1000) / 60;
        long seconds = (milliseconds / 1000) % 60;
        if (seconds < 10) {
            return String.format("%d:0%d", minutes, seconds);
        }
        return String.format("%d:%2d", minutes, seconds);
    }

    //ham kiểm tra kết quả
    private void checkResultProcess(int i) {
        String answerString = opList.get(i).getAnswer();
        if (answerString.equals("")) {
            answerString = "-982183841";    //null
        }
        int answer = Integer.parseInt(answerString);
        int operator = opList.get(i).getOperator();
        int a = opList.get(i).getA();
        int b = opList.get(i).getB();
        int r = 0;
        if (!opList.get(i).getRemainderAnswer().equals("")) {
            r = Integer.parseInt(opList.get(i).getRemainderAnswer());
        }

        //kiểm tra dấu
        switch (operator) {
            case Operation.ADD:
                opList.get(i).setExactAnswer("Đáp án: " + (a + b));
                if (a + b == answer) {
                    opList.get(i).setStatus(Operation.EXACT);
                    score = score + doKho * Operation.ADD_SUBTRACT_COEFFICIENT;
                    exactAnswerCount++;
                } else {
                    opList.get(i).setStatus(Operation.WRONG);
                }
                break;
            case Operation.SUBTRACT:
                opList.get(i).setExactAnswer("Đáp án: " + (a - b));
                if (a - b == answer) {
                    opList.get(i).setStatus(Operation.EXACT);
                    score = score + doKho * Operation.ADD_SUBTRACT_COEFFICIENT;
                    exactAnswerCount++;
                } else {
                    opList.get(i).setStatus(Operation.WRONG);
                }
                break;
            case Operation.MULTIPLE:
                opList.get(i).setExactAnswer("Đáp án: " + (a * b));
                if (a * b == answer) {
                    opList.get(i).setStatus(Operation.EXACT);
                    score = score + doKho * Operation.MULTIPLE_COEFFICIENT;
                    exactAnswerCount++;
                } else {
                    opList.get(i).setStatus(Operation.WRONG);
                }
                break;
            case Operation.DIVIDE:
                opList.get(i).setExactAnswer("Đáp án: " + (a / b) + " dư " + (a % b));
                if (a / b == answer && a % b == r) {
                    opList.get(i).setStatus(Operation.EXACT);
                    score = score + doKho * Operation.DIVIDE_COEFFICIENT;
                    exactAnswerCount++;
                } else {
                    opList.get(i).setStatus(Operation.WRONG);
                }
                break;
        }

        Log.d("AAAb", opList.get(i).getExactAnswer() + " dasdas ");
        adapter.notifyDataSetChanged();
    }

    //xủe lý kết quả
    private void resultHandle(int exactAnswerCount) {
        Cursor cursor = Database.findUserData(user.getId());
        int userScore = 0, soLanTinhToan = 0;
        if (cursor.moveToFirst() && cursor.getCount() > 0) {
            userScore = cursor.getInt(1);
            soLanTinhToan = cursor.getInt(9);
        }
        //diem = socaudung*heso - socausai*5
        score = score - (opList.size() - exactAnswerCount) * 5;

        //update userscore
        userScore += score;
        Database.updateScore(user.getId(), userScore, soLanTinhToan);

        showDiaglogResult(exactAnswerCount, score, userScore);
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
        txtName = dialog.findViewById(R.id.textViewNameResult);
        txtResultCount = dialog.findViewById(R.id.textViewResultCount);
        txtScore = dialog.findViewById(R.id.textViewScore);
        txtUserScore = dialog.findViewById(R.id.textViewUserScore);
        txtResultTime = dialog.findViewById(R.id.textViewResultTime);
        txtResultDate = dialog.findViewById(R.id.textViewResultDate);
        btnOk = dialog.findViewById(R.id.buttonOk);

        txtName.setText(user.getName());
        txtResultCount.setText("Số câu đúng: " + exactAnswerCount + "/" + opList.size());
        txtScore.setText("Điểm: " + score);
        txtUserScore.setText("Tổng điểm: " + userScore);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HH:mm");
        Calendar calendar = Calendar.getInstance();
        String date = sdf.format(calendar.getTime());
        txtResultDate.setText("Vào lúc: " + date);
        txtResultTime.setText("Thời gian làm: " + millisecondsToMinutes(soGiay*opList.size()*1000 - timeConLai));

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    //khởi tạo các biểu thức
    private void initOperationList() {
        opList = new ArrayList<>();

        int soCau = intent.getIntExtra("soCau", 0);
        soGiay = intent.getIntExtra("soGiay", 0);
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
            doKho = Operation.EASY;
            max = 50;
        } else if (normal) {
            doKho = Operation.NORMAL;
            max = 1000;
        } else if (hard) {
            doKho = Operation.HARD;
            max = 20000;
        }


        Random random = new Random();
        int operator, a, b;
        int i = 0;

        //sinh ngau nhien phep tinh
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
                        a = random.nextInt(max) + 1;
                        ;
                        b = Math.round((random.nextInt(max) + 1) / 10) + 1;
                        opList.add(new Operation(a, b, operator));
                        i++;
                    }
                    break;
            }
        }

        //đặt countdown timer
        cancelCountDownTimer();
        setCountDownTimer(soGiay * opList.size() * 1000 + 1000);
    }

    private void cancelCountDownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void mapping() {
        ibtnRetry = findViewById(R.id.imageButtonRetry);
        ibtnBack = findViewById(R.id.imageButtonBack);
        btnSubmit = findViewById(R.id.buttonSubmit);
        recyclerView = findViewById(R.id.recyclerViewExercise);
        txtTime = findViewById(R.id.textViewTime);
        score = 0;
    }

}