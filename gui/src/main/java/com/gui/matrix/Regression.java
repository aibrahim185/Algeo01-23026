package com.gui.matrix;
import java.util.Scanner;

import com.gui.matrix.lib.Matrix;


public class Regression {
    public static void driver(Scanner sc) {
        System.out.print("Masukkan jumlah peubah x : ");
        int n = sc.nextInt();  

        System.out.print("Masukkan jumlah data     : ");
        int m = sc.nextInt();  
        
        System.out.println("Masukkan semua nilai-nilai x dan nilai y (kolom terakhir adalah y):");
        Matrix X = new Matrix(m, n + 1);
        Matrix Y = new Matrix(m, 1);
        
        for (int i = 0; i < m; i++) {
            X.setMat(i, 0, 1);  
            for (int j = 1; j <= n; j++) {
                double temp = sc.nextDouble();
                X.setMat(i, j, temp);  
            }
            double tempY = sc.nextDouble();
            Y.setMat(i, 0, tempY);  
        }

        Matrix XK = new Matrix(n + 1, 1);
        XK.setMat(0, 0, 1);  
        for (int i = 1; i <= n; i++) {
            double temp = sc.nextDouble();
            XK.setMat(i, 0, temp);  
        }
        
        // Regresi Linier
        Matrix resLinier = regresiLinier(X, Y);
        System.out.println("Hasil regresi linier:");
        // resLinier.print();
        double res = 0;
        for (int i = 0; i <= n; i++) {
            res += resLinier.getMat(i, 0) * XK.getMat(i, 0);
        }
        System.out.println(res);
        
        // Regresi Kuadratik
        Matrix resKuadratik = regresiKuadratik(X, Y);
        System.out.println("Hasil regresi kuadratik:");
        // resKuadratik.print();
        Matrix XK2 = new Matrix((n * (n - 1) / 2) + 1 + n + n, 1);
        XK2.setMat(0, 0, 1);
        int idx = 1;
        for (int i = 1; i <= n; i++) {
            XK2.setMat(idx, 0, XK.getMat(i, 0));
            idx++;
        } 
        for (int i = 1; i <= n; i++) {
            XK2.setMat(idx, 0, Math.pow(XK.getMat(i, 0), 2));
            idx++;
        } 
        for (int i = 1; i <= n - 1; i++) {
            for (int j = i + 1; j <= n; j++) {
                XK2.setMat(idx, 0, XK.getMat(i, 0) * XK.getMat(j, 0));
                idx++;
            }
        } 
        double res2 = 0;
        for (int i = 0; i < ((n * (n - 1) / 2) + 1 + n + n); i++) {
            res2 += resKuadratik.getMat(i, 0) * XK2.getMat(i, 0);
        }
        System.out.println(res2);
    }

    public static Matrix regresiLinier(Matrix X, Matrix Y) {
        Matrix Xt = X.transpose();  
        Matrix XtX = Xt.mulMatrix(X);  
        Matrix XtY = Xt.mulMatrix(Y);  
        Matrix B = XtX.concat(XtY);
        Matrix C = B.metodeBalikan();
        return C;
    }

    public static Matrix regresiKuadratik(Matrix X, Matrix Y) {
        int n = X.getCol() - 1;  
        int m = X.getRow();  

        Matrix X_quad = new Matrix(m, n + n + (n * (n - 1)) / 2 + 1);  
        
        for (int h = 0; h < m; h++) {
            X_quad.setMat(h, 0, 1);
            int idx = 1;
            for (int i = 1; i <= n; i++) {
                X_quad.setMat(h, idx, X.getMat(h, i));
                idx++;
            } 
            for (int i = 1; i <= n; i++) {
                X_quad.setMat(h, idx, Math.pow(X.getMat(h, i), 2));
                idx++;
            } 
            for (int i = 1; i <= n - 1; i++) {
                for (int j = i + 1; j <= n; j++) {
                    X_quad.setMat(h, idx, X.getMat(h, i) * X.getMat(h, j));
                    idx++;
                }
            } 
        }
        return regresiLinier(X_quad, Y);
    }
}
