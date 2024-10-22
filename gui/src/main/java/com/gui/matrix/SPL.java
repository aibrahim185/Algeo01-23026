package com.gui.matrix;
import java.util.Scanner;

import com.gui.matrix.lib.Matrix;

public class SPL {
    public static void driver(Scanner sc) {
        Matrix m = new Matrix(); 
        m.readFile();
		System.out.println("\n1. Metode eliminasi Gauss");
		System.out.println("2. Metode eliminasi Gauss-Jordan");
		System.out.println("3. Metode Matriks Balikan");
		System.out.println("4. Kaidah Cramer");
		System.out.println("5. Kembali");
		System.out.print("Pilihan: ");

        int metode = sc.nextInt();
        switch (metode) {
            case 1 : m.gaussElimination();
            case 2 : m.jordanElimination();
            case 3 : {
                if (m.determinanEkspansiKofaktor()!=0){
                    m = m.metodeBalikan(); // mengembalikan solusi SPL
                    m.solutionInverseCramer();
                    System.out.println("");
                }
                else {
                    System.out.println("Matriks tidak dapat dicari dengan metode matriks balikan.");
                }
            }
            case 4 : {
                if (m.determinanReduksiBaris()!=0){
                    m = m.kaidahCramer(); // mengembalikan solusi SPL
                    m.solutionInverseCramer();
                    System.out.println("");
                }
                else {
                    System.out.println("Matriks tidak dapat dicari dengan Kaidah Cramer");
                }
            }
        }

        m.print();
	}
}
