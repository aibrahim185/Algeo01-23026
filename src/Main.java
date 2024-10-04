import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        int menu = 0;
        try (Scanner sc = new Scanner(System.in)) {
            while (menu != 8) {
                System.out.println("\n===== M E N U =====");
                System.out.println("1. Sistem Persamaan Linier");
                System.out.println("2. Determinan");
                System.out.println("3. Matriks Balikan");
                System.out.println("4. Interpolasi Polinom");
                System.out.println("5. Interpolasi Bicubic Spline");
                System.out.println("6. Regresi linier dan kuadratik berganda");
                System.out.println("7. Interpolasi Gambar");
                System.out.println("8. Keluar");
                System.out.print("Pilihan: ");
                menu = sc.nextInt();
                
                while(menu < 1 || menu > 8){
                    System.out.printf("Masukan tidak valid!\nPilihan: ");
                    menu = sc.nextInt();
                }

                switch (menu) {
                    case 1 -> SPL.driverSPL(sc);
                    case 2 -> System.out.println("2");
                    case 3 -> System.out.println("3");
                    case 4 -> System.out.println("4");
                    case 5 -> System.out.println("5");
                    case 6 -> System.out.println("6");
                    case 7 -> System.out.println("7 bonus");
                    case 8 -> System.out.println("See u~");
                }
            }
        }
    }
}