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
        Matrix ret = new Matrix(getRow(), getCol());
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

    // public Matrix mulDouble(double Multiplier);

    // public Matrix transpose()

    // public Matrix cofactor()

    // public Matrix adjoin()

    // public Matrix inverse()

    /* *** COMMON MATRIX */
    // public static Matrix one(int sz);
	
	// public static Matrix zero(int sz);

    /* *** OBE *** */
    // public void swapRow(int row1, int row2)

    // public void divRow(int row1, double divider)

    // public void addRow(int row, int rowAdder, double multiplier)


}
