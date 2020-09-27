package com.learn.tinhtoan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.tinhtoan.R;
import com.learn.tinhtoan.model.Notification;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    ArrayList<Notification> notificationList;
    Context context;

    public NotificationAdapter(ArrayList<Notification> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle, txtContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.textViewTitleNotice);
            txtContent = itemView.findViewById(R.id.textViewContentNotice);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.row_notification, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtContent.setText(notificationList.get(position).getContent());
        holder.txtTitle.setText(notificationList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }


}
