package com.gui;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.gui.matrix.Regression;
import com.gui.matrix.lib.Matrix;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class RegresiController {

    @FXML
    private Button process;
    @FXML
    private Label jawaban;
    @FXML
    private TextArea input;
    FileChooser fc = new FileChooser();
    
    public void inputFile() {
        File inputFile = fc.showOpenDialog(new Stage());
    }

    public void outputFile() {
        File outputFile = fc.showOpenDialog(new Stage());
    }
    
    public void initialize() {
        process.setOnAction(event -> processRegression());
    }

    private void processRegression() {
        String inputData = input.getText();
        if (inputData.isEmpty()) {
            jawaban.setText("Input tidak boleh kosong!");
            return;
        }
    
        try {
            Matrix[] matrices = parseMatrixInput(inputData);
            Matrix X = matrices[0];
            Matrix Y = matrices[1];
            Matrix XK = matrices[2]; 
    
            double resultLinier = calculateLinearRegression(X, Y, XK);
            double resultQuadratic = calculateQuadraticRegression(X, Y, XK, X.getCol() - 1);
    
            jawaban.setText("Hasil regresi Linier: " + resultLinier + "\nHasil regresi kuadratik: " + resultQuadratic);
            // jawaban.setText("Hasil regresi Linier: " + resultLinier);
        } catch (Exception e) {
            jawaban.setText("Input tidak valid: " + e.getMessage());
        }
    }

    @SuppressWarnings("resource")
    private Matrix[] parseMatrixInput(String inputData) throws Exception {
        Scanner sc = new Scanner(inputData);
        StringTokenizer st = new StringTokenizer(inputData, "\n");
    
        int m = 0;
        int n = 0;
    
        while (st.hasMoreTokens()) {
            String row = st.nextToken();
            if (!st.hasMoreTokens()) break; // Pastikan baris terakhir adalah XK
            StringTokenizer rowTokens = new StringTokenizer(row, " ");
            n = rowTokens.countTokens() - 1; 
            m++;
        }
    
        sc = new Scanner(inputData);
    
        Matrix X = new Matrix(m, n + 1); 
        Matrix Y = new Matrix(m, 1);
    
        for (int i = 0; i < m; i++) {
            X.setMat(i, 0, 1);
            for (int j = 1; j <= n; j++) {
                double xVal = sc.nextDouble();
                X.setMat(i, j, xVal); 
            }
            double yVal = sc.nextDouble();
            Y.setMat(i, 0, yVal); 
        }
        // System.out.println(n);
        // System.out.println(m);
        Y.print();
        // Membaca nilai XK di baris terakhir
        Matrix XK = new Matrix(n + 1, 1);
        XK.setMat(0, 0, 1);
        for (int i = 1; i <= n; i++) {
            double xkVal = sc.nextDouble();
            XK.setMat(i, 0, xkVal);
        }
        
        return new Matrix[]{X, Y, XK};
    }

    private double calculateLinearRegression(Matrix X, Matrix Y, Matrix XK) {
        Matrix resLinier = Regression.regresiLinier(X, Y);
        double res = 0;
        for (int i = 0; i < resLinier.getRow(); i++) {
            res += resLinier.getMat(i, 0) * XK.getMat(i, 0);
        }
        return res;
    }

    private double calculateQuadraticRegression(Matrix X, Matrix Y, Matrix XK, int n) {
        Matrix resKuadratik = Regression.regresiKuadratik(X, Y);
        Matrix XK2 = new Matrix((n * (n - 1) / 2) + 1 + n + n, 1);
        XK2.setMat(0, 0, 1);
        int idx = 1;
        for (int i = 1; i <= n; i++) {
            XK2.setMat(idx, 0, XK.getMat(i, 0));
            idx++;
        } 
        for (int i = 1; i <= n; i++) {
            XK2.setMat(idx, 0, Math.pow(XK.getMat(i, 0), 2));
            idx++;
        } 
        for (int i = 1; i <= n - 1; i++) {
            for (int j = i + 1; j <= n; j++) {
                XK2.setMat(idx, 0, XK.getMat(i, 0) * XK.getMat(j, 0));
                idx++;
            }
        } 
        double res = 0;
        int sz = ((n * (n - 1) / 2) + 1 + n + n);
        System.out.println(sz);
        for (int i = 0; i < sz; i++) {
            res += resKuadratik.getMat(i, 0) * XK2.getMat(i, 0);
        }
        return res;
    }

    @FXML
    private void switchToSPL() throws IOException {
        App.setRoot("spl");
    }

    @FXML
    private void switchToDeterminan() throws IOException {
        App.setRoot("determinan");
    }

    @FXML
    private void switchToInvers() throws IOException {
        App.setRoot("invers");
    }

    @FXML
    private void switchToPolinom() throws IOException {
        App.setRoot("interpolasiPolinom");
    }

    @FXML
    private void switchToBicubic() throws IOException {
        App.setRoot("interpolasiBicubic");
    }

    @FXML
    private void switchToRegresi() throws IOException {
        App.setRoot("regresi");
    }
}
