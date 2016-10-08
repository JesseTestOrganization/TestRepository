package com.edianjucai.util;

public class MathUtil {

    public static double getRepayment(double balance, int periods, float rate) {
        rate = rate / 100 / 12;
        return balance * rate * Math.pow((1 + rate), periods) / (Math.pow((1 + rate), periods) - 1);
    }
    
    public static void main(String[] args) {
        System.out.println(getRepayment(6000, 6, 8));
    }
}
