package com.learn.tinhtoan;

public class Operation {
    public static final int ADD = 0;
    public static final int SUBTRACT = 1;
    public static final int MULTIPLE = 2;
    public static final int DIVIDE = 3;

    public static final int UNCHECKED = 0;
    public static final int EXACT = 1;
    public static final int WRONG = 2;

    private int a;
    private int b;
    private int operator;
    private String answer;
    private int status;
    // 0 = '+',  1 = '-',  2 = '*',  3 = '/'

    public Operation(int a, int b, int operator) {
        this.a = a;
        this.b = b;
        this.operator = operator;
        this.answer = "";
        this.status = 0;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }
}
