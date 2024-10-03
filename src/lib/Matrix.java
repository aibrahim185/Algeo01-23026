public class Matrix {
    double[][] mt = null;
    int rowCnt, colCnt;

    /* *** CONSTRUCTOR *** */
    public Matrix(int x, int y) {
		mt = new double[x][y];
		rowCnt = x;
		colCnt = y;
	}
	public Matrix() {
		mt = new double[1][1];
		rowCnt = 1;
		colCnt = 1;
	}
}
