import lib.*;
import java.util.Scanner;

public class Regression {
    public static void driver(Scanner sc) {
        System.out.print("Masukkan jumlah peubah x : ");
        int n = sc.nextInt();  

        System.out.print("Masukkan jumlah data     : ");
        int m = sc.nextInt();  
        
        System.out.println("Masukkan semua nilai-nilai x dan nilai y (kolom terakhir adalah y):");
        Matrix X = new Matrix(m, n);
        Matrix Y = new Matrix(m, 1);
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                double temp = sc.nextDouble();
                X.setMat(i, j, temp);  
            }
            double tempY = sc.nextDouble();
            Y.setMat(i, 0, tempY);  
        }

        // Regresi Linier
        Matrix resLinier = regresiLinier(X, Y);
        System.out.println("Hasil regresi linier:");
        resLinier.print();

        // Regresi Kuadratik
        Matrix resKuadratik = regresiKuadratik(X, Y);
        System.out.println("Hasil regresi kuadratik:");
        resKuadratik.print();;
    }

    public static Matrix regresiLinier(Matrix X, Matrix Y) {
        Matrix Xt = X.transpose();  
        Matrix XtX = Xt.mulMatrix(X);  
        Matrix XtY = Xt.mulMatrix(Y);  
        Matrix B = XtX.inverse().mulMatrix(XtY);  // (Xt * X)^(-1) * Xt * Y
        return B;
    }

    public static Matrix regresiKuadratik(Matrix X, Matrix Y) {
        int n = X.getCol();  
        int m = X.getRow();  

        Matrix X_quad = new Matrix(m, 2 * n);  
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                X_quad.setMat(i, j, X.getMat(i, j));  
                X_quad.setMat(i, n + j, Math.pow(X.getMat(i, j), 2));  
            }
        }

        return regresiLinier(X_quad, Y);
    }
}
