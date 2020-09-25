package com.learn.tinhtoan.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

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
import com.learn.tinhtoan.model.User;
import com.learn.tinhtoan.model.UserAchievement;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AchievementFragment extends androidx.fragment.app.Fragment {

    User user = MainActivity.currentUser;
    UserAchievement userAchievement = MainActivity.currentUserAchievement;
    DataUser dataUser = MainActivity.currentDataUser;
    ArrayList<Achievement> achieveList;
    View view;
    RecyclerView recyclerView;
    AchievementAdapter adapter;
    CircleImageView imgAvatar;
    TextView txtLevel, txtScore, txtExp, txtTitle, txtTrophy;
    SeekBar sbFlag;
    ProgressBar pbExp;

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

        //set các view trong fragment
        setAvatar();
        txtLevel.setText("Level " + userAchievement.getLevel() + ": ");
        txtScore.setText("Điểm: " + dataUser.getDiem() + "");
        txtTitle.setText(userAchievement.getTitle());
        setExp();
        setTrophy();
        setFlag();

        return view;
    }

    private void setFlag() {
        sbFlag.setMax(5);
        sbFlag.setProgress(userAchievement.getLevel());
        sbFlag.setSecondaryProgress(userAchievement.getLevel() + 1);
    }

    private void setTrophy() {
        int count = 0;
        for(int i = 0; i < achieveList.size(); i++){
            if(achieveList.get(i).getTrophy() > 0){
                count++;
            }
        }
        txtTrophy.setText("Số thành tựu đã đạt được: " + count + "/" + achieveList.size());
    }

    private void setExp() {
        int score = dataUser.getDiem();
        int lv1 = Achievement.lv1Tolv2;
        int lv2 = Achievement.lv2Tolv3;
        int lv3 = Achievement.lv3Tolv4;
        int lv4 = Achievement.lv4Tolv5;
        switch (userAchievement.getLevel()){
            case 0:
                pbExp.setProgress(0);
                pbExp.setMax(1);
                txtExp.setText(score + "/0");
                break;
            case 1:
                pbExp.setProgress(score);
                pbExp.setMax(lv1);
                txtExp.setText(pbExp.getProgress() + "/" + pbExp.getMax());
                break;
            case 2:
                pbExp.setProgress(score - lv1);
                pbExp.setMax(lv2 - lv1);
                txtExp.setText(pbExp.getProgress() + "/" + pbExp.getMax());
                break;
            case 3:
                pbExp.setProgress(score - lv1 - lv2);
                pbExp.setMax(lv3 - lv2 -lv1);
                txtExp.setText(pbExp.getProgress() + "/" + pbExp.getMax());
                break;
            case 4:
                pbExp.setProgress(score - lv1 - lv2- lv3);
                pbExp.setMax(lv4 - lv3 - lv2 -lv1);
                txtExp.setText(pbExp.getProgress() + "/" + pbExp.getMax());
                break;
            case 5:
                pbExp.setProgress(score - lv1 - lv2- lv3 - lv4);
                pbExp.setMax(score - lv1 - lv2- lv3 - lv4);
                txtExp.setText(pbExp.getProgress() + "/" + "∞");
                break;
        }
    }

    private void setAvatar() {
        byte[] avatar = user.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
        imgAvatar.setImageBitmap(bitmap);
    }

    private void mapping() {
        recyclerView = view.findViewById(R.id.recyclerViewAchievement);
        imgAvatar = view.findViewById(R.id.circleImageAvatarAchievement);
        txtLevel = view.findViewById(R.id.textViewLevelAchevement);
        txtScore = view.findViewById(R.id.textViewScoreAchievement);
        txtExp = view.findViewById(R.id.textViewExp);
        txtTrophy = view.findViewById(R.id.textViewTrophyAchievement);
        txtTitle = view.findViewById(R.id.textViewTitleAchievement);
        sbFlag = view.findViewById(R.id.seekBarFlag);
        pbExp = view.findViewById(R.id.progressBarExp);
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
