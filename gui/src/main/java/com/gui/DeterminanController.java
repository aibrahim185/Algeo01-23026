package com.gui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.gui.matrix.lib.Matrix;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DeterminanController {

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
                jawaban.setText("Hasil determinan berhasil disimpan ke file.");
            } catch (IOException e) {
                jawaban.setText("Gagal menulis file: " + e.getMessage());
            }
        }
    }

    public void initialize() {
        process.setOnAction(event -> calculateDeterminant());
    }

    private void calculateDeterminant() {
        String inputData = input.getText();
        if (inputData.isEmpty()) {
            jawaban.setText("Input tidak boleh kosong!");
            return;
        }

        try {
            Matrix m = parseMatrixInput(inputData);
            StringBuilder result = new StringBuilder();

            // Calculate determinant using Row Reduction
            result.append("Determinan (Reduksi Baris): ");
            result.append(m.determinanReduksiBaris()).append("\n");

            // Calculate determinant using Cofactor Expansion
            result.append("Determinan (Ekspansi Kofaktor): ");
            result.append(m.determinanEkspansiKofaktor()).append("\n");

            jawaban.setText(result.toString());

        } catch (Exception e) {
            jawaban.setText("Input tidak valid: " + e.getMessage());
        }
    }

    private Matrix parseMatrixInput(String inputData) throws Exception {
        Scanner sc = new Scanner(inputData);
        StringTokenizer st = new StringTokenizer(inputData, "\n");

        int rowCount = 0;
        int colCount = 0;

        // Count rows and columns
        while (st.hasMoreTokens()) {
            String row = st.nextToken();
            StringTokenizer rowTokens = new StringTokenizer(row, " ");
            colCount = rowTokens.countTokens();
            rowCount++;
        }

        sc = new Scanner(inputData);
        Matrix m = new Matrix(rowCount, colCount);

        // Fill matrix with input values
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                double val = sc.nextDouble();
                m.setMat(i, j, val);
            }
        }

        return m;
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
