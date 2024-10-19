package com.gui.matrix;
import java.util.Scanner;

import com.gui.matrix.lib.Matrix;

public class Invers {
    public static void driver(Scanner sc) {
        Matrix m = new Matrix();
        m.read(sc);
        if (m.determinanEkspansiKofaktor()==0){
            System.out.println("Matriks tidak mempunyai matriks balikan karena determinan matriks adalah 0");
        }
        else{
            System.out.println("\n1. Metode OBE");
            System.out.println("2. Metode Matriks Adjoin");
            System.out.println("3. Kembali");
            System.out.print("Pilihan: ");
            
            int metode = sc.nextInt();
            if (metode == 1) {
                m = m.inverse();
            } else if (metode == 2) {
                m.matBalikan();
            }
            m.print();
        }
    }
}
