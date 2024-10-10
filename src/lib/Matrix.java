package lib;
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
        Matrix ret = this;
        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < getCol(); j++) {
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
        Matrix res = new Matrix(getRow(), getCol());
        for (int i = 0; i<getRow();i++){
            for (int j = 0; j<getCol(); j++){
                Matrix temp = this.cofactor(i, j);
                res.elmnt[i][j] = temp.determinanEkspansiKofaktor();
                if ((i + j) % 2 != 0) {
                    res.elmnt[i][j] *= -1;
                }
            }
        }
        return res;
    }

    public Matrix adjoin() {
        Matrix temp = this.MatCof();
        temp.transpose();
        return temp;
    }

    public Matrix inverse() {
        /* mengembalikkan matriks invers */
        double det = determinanEkspansiKofaktor();
        Matrix temp = new Matrix(getRow(), getCol());
        temp = adjoin().mulDouble(1/det);
        return temp;
    }

    /* *** COMMON MATRIX */
    // public static Matrix one(int sz);
	
	// public static Matrix zero(int sz);

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

    // public void metodeBalikan()

    // public void kaidahCramer()

    /* *** Determinan *** */
    public double determinanReduksiBaris() {
        Matrix temp = new Matrix(getRow(), getCol());
    
        // copy matrix 
        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < getCol(); j++) {
                temp.setMat(i, j, getMat(i, j));
            }
        }
    
        // swap baris 
        double swapCount = 0;
        for (int i = 0; i < getRow() - 1; i++) {
            if (temp.getMat(i, 0) == 0) {
                temp.swapRow(i, i + 1);
                swapCount++;
            }
        }
    
        // reduksi baris
        for (int i = 0; i < getRow() - 1; i++) {
            for (int j = i + 1; j < getRow(); j++) {
                double pivot = temp.getMat(i, i);
                if (pivot == 0) continue;
    
                double factor = temp.getMat(j, i) / pivot;
                for (int k = i; k < getCol(); k++) {
                    temp.setMat(j, k, temp.getMat(j, k) - factor * temp.getMat(i, k));
                }
            }
        }
    
        double determinant = 1;
        for (int i = 0; i < getRow(); i++) {
            determinant *= temp.getMat(i, i);
        }
    
        return determinant * Math.pow(-1, swapCount);
    }
    

    public double determinanEkspansiKofaktor() {
        double ret = 0;
        int sign = 1;
    
        if (getRow() == 1) {
            // jika matriks 1x1, determinannya adalah elemen itu sendiri
            ret = (double) getMat(0, 0);
        } else {
            // ekspansi kofaktor untuk matriks berukuran lebih dari 1
            for (int i = 0; i < getRow(); i++) {
                // membuat matriks minor
                Matrix temp = cofactor(0, i);
    
                // menambahkan ke hasil determinan
                ret += sign * getMat(0, i) * temp.determinanEkspansiKofaktor(); 
                sign *= -1; 
            }
        }
    
        return ret;
    }
    
}
