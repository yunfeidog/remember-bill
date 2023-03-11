package com.cxk;

import java.util.Scanner;

public class test {
    //求面积getArea：
    static int getArea(int x1, int y1, int x2, int y2) {
        return (x2 - x1) * (y1 - y2);

    }

    public static void main(String[] args) {
        int x1, y1, x2, y2;
        int x3, y3, x4, y4;
        Scanner sc = new Scanner(System.in);
        x1 = sc.nextInt();
        y1 = sc.nextInt();
        x2 = sc.nextInt();
        y2 = sc.nextInt();
        x3 = sc.nextInt();
        y3 = sc.nextInt();
        x4 = sc.nextInt();
        y4 = sc.nextInt();
        //重叠部分的长和宽的判断
        int l = Math.max(0, Math.min(x2, x4) - Math.max(x1, x3));
        int w = Math.max(0, Math.min(y1, y3) - Math.max(y2, y4));
        int S = getArea(x1, y1, x2, y2) + getArea(x3, y3, x4, y4) - l * w;
        System.out.println(l);
        System.out.println(w);
        System.out.print(S);
        System.out.println();


    }

}
