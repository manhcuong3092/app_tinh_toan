package com.learn.tinhtoan.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.tinhtoan.Database;
import com.learn.tinhtoan.R;
import com.learn.tinhtoan.activity.LoginActivity;
import com.learn.tinhtoan.activity.MainActivity;
import com.learn.tinhtoan.model.Task;
import com.learn.tinhtoan.model.User;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    User user = MainActivity.currentUser;
    Database db = LoginActivity.database;

    ArrayList<Task> taskList;
    MainActivity context;

    public TaskAdapter(ArrayList<Task> taskList, MainActivity context) {
        this.taskList = taskList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtContent;
        CheckBox cbFinish;
        ImageButton ibtnEdit, ibtnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtContent = itemView.findViewById(R.id.textViewTaskContent);
            cbFinish = itemView.findViewById(R.id.checkBoxFinish);
            ibtnDelete = itemView.findViewById(R.id.imageButtonDeleteTask);
            ibtnEdit = itemView.findViewById(R.id.imageButtonEditTask);
        }
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.row_task, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        int status = taskList.get(position).getStatus();
        if (status == Task.UNFINISHED) {
            holder.cbFinish.setChecked(false);
        }
        holder.txtContent.setText(taskList.get(position).getContent());

        holder.cbFinish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                context.setFinishTask(taskList.get(position).getId());
            }
        });

        holder.ibtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.showDialogEditTask(taskList.get(position).getId(), taskList.get(position).getContent());
            }
        });

        holder.ibtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.showDialogDeleteTask(taskList.get(position).getId(), taskList.get(position).getContent());
            }
        });
    }




}
