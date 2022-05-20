package com.learn.tinhtoan.fragment;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.learn.tinhtoan.Database;
import com.learn.tinhtoan.R;
import com.learn.tinhtoan.activity.LoginActivity;
import com.learn.tinhtoan.activity.MainActivity;
import com.learn.tinhtoan.adapter.TaskAdapter;
import com.learn.tinhtoan.model.Task;
import com.learn.tinhtoan.model.User;

import java.util.ArrayList;

public class TodoFragment extends Fragment {

    View view;
    TaskAdapter adapter;
    RecyclerView recyclerView;
    public static ArrayList<Task> taskList;
    FloatingActionButton fabAdd, fabViewDone;
    SearchView searchView;
    Database db;
    User user = MainActivity.currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_todo, container, false);
        taskList = new ArrayList<>();
        adapter = new TaskAdapter(taskList, (MainActivity) getActivity());
        anhXa();
        db = new Database(container.getContext());
        getTaskList();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });

        fabViewDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adapter.isViewDone()) {
                    fabViewDone.setImageResource(R.drawable.ic_baseline_done_24);
                    adapter.setViewDone(false);
                } else {
                    fabViewDone.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    adapter.setViewDone(true);
                }
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Cursor cursorTask = db.getData("" +
                        "SELECT * FROM Task WHERE userId = " + user.getId() +
                        " AND content LIKE '%" + s + "%'");
                taskList.clear();
                while (cursorTask.moveToNext()) {
                    int id = cursorTask.getInt(0);
                    int userId = cursorTask.getInt(1);
                    String content = cursorTask.getString(2);
                    int status = cursorTask.getInt(3);
                    taskList.add(new Task(id, userId, content, status));
                }
                if (!recyclerView.isComputingLayout() && recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                    adapter.notifyDataSetChanged();
                }
                return true;
            }
        });

        //khoi tao adapter
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }


    private void addTask() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_addtask);

        final EditText edtAddTask = dialog.findViewById(R.id.editTextAddTask);
        Button btnAddTask = dialog.findViewById(R.id.buttonAddTask);
        Button btnCancel = dialog.findViewById(R.id.buttonCancelAdd);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = edtAddTask.getText().toString();
                if (content.equals("")) {
                    Toast.makeText(getActivity(), "Vui lòng nhập tên công việc.", Toast.LENGTH_SHORT).show();
                } else {
                    db.queryData("INSERT INTO Task VALUES(null, " + user.getId() + ", '" + content + "', " +
                            "0)");
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Đã thêm.", Toast.LENGTH_SHORT).show();
                    getTaskList();
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


    public void getTaskList() {
        Cursor cursorTask = db.getData("" +
                "SELECT * FROM Task WHERE userId = " + user.getId());
        taskList.clear();
        while (cursorTask.moveToNext()) {
            int id = cursorTask.getInt(0);
            int userId = cursorTask.getInt(1);
            String content = cursorTask.getString(2);
            int status = cursorTask.getInt(3);
            taskList.add(new Task(id, userId, content, status));
        }
        if (!recyclerView.isComputingLayout() && recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
            adapter.notifyDataSetChanged();
        }
//        adapter.notifyDataSetChanged();
    }

    private void anhXa() {
        fabAdd = view.findViewById(R.id.imageButtonAddTask);
        fabViewDone = view.findViewById(R.id.fabDone);
        recyclerView = view.findViewById(R.id.recyclerViewTasks);
        searchView = view.findViewById(R.id.searchView);
    }

}
