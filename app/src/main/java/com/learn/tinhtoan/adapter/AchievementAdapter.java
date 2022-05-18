package com.learn.tinhtoan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.tinhtoan.model.Achievement;
import com.learn.tinhtoan.R;

import java.util.ArrayList;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.ViewHolder> {

    ArrayList<Achievement> achieveList;
    Context context;

    public AchievementAdapter(ArrayList<Achievement> achieveList, Context context) {
        this.achieveList = achieveList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle, txtCurrent;
        ImageView imgTrophy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgTrophy = itemView.findViewById(R.id.imageViewTrophy);
            txtTitle = itemView.findViewById(R.id.textViewTrophyTitle);
            txtCurrent = itemView.findViewById(R.id.textViewTrophyCurrentProgress);
        }
    }

    @NonNull
    @Override
    public AchievementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.row_achievement, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int trophy = achieveList.get(position).getTrophy();
        switch (trophy){
            case Achievement.NO_TROPHY:
                holder.imgTrophy.setImageResource(R.drawable.trophy_black);
                break;
            case Achievement.BRONZE_TROPHY:
                holder.imgTrophy.setImageResource(R.drawable.trophy_bronze);
                break;
            case Achievement.SILVER_TROPHY:
                holder.imgTrophy.setImageResource(R.drawable.trophy_silver);
                break;
            case Achievement.GOLD_TROPHY:
                holder.imgTrophy.setImageResource(R.drawable.trophy_gold);
                break;
        }
        holder.txtTitle.setText(achieveList.get(position).getTitle());
        holder.txtCurrent.setText("Hiện tại: " + achieveList.get(position).getCurrent());
    }

    @Override
    public int getItemCount() {
        return achieveList.size();
    }
}
