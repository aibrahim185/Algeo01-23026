package com.gui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.gui.matrix.Interpolate;
import com.gui.matrix.lib.Matrix;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PolinomController {

    @FXML
    private Button process;
    @FXML
    private Label jawaban;
    @FXML
    private TextArea input;
    FileChooser fc = new FileChooser();
    
    public void inputFile() {
        File inputFile = fc.showOpenDialog(new Stage());
        if (inputFile != null) {
            try (Scanner sc = new Scanner(inputFile)) {
                StringBuilder inputData = new StringBuilder();
                while (sc.hasNextLine()) {
                    inputData.append(sc.nextLine()).append("\n");
                }
                input.setText(inputData.toString());
            } catch (IOException e) {
                jawaban.setText("Gagal membaca file: " + e.getMessage());
            }
        }
    }

    public void outputFile() {
        String outputText = jawaban.getText();
        File outputFile = fc.showSaveDialog(new Stage());
        if (outputFile != null) {
            try (FileWriter writer = new FileWriter(outputFile)) {
                writer.write(outputText); 
                jawaban.setText("Hasil interpolasi berhasil disimpan ke file.");
            } catch (IOException e) {
                jawaban.setText("Gagal menulis file: " + e.getMessage());
            }
        }
    }

    public void initialize() {
        process.setOnAction(event -> processInterpolation());
    }

    private void processInterpolation() {
        String inputData = input.getText();
        if (inputData.isEmpty()) {
            jawaban.setText("Input tidak boleh kosong!");
            return;
        }
    
        try {
            InterpolationInput parsedInput = parseInputData(inputData);
            Matrix coefficients = Interpolate.interpolasiLinier(parsedInput.X, parsedInput.XP, parsedInput.YV);
            double fxResult = calculateFx(coefficients, parsedInput.predictX);
            String formattedResult = formatInterpolationResult(coefficients, parsedInput.predictX, fxResult);
            jawaban.setText(formattedResult);
        } catch (Exception e) {
            jawaban.setText("Input tidak valid: " + e.getMessage());
        }
    }

    private InterpolationInput parseInputData(String inputData) throws Exception {
        Scanner sc = new Scanner(inputData);
        StringTokenizer st = new StringTokenizer(inputData, "\n");
    
        int n = 0;
        double predictX = 0;

        while (st.hasMoreTokens()) {
            String row = st.nextToken();
            if (!st.hasMoreTokens()) break;
            n++;
        }

        sc = new Scanner(inputData);

        Matrix X = new Matrix(n, n);
        Matrix XP = new Matrix(n, 1);
        Matrix YV = new Matrix(n, 1);

        for (int i = 0; i < n; i++) {
            double xVal = sc.nextDouble();
            XP.setMat(i, 0, xVal);
            double yVal = sc.nextDouble();
            YV.setMat(i, 0, yVal);

            for (int j = 0; j < n; j++) {
                X.setMat(i, j, Math.pow(xVal, j));
            }
        }

        predictX = sc.nextDouble();
        
        return new InterpolationInput(X, XP, YV, predictX);
    }

    private double calculateFx(Matrix coefficients, double xVal) {
        double result = 0;
        for (int i = 0; i < coefficients.getRow(); i++) {
            result += coefficients.getMat(i, 0) * Math.pow(xVal, i);
        }
        return result;
    }

    private String formatInterpolationResult(Matrix coefficients, double predictX, double fxResult) {
        StringBuilder result = new StringBuilder("f(x) = ");

        for (int i = coefficients.getRow() - 1; i >= 0; i--) {
            double coef = coefficients.getMat(i, 0);
            if (coef < 0) {
                result.append(String.format("- %.4fx^%d ", Math.abs(coef), i));
            } else if (i != coefficients.getRow() - 1) {
                result.append(String.format("+ %.4fx^%d ", coef, i));
            } else {
                result.append(String.format("%.4fx^%d ", coef, i));
            }
        }

        result.append(String.format(", f(%.4f) = %.4f", predictX, fxResult));
        return result.toString().replace("^1 ", " ").replace("x^0", "");
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
    private void switchToRegresi() throws IOException {
        App.setRoot("regresi");
    }

    @FXML
    private void switchToBicubic() throws IOException {
        App.setRoot("interpolasiBicubic");
    }

    @FXML
    private void switchToPolinom() throws IOException {
        App.setRoot("interpolasiPolinom");
    }

    // Helper class to store parsed input
    private class InterpolationInput {
        Matrix X;
        Matrix XP;
        Matrix YV;
        double predictX;

        public InterpolationInput(Matrix X, Matrix XP, Matrix YV, double predictX) {
            this.X = X;
            this.XP = XP;
            this.YV = YV;
            this.predictX = predictX;
        }
    }
}
