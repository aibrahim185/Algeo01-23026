import lib.*;
import java.util.Scanner;

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
            case 1 -> m.gaussElimination();
            case 2 -> m.jordanElimination();
            case 3 -> System.out.println("invers");
            case 4 -> System.out.println("Cramer");
        }

        m.print();
	}
}
