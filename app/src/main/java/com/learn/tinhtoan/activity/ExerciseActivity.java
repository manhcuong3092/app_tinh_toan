package com.learn.tinhtoan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.learn.tinhtoan.Database;
import com.learn.tinhtoan.model.Achievement;
import com.learn.tinhtoan.model.DataUser;
import com.learn.tinhtoan.model.Operation;
import com.learn.tinhtoan.adapter.OperationAdapter;
import com.learn.tinhtoan.R;
import com.learn.tinhtoan.model.User;
import com.learn.tinhtoan.model.UserAchievement;

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
    int soPhepCong, soPhepTru, soPhepNhan, soPhepChia;
    long timeConLai;
    CountDownTimer countDownTimer;
    ArrayList<Operation> opList;
    Intent intent;
    OperationAdapter adapter;

    //gán tham chiếu đến cùng địa chỉ ô nhớ
    User user = MainActivity.currentUser;
    DataUser dataUser = MainActivity.currentDataUser;
    UserAchievement userAchievement = MainActivity.currentUserAchievement;


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
                resultHandle();
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
                resultHandle();
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

        //kiểm tra dấu và tính điểm
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
    private void resultHandle() {
        int userScore = dataUser.getDiem();
        //diem = socaudung*heso - socausai*5
        score = score - (opList.size() - exactAnswerCount) * 5;
        //update userscore
        userScore += score;

        //update userData and userAchievement
        updateUserData();
        updateUserAchievement();

        showDiaglogResult(exactAnswerCount, score, userScore);
        btnSubmit.setEnabled(false);
    }


    private void updateUserData() {
        //update userData
        dataUser.setDiem(dataUser.getDiem() + score);
        dataUser.setSoCauCong(dataUser.getSoCauCong() + soPhepCong);
        dataUser.setSoCauTru(dataUser.getSoCauTru() + soPhepTru);
        dataUser.setSoCauNhan(dataUser.getSoCauNhan() + soPhepNhan);
        dataUser.setSoCauChia(dataUser.getSoCauChia() + soPhepChia);
        if(doKho == Operation.EASY)
            dataUser.setSoCauDe(dataUser.getSoCauDe() + opList.size());
        else if (doKho == Operation.NORMAL)
            dataUser.setSoCauDe(dataUser.getSoCauTB() + opList.size());
        else
            dataUser.setSoCauKho(dataUser.getSoCauKho() + opList.size());
        dataUser.setSoCauTraLoi(dataUser.getSoCauTraLoi() + opList.size());
        dataUser.setSoCauDung(dataUser.getSoCauDung() + exactAnswerCount);
        dataUser.setSoLanTinhToan(dataUser.getSoLanTinhToan() + 1);
        Database.updateUserData(dataUser);
    }

    private void updateUserAchievement() {
        //-------------------------------------
        //update userAchievement
        //kiem tra diem, set level
        int achievementScore = dataUser.getDiem();
        if(achievementScore < 0){
            userAchievement.setAmDiem(Achievement.BRONZE_TROPHY);
        }
        if(achievementScore >= 6700){
            userAchievement.setLevel(5);
            userAchievement.setTitle("VIP Pro");
        } else if (achievementScore >= 1700){
            userAchievement.setLevel(4);
            userAchievement.setTitle("Cao thủ");
        } else if (achievementScore >= 500){
            userAchievement.setLevel(3);
            userAchievement.setTitle("Chuyên nghiệp");
        } else if (achievementScore >= 100){
            userAchievement.setLevel(2);
            userAchievement.setTitle("Nghiệp dư");
        } else if (achievementScore >= 0){
            userAchievement.setLevel(1);
            userAchievement.setTitle("Tập sự");
        } else {
            userAchievement.setLevel(0);
            userAchievement.setTitle("Gà");
        }
        //sai het
        if (exactAnswerCount == 0){
            userAchievement.setSaiHet(Achievement.BRONZE_TROPHY);
        }
        //dung het
        if(exactAnswerCount == opList.size()){
            userAchievement.setDungHet(Achievement.BRONZE_TROPHY);
        }
        //muc do kho
        if (dataUser.getSoCauKho() >= 30){
            userAchievement.setClearHard(Achievement.GOLD_TROPHY);
        } else if (dataUser.getSoCauKho() >= 20){
            userAchievement.setClearHard(Achievement.SILVER_TROPHY);
        } else if (dataUser.getSoCauKho() >= 10){
            userAchievement.setClearHard(Achievement.BRONZE_TROPHY);
        }
        //so cau lam dung
        if(dataUser.getSoCauDung() >= 150){
            userAchievement.setClearOperators(Achievement.GOLD_TROPHY);
        } else if (dataUser.getSoCauDung() >= 75){
            userAchievement.setClearOperators(Achievement.SILVER_TROPHY);
        } else if (dataUser.getSoCauDung() >= 20){
            userAchievement.setClearOperators(Achievement.BRONZE_TROPHY);
        }
        //so phep tinh cộng
        if(dataUser.getSoCauCong() >= 100){
            userAchievement.setClearAdd(Achievement.GOLD_TROPHY);
        } else if(dataUser.getSoCauCong() >= 50){
            userAchievement.setClearAdd(Achievement.SILVER_TROPHY);
        } else if(dataUser.getSoCauCong() >= 15){
            userAchievement.setClearAdd(Achievement.BRONZE_TROPHY);
        }
        //so phep tinh trừ
        if(dataUser.getSoCauTru() >= 100){
            userAchievement.setClearSub(Achievement.GOLD_TROPHY);
        } else if(dataUser.getSoCauTru() >= 50){
            userAchievement.setClearSub(Achievement.SILVER_TROPHY);
        } else if(dataUser.getSoCauTru() >= 15){
            userAchievement.setClearSub(Achievement.BRONZE_TROPHY);
        }
        //so phep tinh nhân
        if(dataUser.getSoCauNhan() >= 100){
            userAchievement.setClearMul(Achievement.GOLD_TROPHY);
        } else if(dataUser.getSoCauNhan() >= 50){
            userAchievement.setClearMul(Achievement.SILVER_TROPHY);
        } else if(dataUser.getSoCauNhan() >= 15){
            userAchievement.setClearMul(Achievement.BRONZE_TROPHY);
        }
        //so phep tinh chia
        if(dataUser.getSoCauChia() >= 100){
            userAchievement.setClearDiv(Achievement.GOLD_TROPHY);
        } else if(dataUser.getSoCauChia() >= 50){
            userAchievement.setClearDiv(Achievement.SILVER_TROPHY);
        } else if(dataUser.getSoCauChia() >= 15){
            userAchievement.setClearDiv(Achievement.BRONZE_TROPHY);
        }
        //tính thời gian nhanh
        if((soGiay*opList.size()*1000 - timeConLai)/opList.size() <= 5000 && opList.size() == exactAnswerCount
            && opList.size() >= 10){
            //dưới 5 giây và ít nhất 10 câu
            userAchievement.setUnder15Seconds(Achievement.GOLD_TROPHY);
        } else if ((soGiay*opList.size()*1000 - timeConLai) <= 10000 && opList.size() == exactAnswerCount
                && opList.size() >= 10){
            if(userAchievement.getUnder15Seconds()/opList.size() < Achievement.GOLD_TROPHY){
                userAchievement.setUnder15Seconds(Achievement.SILVER_TROPHY);
            }
        } else if ((soGiay*opList.size()*1000 - timeConLai) <= 15000 && opList.size() == exactAnswerCount
                && opList.size() >= 10){
            if(userAchievement.getUnder15Seconds()/opList.size() < Achievement.SILVER_TROPHY){
                userAchievement.setUnder15Seconds(Achievement.BRONZE_TROPHY);
            }
        }
        Database.updateUserAchievement(userAchievement);
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

        //dem so luong phep tinh
        soPhepCong = 0;
        soPhepTru = 0;
        soPhepNhan = 0;
        soPhepChia = 0;

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
                        soPhepCong++;
                        i++;
                    }
                    break;
                case Operation.SUBTRACT:
                    if (phepTru) {
                        a = random.nextInt(max) + 1;
                        b = random.nextInt(max) + 1;
                        opList.add(new Operation(a, b, operator));
                        soPhepTru++;
                        i++;
                    }
                    break;
                case Operation.MULTIPLE:
                    if (phepNhan) {
                        a = random.nextInt(max) + 1;
                        b = random.nextInt(max) + 1;
                        opList.add(new Operation(a, b, operator));
                        soPhepNhan++;
                        i++;
                    }
                    break;
                case Operation.DIVIDE:
                    if (phepChia) {
                        a = random.nextInt(max) + 1;
                        b = Math.round((random.nextInt(max) + 1) / 10) + 1;
                        opList.add(new Operation(a, b, operator));
                        soPhepChia++;
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