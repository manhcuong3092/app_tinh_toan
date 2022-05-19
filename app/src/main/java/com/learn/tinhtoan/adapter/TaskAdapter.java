package com.learn.tinhtoan.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.graphics.Typeface;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.tinhtoan.Database;
import com.learn.tinhtoan.R;
import com.learn.tinhtoan.activity.LoginActivity;
import com.learn.tinhtoan.activity.MainActivity;
import com.learn.tinhtoan.fragment.TodoFragment;
import com.learn.tinhtoan.model.Task;
import com.learn.tinhtoan.model.User;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    User user = MainActivity.currentUser;
    Database database;
    ArrayList<Task> taskList;
    MainActivity context;
    boolean viewDone;

    public TaskAdapter(ArrayList<Task> taskList, MainActivity context) {
        this.taskList = taskList;
        this.context = context;
        this.viewDone = false;
        database = new Database(context);
    }

    public boolean isViewDone() {
        return viewDone;
    }

    public void setViewDone(boolean viewDone) {
        this.viewDone = viewDone;
        notifyDataSetChanged();
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
        } else {
            if(viewDone) {
                holder.cbFinish.setChecked(true);
                holder.txtContent.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                holder.itemView.setVisibility(View.VISIBLE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            } else {
                holder.txtContent.setPaintFlags(Paint.ANTI_ALIAS_FLAG);
                holder.itemView.setVisibility(View.GONE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            }
        }
        if(taskList.size() > 0){
            holder.txtContent.setText(taskList.get(position).getContent());

            holder.cbFinish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    taskList.get(position).setStatus(holder.cbFinish.isChecked() ? Task.FINISHED : Task.UNFINISHED);
                    if(taskList.get(position).getStatus() == Task.FINISHED) {
                        holder.txtContent.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    } else {
                        holder.txtContent.setPaintFlags(Paint.ANTI_ALIAS_FLAG);
                    }
                    setFinishTask(taskList.get(position));
                }
            });

            holder.ibtnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialogEditTask(taskList.get(position).getId(), taskList.get(position).getContent());
                }
            });

            holder.ibtnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialogDeleteTask(taskList.get(position).getId(), taskList.get(position).getContent());
                }
            });
        }

    }

    public void showDialogEditTask(final int id, String content) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_task);

        final EditText edtTaskEdit = dialog.findViewById(R.id.editTextEditTask);
        Button btnConfirm = dialog.findViewById(R.id.buttonConfirmEdit);
        Button btnCancel = dialog.findViewById(R.id.buttonCancelEdit);

        edtTaskEdit.setText(content);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newContent = edtTaskEdit.getText().toString();
                if (newContent.length() > 200) {
                    Toast.makeText(context, "Nội dung không được quá 200 ký tự", Toast.LENGTH_SHORT).show();
                } else {
                    database.queryData("UPDATE Task SET content = '" + newContent +
                            "' WHERE Id = " + id + ";");
                    setChangeDataAdapter();
                    Toast.makeText(context, "Đã cập nhật.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showDialogDeleteTask(final int id, String content) {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(context);
        dialogDelete.setMessage("Bạn có muốn xóa công việc '" + content + "' này không?");
        dialogDelete.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.queryData("DELETE FROM Task WHERE Id = " + id + ";");
                Toast.makeText(context, "Đã xóa.", Toast.LENGTH_SHORT).show();
                setChangeDataAdapter();
            }
        });
        dialogDelete.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogDelete.show();
    }

    public void setFinishTask(Task task) {
        database.queryData("UPDATE Task set status = " + task.getStatus() + " WHERE Id = " +
                task.getId() + ";");
        setChangeDataAdapter();
    }


    public void setChangeDataAdapter() {
        FragmentManager fragmentManager = context.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TodoFragment todoFragment = (TodoFragment) fragmentManager.findFragmentByTag("fragment");
        todoFragment.getTaskList();
    }
}
