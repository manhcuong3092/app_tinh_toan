package com.learn.tinhtoan.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.learn.tinhtoan.R;
import com.learn.tinhtoan.activity.MainActivity;
import com.learn.tinhtoan.model.DataUser;
import com.learn.tinhtoan.model.User;

import java.util.ArrayList;
import java.util.List;

public class StatisticFragment extends Fragment {
    User user = MainActivity.currentUser;
    DataUser dataUser = MainActivity.currentDataUser;
    AnyChartView acvDificult, acvType;
    ProgressBar pbRatio;
    TextView txtSoLan, txtSoPhepTinh, txtSoLuotMinigame, txtSoCauDung, txtRatio;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_statistic, container, false);
        anhXa();

        setUpView();
        setUpPieChartDificult();
        setUpPieChartType();

        return view;
    }

    private void setUpView() {
        txtSoLan.setText("Tổng số lượt tính: " + dataUser.getSoLanTinhToan());
        txtSoPhepTinh.setText("Tổng số phép tính: " + dataUser.getSoCauTraLoi());
        txtSoLuotMinigame.setText("Tổng số lượt chơi minigame: " + dataUser.getSoLanMiniGame());
        txtSoCauDung.setText("Số phép tính đúng: " + dataUser.getSoCauDung());

        int ratio = 0;
        //chua tra loi thi ti le = 0
        if(dataUser.getSoCauTraLoi() > 0){
            ratio = dataUser.getSoCauDung()*100 / dataUser.getSoCauTraLoi();
        }
        txtRatio.setText(ratio + "%");
        pbRatio.setProgress(ratio);
    }

    private void setUpPieChartType() {
        APIlib.getInstance().setActiveAnyChartView(acvType);
        Pie pie = AnyChart.pie();
        String[] types = {"Cộng", "Trừ", "Nhân", "Chia"};
        int soCauCong = dataUser.getSoCauCong();
        int soCauTru = dataUser.getSoCauTru();
        int soCauNhan = dataUser.getSoCauNhan();
        int soCauChia = dataUser.getSoCauChia();
        int[] amount = {soCauCong, soCauTru, soCauNhan, soCauChia};

        List<DataEntry> dataEntries = new ArrayList<>();

        for(int i = 0; i< types.length; i++){
            dataEntries.add(new ValueDataEntry(types[i], amount[i]));
            Log.d("AAAa", types[i] + ": " + amount[i]);
        }
        pie.data(dataEntries);
        pie.title("Tỉ lệ làm các loại phép tính");
        acvType.setChart(pie);
    }

    private void setUpPieChartDificult() {
        APIlib.getInstance().setActiveAnyChartView(acvDificult);
        Pie pie = AnyChart.pie();
        String[] dificults = {"Dễ", "Trung bình", "Khó"};
        int soCauDe = dataUser.getSoCauDe();
        int soCauTB = dataUser.getSoCauTB();
        int soCauKho = dataUser.getSoCauKho();
        int[] amount = {soCauDe, soCauTB, soCauKho};

        List<DataEntry> dataEntries = new ArrayList<>();

        for(int i = 0; i< dificults.length; i++){
            dataEntries.add(new ValueDataEntry(dificults[i], amount[i]));
        }
        pie.data(dataEntries);
        pie.title("Tỉ lệ làm phép tính của các mức độ");
        acvDificult.setChart(pie);
    }

    private void anhXa() {
        acvDificult = view.findViewById(R.id.anyChartViewDifficult);
        acvType = view.findViewById(R.id.anyChartViewType);
        pbRatio = view.findViewById(R.id.progressBarRatio);
        txtRatio = view.findViewById(R.id.textViewRatio);
        txtSoLan = view.findViewById(R.id.textViewTongSoLuot);
        txtSoCauDung = view.findViewById(R.id.textViewSoCauDung);
        txtSoLuotMinigame = view.findViewById(R.id.textViewTongMinigame);
        txtSoPhepTinh = view.findViewById(R.id.textViewTongSoPhepTinh);
    }

}
