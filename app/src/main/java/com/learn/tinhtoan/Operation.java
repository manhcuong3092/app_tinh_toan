package com.learn.tinhtoan;

public class Operation {
    public static final int ADD = 0;
    public static final int SUBTRACT = 1;
    public static final int MULTIPLE = 2;
    public static final int DIVIDE = 3;

    private int a;
    private int b;
    private int operator;
    // 0 = '+',  1 = '-',  2 = '*',  3 = '/'

    public Operation(int a, int b, int operator) {
        this.a = a;
        this.b = b;
        this.operator = operator;
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
