package com.gui.matrix;
import java.util.Scanner;

import com.gui.matrix.lib.Matrix;

public class Determinant {
    public static void driver(Scanner sc) {
        Matrix m = new Matrix(); 
        m.read(sc);

		System.out.println("\n1. Determinan reduksi baris");
		System.out.println("2. Determinan ekspansi kofaktor");
		System.out.println("3. Kembali");
		System.out.print("Pilihan: ");

        System.out.println("Determinan = " + m.determinanReduksiBaris());
        System.out.println("Determinan = " + m.determinanEkspansiKofaktor());

        m.print();
    }
}
