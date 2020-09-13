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

import java.util.ArrayList;
import java.util.Random;

public class ExerciseActivity extends AppCompatActivity {

    Button btnSubmit;
    RecyclerView recyclerView;
    int exactlyAnswerCount;
    ArrayList<Operation> opList;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        mapping();

        intent = getIntent();
        //khoi tao
        initOperationList();

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        OperationAdapter adapter = new OperationAdapter(opList, this);
        recyclerView.setAdapter(adapter);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkResultProcess();
            }
        });

    }

    private void checkResultProcess() {
        for(int i = 0; i < opList.size(); i++){
            RecyclerView.ViewHolder view = recyclerView.findViewHolderForAdapterPosition(i);
            EditText edtAnswer = view.itemView.findViewById(R.id.editTextAnswer);
            ImageView imgResult = view.itemView.findViewById(R.id.imageViewResult);
            int answer = Integer.parseInt(edtAnswer.getText().toString());
            int operator = opList.get(i).getOperator();
            int a = opList.get(i).getA();
            int b = opList.get(i).getB();

            switch (operator) {
                case Operation.ADD:
                    if (a + b == answer) {
                        imgResult.setImageResource(R.drawable.correct);
                    } else {
                        imgResult.setImageResource(R.drawable.wrong);
                    }
                    break;
                case Operation.SUBTRACT:
                    if (a - b == answer) {
                        imgResult.setImageResource(R.drawable.correct);
                    } else {
                        imgResult.setImageResource(R.drawable.wrong);
                    }
                    break;
                case Operation.MULTIPLE:
                    if (a * b == answer) {
                        imgResult.setImageResource(R.drawable.correct);
                    } else {
                        imgResult.setImageResource(R.drawable.wrong);
                    }
                    break;
                case Operation.DIVIDE:
                    if (a / b == answer) {
                        imgResult.setImageResource(R.drawable.correct);
                    } else {
                        imgResult.setImageResource(R.drawable.wrong);
                    }
                    break;
            }
        }
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