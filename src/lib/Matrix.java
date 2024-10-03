public class Matrix {
    double[][] mtx = null;
    int rowCnt, colCnt;

    /* *** CONSTRUCTOR *** */
    public Matrix(int x, int y) {
		mtx = new double[x][y];
		rowCnt = x;
		colCnt = y;
	}
	public Matrix() {
		mtx = new double[1][1];
		rowCnt = 1;
		colCnt = 1;
	}

    /* *** SELECTOR *** */
    public double getMat(int r, int c) {
		return mtx[r][c];
	}
	public int getRow() {
		return rowCnt;
	}
	public int getCol() {
		return colCnt;
	}
	
	public void setMat(int r, int c, double newVal) {
		mtx[r][c] = newVal;
	}
	public void setRow(int newVal) {
		rowCnt = newVal;
	}
	public void setCol(int newVal) {
		colCnt = newVal;
	}

    /* *** MATRIX PRIMITIVE */
    // public Matrix mulMatrix(Matrix matrix);

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
