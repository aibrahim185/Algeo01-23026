package lib;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Matrix {
    double[][] elmnt = null;
    int row, col;

    /* *** CONSTRUCTOR *** */
    public Matrix(int x, int y) {
		elmnt = new double[x][y];
		row = x;
		col = y;
	}
	public Matrix() {
		elmnt = new double[1][1];
		row = 1;
		col = 1;
	}

    /* *** READ/WRITE *** */
    public void print() {
		/*	Print matriks */
		for(int i = 0; i < getRow(); i++) {
            System.out.print("[ ");
			for(int j = 0; j < getCol(); j++) {
				if(j > 0) System.out.print(" ");
				System.out.printf("%.2f",getMat(i, j));
			}
            System.out.println(" ]");
		}
	}

    public void solutionInverseCramer(){
        /* Print solusi SPL Inverse dan Cramer x1=... x2=... dst */
        for (int i = 0; i<getRow(); i++){
            System.out.print("x"+i+"=" +getMat(i, getCol()-1)+" ");
        }
    }

    public void read(Scanner sc) {
		/* Membaca cebuah matrikc dari keyboard
		 * Dimulai dengan membaca jumlah baris dan jumlah kolom
		 */
		System.out.print("Jumlah baris: ");
		row = sc.nextInt();
		System.out.print("Jumlah kolom: ");
		col = sc.nextInt();
		elmnt = new double[row][col];
		for(int i = 0; i < getRow(); i++) {
			for(int j = 0; j < getCol(); j++) {
				elmnt[i][j] = sc.nextDouble();
			}
		}
	}
    public void readFile(){
        Scanner sc = new Scanner(System.in);
        BufferedReader scFile = new BufferedReader(new InputStreamReader(System.in));
        System.out.printf("1. Masukan dari keyboard\n2. Masukan dari file\nPilihan: ");
        int choice = sc.nextInt();
        while(choice != 1 && choice != 2){
            System.out.printf("Masukan tidak valid! Silakan ulangi...\n");
            choice = sc.nextInt();
        }
        if(choice == 1){
            read(sc);
        }
        else{
            Boolean found = false;
            while(!found){
                found = true;
                String fileName = "";
                System.out.printf("Masukkan nama file: ");
                try{
                    fileName = scFile.readLine();
                }
                catch(IOException err){
                    // err.printStackTrace();
                }
                try{
                    Scanner file = new Scanner(new File("../test/"+fileName));
                    Integer n = 0, m = 0;
                    while(file.hasNextLine()){
                        n++;
                        m = file.nextLine().split(" ").length;
                        
                    }
                    file.close();
                    // System.out.println(n + " " + m);

                    elmnt = new double[n][m];
                    col = m;
                    row = n;
                    file = new Scanner(new File("../test/"+fileName));
                    for(int i = 0; i < n; i++){
                        for(int j = 0; j < m; j++){
                            // System.out.println(file.nextDouble());
                            double x = file.nextDouble();
                            elmnt[i][j] = x;
                        }
                    }
                    file.close();
                }
                catch(FileNotFoundException err){
                    // err.printStackTrace();
                    found = false;
                }
            }
        }
    }
    
    /* *** SELECTOR *** */
    public double getMat(int r, int c) {
		return elmnt[r][c];
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	
	public void setMat(int r, int c, double newelmnt) {
		elmnt[r][c] = newelmnt;
	}
	public void setRow(int newelmnt) {
		row = newelmnt;
	}
	public void setCol(int newelmnt) {
		col = newelmnt;
	}

    /* *** MATRIX PRIMITIVE *** */
    public Matrix mulMatrix(Matrix m) {
        /* Mengembalikan hasil perkalian matrix self dengan matrix m */
        Matrix ret = new Matrix(getRow(), m.getCol());
        for (int i = 0; i < ret.getRow(); i++) {
            for (int j = 0; j < ret.getCol(); j++) {
                ret.elmnt[i][j] = 0;
                for (int k = 0; k < getCol(); k++) {
                    ret.elmnt[i][j] += elmnt[i][k] * m.elmnt[k][j];
                }
            }
        }
        return  ret;
    }
    
    public Matrix mulDouble(double multiplier) {
        /* Mengembalikan hasil perkalian matrix self dengan konstanta multiplier */
        Matrix ret = this;
        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < getCol(); j++) {
                ret.elmnt[i][j] *= multiplier;
            }
        }
        return  ret;
    }
    
    public Matrix transpose(){
        /* Mengembalikan transpose matrix */
        Matrix ret = new Matrix(getCol(),getRow());
        for (int i = 0; i < ret.getRow(); i++) {
            for (int j = 0; j < ret.getCol(); j++) {
                ret.elmnt[i][j] = elmnt[j][i];
            }
        }
        return  ret;
    }

    public Matrix cofactor(int p, int q) {
        int size = getRow();
        Matrix temp = new Matrix(size - 1, size - 1); 

        int r = 0, c;
        for (int i = 0; i < size; i++) {
            if (i == p) {
                continue;  
            }

            c = 0;
            for (int j = 0; j < size; j++) {
                if (j == q) {
                    continue;  
                }

                temp.setMat(r, c, getMat(i, j));
                c++;
            }
            r++;
        }

        return temp;
    }

    public Matrix MatCof(){
        /* Mengembalikan matriks kofaktor */
        Matrix ret = new Matrix(getRow(), getCol());
        for (int i = 0; i<getRow();i++){
            for (int j = 0; j<getCol(); j++){
                Matrix temp = this.cofactor(i, j);
                ret.elmnt[i][j] = temp.determinanReduksiBaris();
                if (i%2==0){
                    if (j%2!=0){
                        ret.elmnt[i][j] *= -1;
                    }
                }
                else{
                    if (j%2==0){
                        ret.elmnt[i][j] *= -1;
                    }
                }

            }
        }
        return ret;
    }

    public Matrix adjoin() {
        Matrix temp = MatCof().transpose();
        return temp;
    }

    public Matrix inverse() {
        /* mengembalikkan matriks invers dengan Matriks Adjoin*/
        Matrix temp = adjoin().mulDouble(1/determinanReduksiBaris());
        return temp;
    }

    /* *** COMMON MATRIX */
    public Matrix idenMatrix(int r){
        /* I.S. Matriks tidak terdefinisi
         * F.S Matriks menjadi matriks identitas
         */

        Matrix ret = new Matrix(r, r);
        for (int i=0;i<r;i++){
            for (int j=0;j<r;j++){
                if (i==j){
                    ret.setMat(i, j, 1);
                } else {
                    ret.setMat(i, j, 0);
                }
            }
        }
        return ret;
    }

    public Matrix matTanpaB(){
        /* I.S. Matriks augmented terdefinisi
         * F.S. Mengembalikkan matriks tanpa b
         */

        Matrix ret = new Matrix(getRow(), getCol()-1);
        for (int i = 0; i <getRow();i++){
            for (int j = 0; j<getCol()-1;j++){
                ret.setMat(i, j, getMat(i, j));
            }
        } 
        return ret;
    }

    public Matrix matB(){
        Matrix ret = new Matrix(getRow(), 1);
        for (int i = 0; i<getRow();i++){
            ret.setMat(i, 0, getMat(i, getCol()-1));
        }
        return ret;
    }
    

    /* *** OBE *** */
    public void swapRow(int row1, int row2) {
        /* I.S. row1 dan row2 terdefinisi
         * F.S. row1 dan row2 mempunyai elemen yang telah ditukar
         */

        for (int i = 0; i < getCol(); i++) {
            double temp = elmnt[row1][i];
            elmnt[row1][i] = elmnt[row2][i];
            elmnt[row2][i] = temp;
        }
    }

    public void divRow(int dRow, double divider) {
        /* I.S. dRow dan divider terdefinisi
         * F.S. seluruh elemen pada baris dRow dibagi dengan divider
         */

        for (int i = 0; i < getCol(); i++) {
            elmnt[dRow][i] /= divider;
        }
    }

    public void addRow(int aRow, int rowAdder, double multiplier) {
        /* I.S. aRow, rowAdder, dan multiplier terdefinisi
         * F.S. seluruh elemen pada baris aRow ditambah dengan multiplier * rowAdder
         */

        for (int i = 0; i < getCol(); i++) {
            elmnt[aRow][i] += multiplier * elmnt[rowAdder][i];
        }
    }

    /* *** SPL *** */
    public void gaussElimination() {
        /* I.S. Matrix terdefinisi
         * F.s. Matrix menjadi matrix gauss
         */

        int satuUtama = 0;
        for (int i = 0; i < getRow(); i++) {
            if (satuUtama >= getCol()) break;

            // jika elemen pada satuUtama = 0, cari baris lain untuk ditukar
            if (getMat(i, satuUtama) == 0) {
                for (int j = i + 1; j < getRow(); j++) {
                    if (getMat(j, satuUtama) != 0) {
                        swapRow(i, j);
                        break;
                    }
                }
            }

            // jika baris untuk ditukar tidak ditemukan, satuUtama bergeser ke kanan
            if (getMat(i, satuUtama) == 0) {
                satuUtama++; i--;
                continue;
            }

            // membagi semua elemen pada baris supaya elemen pada satuUtama menjadi 1
            divRow(i, getMat(i, satuUtama));

            // mengurangi semua elemen di bawah satuUtama supaya menjadi 0
            for (int j = i + 1; j < getRow(); j++){
                addRow(j, i, -getMat(j, satuUtama));
            }

            satuUtama++;
        }
    }
    
    public void jordanElimination() {
        /* I.S. Matrix terdefinisi
         * F.s. Matrix menjadi matrix gauss-jordan
         */
        gaussElimination();
        int satuUtama;
        for (int i = getRow()-1 ; i > 0 ; i--) {
            satuUtama = 0;
            // mencari satuUtama baris i
            while (getMat(i, satuUtama) == 0) {
                satuUtama++;
                if(satuUtama>getCol()-2) break;
            }

            // untuk baris dengan semua elemen bernilai 0
            if (satuUtama == getCol()-1) { 
                continue;
            }

            // mengurangi semua elemen di atas satuUtama supaya menjadi 0
            for (int j = i-1 ; j >= 0; j--){
                addRow(j, i, -getMat(j, satuUtama));
            }
        }
    }

    public void matBalikan(){
        /* Mencari matriks balikan dengan menggunakan OBE
         * I.S. Matriks nxn terdefinisi 
         * F.s. Matriks menjadi matriks balikan
         */

        // Membuat matriks identitas
        Matrix matIden = idenMatrix(getRow());

        // Gabungan ret dan matIden
        Matrix matGabung = new Matrix(getRow(), getCol()*2);
        for (int i = 0; i < getRow();i++){
            for (int j = 0;j<getCol();j++){
                matGabung.setMat(i, j, getMat(i, j)); 
                matGabung.setMat(i, j+getCol(), matIden.getMat(i, j));
            }
        }
        // OBE matGabung
        matGabung.gaussElimination();
        // copas jordan dan ganti batas iterasinya
        int satuUtama;
        for (int i = getRow() - 1 ; i > 0 ; i--) {
            satuUtama = 0;
            // mencari satuUtama baris i
            while (matGabung.getMat(i, satuUtama) == 0 && satuUtama < getCol()) {
                satuUtama++;
            }

            // untuk baris dengan semua elemen bernilai 0
            if (satuUtama == matGabung.getCol()/2) { 
                continue;
            }

            // mengurangi semua elemen di atas satuUtama supaya menjadi 0
            for (int j = i-1 ; j >= 0; j--){
                matGabung.addRow(j, i, -matGabung.getMat(j, satuUtama));
            }
        }

        // masukkan matriks invers dari matGabung ke ret
        for (int i = 0; i<getRow();i++){
            for (int j = 0; j<getCol();j++){
                setMat(i, j, matGabung.getMat(i, getCol()+j));
            }
        }
    }

    public Matrix metodeBalikan(){
        /* I.S. Matriks terdefinisi
         * F.s. Matriks solusi SPL dengan OBE
         */

        Matrix matB = matB();
        Matrix ret = matTanpaB();
        ret.matBalikan();
        
        // menghasilkan solusi SPL
        ret = ret.mulMatrix(matB);
        return ret;
    }


    public Matrix kaidahCramer(){
        /* I.S. matriks augmented terdefinisi
         * F.F. Solusi SPL dengan kaidah Cramer
         */

        // Menyimpan matriks b dan matriks tanpa b
        Matrix matB = matB();
        Matrix temp = matTanpaB();
        Matrix x = new Matrix(getRow(), 1); // matriks solusi

        // mengganti kolom matriks temp dengan matB
        for (int i = 0; i<getCol()-1; i++){
            for (int j = 0; j < getRow(); j++){
                temp.setMat(j, i, matB.getMat(j, 0));
            }
            // mencari determinan dan menyimpan di matriks x
            x.setMat(i, 0, temp.determinanEkspansiKofaktor());
            // reset temp
            temp = matTanpaB();
        }
        // membagi dengan determinan matriks awal
        x = x.mulDouble(1/matTanpaB().determinanEkspansiKofaktor());
        return x;
    }

    /* *** Determinan *** */
    public double determinanReduksiBaris() {
        // modifikasi gaussElimination();

        double determinant = 1;
        int satuUtama = 0;
        for (int i = 0; i < getRow(); i++) {
            if (satuUtama >= getCol()) break;

            // jika elemen pada satuUtama = 0, cari baris lain untuk ditukar
            if (getMat(i, satuUtama) == 0) {
                for (int j = i + 1; j < getRow(); j++) {
                    if (getMat(j, satuUtama) != 0) {
                        swapRow(i, j);
                        determinant *= -1;
                        break;
                    }
                }
            }

            // jika baris untuk ditukar tidak ditemukan, satuUtama bergeser ke kanan
            if (getMat(i, satuUtama) == 0) {
                satuUtama++; i--;
                continue;
            }

            // membagi semua elemen pada baris supaya elemen pada satuUtama menjadi 1
            determinant *= getMat(i, satuUtama);
            divRow(i, getMat(i, satuUtama));
            // mengurangi semua elemen di bawah satuUtama supaya menjadi 0
            for (int j = i + 1; j < getRow(); j++){
                addRow(j, i, -getMat(j, satuUtama));
            }

            satuUtama++;
        }

        for (int i = 0; i < getRow(); i++) {
            determinant *= getMat(i, i);
        }
        
        return determinant;
    }
    

    public double determinanEkspansiKofaktor() {
        double ret = 0;
        if (getRow() == 1) {
            // jika matriks 1x1, determinannya adalah elemen itu sendiri
            ret = (double) getMat(0, 0);
        } else {
            // ekspansi kofaktor untuk matriks berukuran lebih dari 1
            for (int i = 0; i < getRow(); i++) {
                if(getMat(i, 0) != 0){
                    // membuat 0 di kolom pertama selain elmnt(i,0)
                    for(int j = i+1; j < getRow(); j++){
                        addRow(j, i, -getMat(j, 0)/getMat(i,0));
                    }
                    // membuat matriks minor
                    Matrix temp = cofactor(i,0);
                    // menambahkan ke hasil determinan
                    int sign = i%2==0 ? 1 : -1;
                    ret += sign * getMat(i, 0) * temp.determinanEkspansiKofaktor(); //rekursi sampai getRow() = 1;
                    break;
                }
            }
        }
        return ret;
    }

    public void gaussSolution(){
        /* I.S. Matriks augmented terdefinisi
         * F.S. Menampilkan solusi SPL
         */
        gaussElimination();
        String[] solution = new String[getCol()-1];
        
        Boolean[] visited = new Boolean[getCol()-1];
        char[] parametric = new char[getCol()-1];
        Integer[] satuUtama = new Integer[getRow()];
        Integer cur = 17;
        for(int i = 0; i < getCol()-1; i++) {
            visited[i] = false;
            solution[i] = " ";
            parametric[i] = '?';
        }
        Boolean tidakAdaSolusi = false;
        for (int i = getRow()-1 ; i >= 0 ; i--) {
            satuUtama[i] = 0;
            // mencari satuUtama baris i
            while (getMat(i, satuUtama[i]) == 0) {
                satuUtama[i]++;
                if(satuUtama[i]>getCol()-2) break; //satuUtama[i] = getCol()-1
            }

            // untuk baris dengan semua elemen bernilai 0
            if (satuUtama[i] == getCol()-1) { 
                if(getMat(i, getCol()-1) != 0){
                    tidakAdaSolusi = true;
                    break;
                }
                //else, banyak solusi.
            }
            else{
                visited[satuUtama[i]] = true; // satuUtama bukan parametrik
            }
        }
        if(tidakAdaSolusi){
            System.out.println("Tidak ada solusi");
            return;
        }
        for(int i = 0;i < getCol()-1; i++){
            if(!visited[i]){
                parametric[i] = (char)(cur+97);
                cur++;
            }
        }
        for (int i = getRow()-1 ; i >= 0 ; i--) {
            if (satuUtama[i] != getCol()-1){
                String tempsol = "";
                for(int j = satuUtama[i]+1; j < getCol()-1; j++){
                    if(getMat(i, j) != 0){
                        if(!visited[j]){
                            if(getMat(i, j)<0) {
                                tempsol += " + ";
                                if(-1*getMat(i, j)!=1) tempsol += Double.toString(-1*getMat(i, j));
                            }
                            else {
                                tempsol += " - ";
                                if(getMat(i, j)!=1) tempsol += Double.toString(getMat(i, j));
                            }
                            tempsol += parametric[j];
                        }
                        else{
                            int r = 0;
                            for(int k = 0; k<getRow(); k++){
                                if(satuUtama[k] == j){
                                    r = k;
                                }
                            }
                            addRow(i, r, -getMat(i, j));
                        }
                    }
                }
                double temp = getMat(i, getCol()-1);
                solution[satuUtama[i]] = "x" + Integer.toString(satuUtama[i]+1) + " = " + Double.toString(temp);
                solution[satuUtama[i]] += tempsol;
            }
        }
        for(int i = 0; i < getCol()-1; i++){
            if(visited[i]){
                solution[i] += "\n";
                System.out.printf(solution[i]);
            }
            else{
                System.out.printf("x%d = %c\n", i+1, parametric[i]);
            }
        }
    }

    public void gaussJordanSolution(){
        /* I.S. Matriks augmented terdefinisi
         * F.S. Menampilkan solusi SPL
         */
        jordanElimination();
        String[] solution = new String[getCol()-1];
        
        Boolean[] visited = new Boolean[getCol()-1];
        char[] parametric = new char[getCol()-1];
        Integer[] satuUtama = new Integer[getRow()];
        Integer cur = 17;
        for(int i = 0; i < getCol()-1; i++) {
            visited[i] = false;
            solution[i] = " ";
            parametric[i] = '?';
        }
        Boolean tidakAdaSolusi = false;
        for (int i = getRow()-1 ; i >= 0 ; i--) {
            satuUtama[i] = 0;
            // mencari satuUtama baris i
            while (getMat(i, satuUtama[i]) == 0) {
                satuUtama[i]++;
                if(satuUtama[i]>getCol()-2) break; //satuUtama[i] = getCol()-1
            }

            // untuk baris dengan semua elemen bernilai 0
            if (satuUtama[i] == getCol()-1) { 
                if(getMat(i, getCol()-1) != 0){
                    tidakAdaSolusi = true;
                    break;
                }
                //else, banyak solusi.
            }
            else{
                visited[satuUtama[i]] = true; // satuUtama bukan parametrik
            }
        }
        if(tidakAdaSolusi){
            System.out.println("Tidak ada solusi");
            return;
        }
        for(int i = 0;i < getCol()-1; i++){
            if(!visited[i]){
                parametric[i] = (char)(cur+97);
                cur++;
            }
        }
        for (int i = getRow()-1 ; i >= 0 ; i--) {
            if (satuUtama[i] != getCol()-1){
                double temp = getMat(i, getCol()-1);
                solution[satuUtama[i]] = "x" + Integer.toString(satuUtama[i]+1) + " = " + Double.toString(temp);
                for(int j = satuUtama[i]+1; j < getCol()-1; j++){
                    if(getMat(i, j) != 0){
                        if(getMat(i, j)<0) {
                            solution[satuUtama[i]] += " + ";
                            if(-1*getMat(i, j)!=1) solution[satuUtama[i]] += Double.toString(-1*getMat(i, j));
                        }
                        else {
                            solution[satuUtama[i]] += " - ";
                            if(getMat(i, j)!=1) solution[satuUtama[i]] += Double.toString(getMat(i, j));
                        }
                        solution[satuUtama[i]] += parametric[j];
                    }
                }
            }
        }
        for(int i = 0; i < getCol()-1; i++){
            if(visited[i]){
                solution[i] += "\n";
                System.out.printf(solution[i]);
            }
            else{
                System.out.printf("x%d = %c\n", i+1, parametric[i]);
            }
        }
    }

    public Matrix concat(Matrix X) {
        Matrix ret = new Matrix(getRow(), X.getCol() + getCol());
        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < getCol(); j++) {
                ret.setMat(i, j, getMat(i, j));
            }
        }
        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < X.getCol(); j++) {
                ret.setMat(i, getCol() + j, X.getMat(i, j));
            }
        }
        return ret;
    }
}
