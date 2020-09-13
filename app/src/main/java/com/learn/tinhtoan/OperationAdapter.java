package com.learn.tinhtoan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OperationAdapter extends RecyclerView.Adapter<OperationAdapter.ViewHolder> {

    ArrayList<Operation> opList;
    Context context;

    public OperationAdapter(ArrayList<Operation> opList, Context context) {
        this.opList = opList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.row_operation, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtA.setText(opList.get(position).getA() + "");
        holder.txtB.setText(opList.get(position).getB() + "");
        int operator = opList.get(position).getOperator();
        switch (operator){
            case Operation.ADD:
                holder.txtOperator.setText("+");
                break;
            case Operation.SUBTRACT:
                holder.txtOperator.setText("-");
                break;
            case Operation.MULTIPLE:
                holder.txtOperator.setText("x");
                break;
            case Operation.DIVIDE:
                holder.txtOperator.setText("÷");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return opList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtA, txtB, txtOperator, txtResult;
        EditText edtAnswer;
        ImageView imgBoolean;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtA = itemView.findViewById(R.id.textViewOperantA);
            txtB = itemView.findViewById(R.id.textViewOperantB);
            txtOperator = itemView.findViewById(R.id.textViewOperator);
            edtAnswer = itemView.findViewById(R.id.editTextAnswer);
            imgBoolean = itemView.findViewById(R.id.imageViewResult);
            txtResult = itemView.findViewById(R.id.textViewResult);
        }
    }

}

