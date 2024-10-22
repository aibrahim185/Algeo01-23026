
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
        int x,y,powx,powy,temp;
        x = 0; y = 0; powx = 0; powy = 0;
        for (int i = 0; i < 4; i++) {
            switch(i){
                case 0 -> {
                    powx = 0;
                    powy = 0;
                }
                case 1 -> {
                    powx = 0;
                    powy = 1;
                }
                case 2 -> {
                    powx = 1;
                    powy = 0;
                }
                case 3 -> {
                    powx = 1;
                    powy = 1;
                }
            }
            for (int j = 0; j < 4; j++) {
                switch(j){
                    case 0 -> {
                        x = 0;
                        y = 0;
                    }
                    case 1 -> {
                        x = 0;
                        y = 1;
                    }
                    case 2 -> {
                        x = 1;
                        y = 0;
                    }
                    case 3 -> {
                        x = 1;
                        y = 1;
                    }
                }
                for (int k = powx; k < 4; k++) {
                    for (int l = powy; l < 4; l++) {
                        ret.setMat(i * 4 + j, k * 4 + l, Math.pow(k,powx) * Math.pow(l,powy) * Math.pow(x, k-powx) * Math.pow(y, l-powy));
                    }
                }
            }
        }
        ret.print();
        ret.matBalikan();
        return ret;
    }    
    
    public static void bicubicInterpolation(Matrix f, double tx, double ty) {
        Matrix a = muli().mulMatrix(f);
        a.print();
		double ans = 0;
		for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                ans += a.getMat(i * 4 + j, 0) * Math.pow(tx, i) * Math.pow(ty, j);
			}
		}
		System.out.println("f(" + tx + " , " + ty + ") = " + ans);
	}
}
