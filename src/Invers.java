import lib.Matrix;
import java.util.Scanner;

public class Invers {
    public static void driver(Scanner sc) {
        Matrix m = new Matrix();
        m.read(sc);
        if (m.determinanEkspansiKofaktor()==0){
            System.out.println("Matriks tidak mempunyai matriks balikan");
        }
        else{
            Matrix temp = m.inverse();
            System.out.println("Hasil Matriks Balikan dengan Matriks Adjoin");
            temp.print();
            System.out.println("\n");
            System.out.println("Hasil Matriks Balikan dengan OBE");
            m.matBalikan();
            m.print();
        }
    }
}
