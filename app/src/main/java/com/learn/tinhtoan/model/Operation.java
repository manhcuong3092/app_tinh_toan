package com.learn.tinhtoan.model;

public class Operation {
    public static final int ADD = 0;
    public static final int SUBTRACT = 1;
    public static final int MULTIPLE = 2;
    public static final int DIVIDE = 3;

    public static final int UNCHECKED = 0;
    public static final int EXACT = 1;
    public static final int WRONG = 2;

    public static final int EASY = 3;
    public static final int NORMAL = 10;
    public static final int HARD = 25;

    public static final int MAX_EASY = 50;
    public static final int MAX_NORMAL = 1000;
    public static final int MAX_HARD = 20000;

    public static final int WRONG_PENALTY = 5;

    public static final int ADD_SUBTRACT_COEFFICIENT = 1;
    public static final int MULTIPLE_COEFFICIENT = 7;
    public static final int DIVIDE_COEFFICIENT = 5;

    private int a;
    private int b;
    private int operator;
    private String answer;
    private int status;
    private String remainderAnswer;
    private String exactAnswer;
    // 0 = '+',  1 = '-',  2 = '*',  3 = '/'

    public Operation(int a, int b, int operator) {
        this.a = a;
        this.b = b;
        this.operator = operator;
        this.answer = "";
        this.remainderAnswer = "";
        this.status = 0;
        this.exactAnswer = "";
    }

    public String getExactAnswer() {
        return exactAnswer;
    }

    public void setExactAnswer(String exactAnswer) {
        this.exactAnswer = exactAnswer;
    }

    public String getRemainderAnswer() {
        return remainderAnswer;
    }

    public void setRemainderAnswer(String remainderAnswer) {
        this.remainderAnswer = remainderAnswer;
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
