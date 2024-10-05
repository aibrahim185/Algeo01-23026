import lib.Matrix;
import java.util.Scanner;

public class Determinant {
    public static void driver(Scanner sc) {
        Matrix m = new Matrix(); 
        m.read(sc);

		System.out.println("\n1. Determinan reduksi baris");
		System.out.println("2. Determinan ekspansi kofaktor");
		System.out.println("3. Kembali");
		System.out.print("Pilihan: ");

        int metode = sc.nextInt();
        switch (metode) {
            case 1 -> System.out.println("Determinan = " + m.determinanReduksiBaris());
            case 2 -> System.out.println("Determinan = " + m.determinanEkspansiKofaktor());
        }

        m.print();
    }
}
