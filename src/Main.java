import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        int command = 0;
        try (Scanner sc = new Scanner(System.in)) {
            while (command != 8) {
                System.out.println("\n===== M E N U =====");
                System.out.println("1. Sistem Persamaan Linier");
                System.out.println("2. Determinan");
                System.out.println("3. Matriks Balikan");
                System.out.println("4. Interpolasi Polinom");
                System.out.println("5. Interpolasi Bicubic Spline");
                System.out.println("6. Regresi linier dan kuadratik berganda");
                System.out.println("7. Interpolasi Gambar");
                System.out.println("8. Keluar");
                System.out.println("Pilih menu yang tersedia: ");
                command = sc.nextInt();
                
                while(command < 1 || command > 8){
                    System.out.printf("Masukan tidak valid!\nPilihan: ");
                    command = sc.nextInt();
                }
                
                switch (command) {
                    case 1 -> {
                        SPL.printMetodeSPL();
                    }
                    case 2 -> {
                        System.out.println("2");
                    }
                    case 3 -> {
                        System.out.println("3");
                    }
                    case 4 -> {
                        System.out.println("4");
                    }
                    case 5 -> {
                        System.out.println("5");
                    }
                    case 6 -> {
                        System.out.println("6");
                    }
                    case 7 -> {
                        System.out.println("7");
                    }
                    case 8 -> {
                        System.out.println("See u~");
                        break;
                    }
                }
            }
        }
    }
}