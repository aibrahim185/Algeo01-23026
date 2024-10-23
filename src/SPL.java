import java.util.Scanner;
import lib.*;

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
            case 1 -> m.gaussSolution();
            case 2 -> m.gaussJordanSolution();
            case 3 -> {
                if (m.getCol()-1==m.getRow() && m.determinanEkspansiKofaktor()!=0){
                    m = m.metodeBalikan(); // mengembalikan solusi SPL
                    m.solutionInverseCramer();
                    System.out.println("");
                }
                else {
                    System.out.println("Tidak dapat menggunakan metode matriks balikan");
                }
            }
            case 4 -> {
                if (m.getCol()-1==m.getRow() && m.determinanEkspansiKofaktor()!=0){
                    m = m.kaidahCramer(); // mengembalikan solusi SPL
                    m.solutionInverseCramer();
                    System.out.println("");
                }
                else {
                    System.out.println("Tidak dapat menggunakan Kaidah Cramer");
                }
            }
        }

        // m.print();
	}
}
