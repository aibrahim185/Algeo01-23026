package lib;
import java.util.Scanner;

public class Matrix {
    double[][] val = null;
    int row, col;

    /* *** CONSTRUCTOR *** */
    public Matrix(int x, int y) {
		val = new double[x][y];
		row = x;
		col = y;
	}
	public Matrix() {
		val = new double[1][1];
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
				System.out.printf("%f",getMat(i, j));
			}
            System.out.println(" ]");
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
		val = new double[row][col];
		for(int i = 0; i < getRow(); i++) {
			for(int j = 0; j < getCol(); j++) {
				val[i][j] = sc.nextDouble();
			}
		}
	}
    
    /* *** SELECTOR *** */
    public double getMat(int r, int c) {
		return val[r][c];
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	
	public void setMat(int r, int c, double newVal) {
		val[r][c] = newVal;
	}
	public void setRow(int newVal) {
		row = newVal;
	}
	public void setCol(int newVal) {
		col = newVal;
	}

    /* *** MATRIX PRIMITIVE *** */
    public Matrix mulMatrix(Matrix m) {
        /* Mengembalikan hasil perkalian matrix self dengan matrix m */
        Matrix ret = new Matrix(getRow(), m.getCol());
        for (int i = 0; i < ret.getRow(); i++) {
            for (int j = 0; j < ret.getCol(); j++) {
                ret.val[i][j] = 0;
                for (int k = 0; k < getCol(); k++) {
                    ret.val[i][j] += val[i][k] * m.val[k][j];
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
                ret.val[i][j] *= multiplier;
            }
        }
        return  ret;
    }
    
    public Matrix transpose(){
        /* Mengembalikan transpose matrix */
        Matrix ret = this;
        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < getCol(); j++) {
                ret.val[i][j] = val[j][i];
            }
        }
        return  ret;
    }

    // public Matrix cofactor()

    // public Matrix adjoin()

    // public Matrix inverse()

    /* *** COMMON MATRIX */
    // public static Matrix one(int sz);
	
	// public static Matrix zero(int sz);

    /* *** OBE *** */
    public void swapRow(int row1, int row2) {
        /* I.S. row1 dan row2 terdefinisi
         * F.S. row1 dan row2 mempunyai elemen yang telah ditukar
         */

        for (int i = 0; i < getCol(); i++) {
            double temp = val[row1][i];
            val[row1][i] = val[row2][i];
            val[row2][i] = temp;
        }
    }

    public void divRow(int dRow, double divider) {
        /* I.S. dRow dan divider terdefinisi
         * F.S. seluruh elemen pada baris dRow dibagi dengan divider
         */

        for (int i = 0; i < getCol(); i++) {
            val[dRow][i] /= divider;
        }
    }

    public void addRow(int aRow, int rowAdder, double multiplier) {
        /* I.S. aRow, rowAdder, dan multiplier terdefinisi
         * F.S. seluruh elemen pada baris aRow ditambah dengan multiplier * rowAdder
         */

        for (int i = 0; i < getCol(); i++) {
            val[aRow][i] += multiplier * val[rowAdder][i];
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
}
