package com.learn.tinhtoan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.tinhtoan.R;
import com.learn.tinhtoan.activity.MainActivity;
import com.learn.tinhtoan.adapter.AchievementAdapter;
import com.learn.tinhtoan.model.Achievement;
import com.learn.tinhtoan.model.DataUser;
import com.learn.tinhtoan.model.UserAchievement;

import java.util.ArrayList;

public class AchievementFragment extends androidx.fragment.app.Fragment {

    UserAchievement userAchievement = MainActivity.currentUserAchievement;
    DataUser dataUser = MainActivity.currentDataUser;
    ArrayList<Achievement> achieveList;
    View view;
    RecyclerView recyclerView;
    AchievementAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createAchieveList();
        view = inflater.inflate(R.layout.fragment_achievement, container, false);
        mapping();

        adapter = new AchievementAdapter(achieveList, getActivity());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    private void mapping() {
        recyclerView = view.findViewById(R.id.recyclerViewAchievement);
    }

    private void createAchieveList() {
        achieveList = new ArrayList<>();
        achieveList.add(new Achievement("Làm đúng hết",
                checkAchieve(userAchievement.getDungHet()), userAchievement.getDungHet()));
        achieveList.add(new Achievement("Làm sai hết",
                checkAchieve(userAchievement.getSaiHet()), userAchievement.getSaiHet()));
        achieveList.add(new Achievement("Âm điểm",
                checkAchieve(userAchievement.getAmDiem()), userAchievement.getAmDiem()));
        achieveList.add(new Achievement("Làm đúng 20/75/150 phép tính",
                dataUser.getSoCauDung() + "", userAchievement.getClearOperators()));
        achieveList.add(new Achievement("Làm 15/50/100 phép tính khó",
                dataUser.getSoCauKho() + "", userAchievement.getClearHard()));
        achieveList.add(new Achievement("Làm 15/50/100 phép tính cộng",
                dataUser.getSoCauCong() + "", userAchievement.getClearAdd()));
        achieveList.add(new Achievement("Làm 15/50/100 phép tính trừ",
                dataUser.getSoCauTru() + "", userAchievement.getClearSub()));
        achieveList.add(new Achievement("Làm 15/50/100 phép tính nhân",
                dataUser.getSoCauNhan() + "", userAchievement.getClearMul()));
        achieveList.add(new Achievement("Làm 15/50/100 phép tính chia",
                dataUser.getSoCauChia() + "", userAchievement.getClearDiv()));
        achieveList.add(new Achievement("Hoàn thành mỗi phép tính nhanh hơn 5/10/15 giây (ít nhất 10 phép tính)",
                checkAchieve(userAchievement.getUnder15Seconds()), userAchievement.getUnder15Seconds()));
        achieveList.add(new Achievement("Chơi 10/20/30 lần minigame",
                dataUser.getSoLanMiniGame() + "", userAchievement.getClearMinigames()));

    }

    private String checkAchieve(int param){
        if (param > 0) return "Đã đạt";
        return "Chưa đạt.";
    }

}
