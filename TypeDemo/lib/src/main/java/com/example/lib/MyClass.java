package com.example.lib;

import java.util.Arrays;

public class MyClass {
    public static void main(String[] args) {
        int[] argb = getARGB("rgba(255, 255, 255, 0.5)");
        for (int i = 0; i < argb.length; i++) {
            System.out.print(argb[i] + " ");
        }
    }
    // 过滤颜色
    private static int[] getARGB(String buttonColor) {
        int[] nums = new int[4];
        String newStr = buttonColor.substring(5, buttonColor.length() - 1).trim();
        String[] strings = newStr.split(",");
        for (int i = 0; i < strings.length; i++) {
            nums[i] = Integer.parseInt(strings[i]);
        }
        return nums;

    }
}