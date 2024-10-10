
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
        double a, b;
        a = sc.nextDouble();
        b = sc.nextDouble();
    }
    
}
