package com.learn.tinhtoan.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.tinhtoan.model.Operation;
import com.learn.tinhtoan.R;

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
    public OperationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.row_operation, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.txtA.setText(opList.get(position).getA() + "");
        holder.txtB.setText(opList.get(position).getB() + "");

        int operator = opList.get(position).getOperator();
        switch (operator) {
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

        if (operator != Operation.DIVIDE) {
            holder.edtRemainder.setVisibility(View.INVISIBLE);
            holder.txtRemainder.setVisibility(View.INVISIBLE);
        } else {
            holder.edtRemainder.setVisibility(View.VISIBLE);
            holder.txtRemainder.setVisibility(View.VISIBLE);
        }

        //xet trang thai
        switch (opList.get(position).getStatus()) {
            case Operation.UNCHECKED:
                holder.imgResult.setImageResource(R.drawable.null_bg);
                break;
            case Operation.EXACT:
                holder.imgResult.setImageResource(R.drawable.correct);
                break;
            case Operation.WRONG:
                holder.imgResult.setImageResource(R.drawable.wrong);
                break;
        }

//        Log.d("AAA", opList.get(position).getExactAnswer());
        holder.txtResultAnswer.setText(opList.get(position).getExactAnswer());

        //lấy lại text bị mất khi đã nhập
        holder.edtAnswer.setText(opList.get(position).getAnswer());
        holder.edtRemainder.setText(opList.get(position).getRemainderAnswer());

    }


    @Override
    public int getItemCount() {
        return opList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtA, txtB, txtOperator, txtRemainder, txtResultAnswer;
        EditText edtAnswer, edtRemainder;
        ImageView imgResult;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtA = itemView.findViewById(R.id.textViewOperantA);
            txtB = itemView.findViewById(R.id.textViewOperantB);
            txtOperator = itemView.findViewById(R.id.textViewOperator);
            edtAnswer = itemView.findViewById(R.id.editTextAnswer);
            imgResult = itemView.findViewById(R.id.imageViewResult);
            txtRemainder = itemView.findViewById(R.id.textViewRemainder);
            txtResultAnswer = itemView.findViewById(R.id.textViewResultAnswer);
            edtRemainder = itemView.findViewById(R.id.editTextRemainder);

            //Sự kiện để lưu giá trị của edittext khi scroll
            edtAnswer.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String answer = edtAnswer.getText().toString();
                    opList.get(getAbsoluteAdapterPosition()).setAnswer(answer);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            edtRemainder.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String remainder = edtRemainder.getText().toString();
                    opList.get(getAbsoluteAdapterPosition()).setRemainderAnswer(remainder);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }

}

