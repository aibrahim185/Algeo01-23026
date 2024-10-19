package com.gui;

import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.gui.matrix.Regression;
import com.gui.matrix.lib.Matrix;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class RegresiController {

    @FXML
    private Button process;
    private Label jawaban;
    private TextArea input;

    public void initialize() {
        process.setOnAction(event -> processRegression());
        // System.out.println("p");
    }

    private void processRegression() {
        // System.out.println("q");
        String inputData = input.getText();
        if (inputData.isEmpty()) {
            jawaban.setText("Input tidak boleh kosong!");
            return;
        }

        try {
            Matrix[] matrices = parseMatrixInput(inputData);
            Matrix X = matrices[0];
            Matrix Y = matrices[1];

            double result = calculateLinearRegression(X, Y);

            jawaban.setText("Hasil regresi: " + result);
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
            StringTokenizer rowTokens = new StringTokenizer(row, " ");
            n = rowTokens.countTokens() - 1; 
            m++;
        }

        sc = new Scanner(inputData);

        Matrix X = new Matrix(m, n + 1); // Tambahkan kolom untuk konstanta 1
        Matrix Y = new Matrix(m, 1);

        for (int i = 0; i < m; i++) {
            X.setMat(i, 0, 1); // Kolom pertama diisi dengan 1 untuk regresi linier
            for (int j = 1; j <= n; j++) {
                double xVal = sc.nextDouble();
                X.setMat(i, j, xVal); // Set nilai X
            }
            double yVal = sc.nextDouble();
            Y.setMat(i, 0, yVal); // Set nilai Y
        }
        sc.close();
        return new Matrix[]{X, Y};
    }

    private double calculateLinearRegression(Matrix X, Matrix Y) {
        Matrix resLinier = Regression.regresiLinier(X, Y);
        double res = 0;
        for (int i = 0; i < resLinier.getRow(); i++) {
            res += resLinier.getMat(i, 0);
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
        App.setRoot("interpolasiBicubic");
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
