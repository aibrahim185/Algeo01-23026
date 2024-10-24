package com.gui.matrix;

import java.util.Scanner;

import com.gui.matrix.lib.Matrix;

public class Interpolate {
    public static void driver(Scanner sc) {
        System.out.print("Masukkan jumlah data : ");
        int n = sc.nextInt();  
        
        System.out.println("Masukkan data        : x0 y0 x1 y1 ... xn yn");
        Matrix X = new Matrix(n + 1, n + 1);
        Matrix XP = new Matrix(n, 1);
        Matrix YV = new Matrix(1, 0);
        for (int i = 0; i <= n; i++) {
            double temp = sc.nextDouble();
            XP.setMat(i, 0, temp);  
            temp = sc.nextDouble();
            YV.setMat(0, i, temp);  
        }
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                X.setMat(i, j, Math.pow(XP.getMat(i, 0), j));  
            }
        }
        
        Matrix resInterpolate = interpolasiLinier(X, XP, YV);
        resInterpolate.print();
    }

    public static Matrix interpolasiLinier(Matrix X, Matrix XP, Matrix YV){
        Matrix Xt = X.transpose();
        Matrix XtX = Xt.mulMatrix(X);
        Matrix XtY = Xt.mulMatrix(YV);
        Matrix B = XtX.inverse().mulMatrix(XtY);
        return B;
    }
}
