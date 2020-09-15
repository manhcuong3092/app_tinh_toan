package com.learn.tinhtoan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class ExerciseActivity extends AppCompatActivity {

    Button btnSubmit;
    RecyclerView recyclerView;
    int exactlyAnswerCount;
    ArrayList<Operation> opList;
    Intent intent;
    OperationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        mapping();

        intent = getIntent();
        //khoi tao list
        initOperationList();

        adapter = new OperationAdapter(opList, this);

        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < opList.size(); i++){
                    checkResultProcess(i);
                }
            }
        });

    }

    private void checkResultProcess(int i) {
        int answer = Integer.parseInt(opList.get(i).getAnswer());
        int operator = opList.get(i).getOperator();
        int a = opList.get(i).getA();
        int b = opList.get(i).getB();

        switch (operator) {
            case Operation.ADD:
                if (a + b == answer) {
                    opList.get(i).setStatus(Operation.EXACT);
                } else {
                    opList.get(i).setStatus(Operation.WRONG);
                }
                break;
            case Operation.SUBTRACT:
                if (a - b == answer) {
                    opList.get(i).setStatus(Operation.EXACT);
                } else {
                    opList.get(i).setStatus(Operation.WRONG);
                }
                break;
            case Operation.MULTIPLE:
                if (a * b == answer) {
                    opList.get(i).setStatus(Operation.EXACT);
                } else {
                    opList.get(i).setStatus(Operation.WRONG);
                }
                break;
            case Operation.DIVIDE:
                if (a / b == answer) {
                    opList.get(i).setStatus(Operation.EXACT);
                } else {
                    opList.get(i).setStatus(Operation.WRONG);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

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
            max = 100;
        } else if (normal) {
            max = 2000;
        } else if (hard) {
            max = 50000;
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
        btnSubmit = findViewById(R.id.buttonSubmit);
        recyclerView = findViewById(R.id.recyclerViewExercise);
        exactlyAnswerCount = 0;
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