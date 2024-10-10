import lib.Matrix;
import java.util.Scanner;

public class Invers {
    public static void driver(Scanner sc) {
        Matrix m = new Matrix();
        m.read(sc);
        if (m.determinanEkspansiKofaktor()==0){
            System.out.println("Matriks tidak mempunyai matriks balikan");
        }
        else{m.inverse();}
        m.print();
    }
}
