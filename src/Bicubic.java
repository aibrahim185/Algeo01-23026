
import java.util.Scanner;

import lib.Matrix;

public class Bicubic {
    public static void driver(Scanner sc) {
        System.out.println("Masukkan Matrix : 4x4");
        Matrix X = new Matrix(4, 4);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                double temp = sc.nextDouble();
                X.setMat(i, j, temp);  
            }
        }
        Matrix flatMatrix = flatten(X);
        double a, b;
        a = sc.nextDouble();
        b = sc.nextDouble();
        bicubicInterpolation(flatMatrix, a, b);
    }
    
    public static Matrix flatten(Matrix X) {
        Matrix flatMatrix = new Matrix(16, 1); // 16x1 matrix
        int idx = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                flatMatrix.setMat(idx, 0, X.getMat(i, j));
                idx++;
            }
        }
        return flatMatrix;
    }

    public static Matrix muli() {
        Matrix ret = new Matrix(16, 16);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    for (int l = 0; l < 4; l++) {
                        ret.setMat(i * 4 + j, k * 4 + l, Math.pow(i - 1, k) * Math.pow(j - 1, l));
                    }
                }
            }
        }
        ret.print();
        return ret.inverse();
    }    
    
    public static void bicubicInterpolation(Matrix f, double tx, double ty) {
        Matrix a = muli().mulMatrix(f);
		double ans = 0;
		for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                ans += a.getMat(i * 4 + j, 0) * Math.pow(tx, i) * Math.pow(ty, j);
			}
		}
		muli().print();
		System.out.println("f(" + tx + " , " + ty + ") = " + ans);
	}
}
