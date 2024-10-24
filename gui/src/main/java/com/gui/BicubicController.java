package com.gui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.gui.matrix.Bicubic;
import com.gui.matrix.lib.Matrix;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BicubicController {

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
    @FXML
    private void processInterpolation() {
        try {
            // Membagi input menjadi baris-baris
            String[] lines = input.getText().trim().split("\\n");
            
            // Validasi jumlah baris, harus ada 5 baris (4 untuk matriks, 1 untuk tx dan ty)
            if (lines.length != 5) {
                throw new IllegalArgumentException("Jumlah baris input tidak valid. Harus ada 5 baris (4 untuk matriks dan 1 untuk tx, ty).");
            }
    
            Matrix matrix = new Matrix(4, 4);
    
            // Membaca 4x4 matriks
            for (int i = 0; i < 4; i++) {
                String[] values = lines[i].trim().split("\\s+");
                
                // Validasi jumlah kolom, harus ada 4 kolom di setiap baris matriks
                if (values.length != 4) {
                    throw new IllegalArgumentException("Jumlah kolom pada baris " + (i + 1) + " tidak valid. Harus ada 4 nilai per baris.");
                }
    
                for (int j = 0; j < 4; j++) {
                    matrix.setMat(i, j, Double.parseDouble(values[j]));
                }
            }
    
            // Membaca tx dan ty dari baris kelima
            String[] lastLineValues = lines[4].trim().split("\\s+");
            
            // Validasi jumlah nilai untuk tx dan ty, harus ada 2
            if (lastLineValues.length != 2) {
                throw new IllegalArgumentException("Input untuk tx dan ty tidak valid. Harus ada 2 nilai (tx dan ty).");
            }
    
            double tx = Double.parseDouble(lastLineValues[0]);
            double ty = Double.parseDouble(lastLineValues[1]);
    
            // Melakukan interpolasi bicubic
            String result = Bicubic.bicubicInterpolation(Bicubic.flatten(matrix), tx, ty);
            jawaban.setText(result);
        } catch (NumberFormatException e) {
            jawaban.setText("Input mengandung nilai yang tidak valid. Pastikan semua nilai adalah angka.");
        } catch (IllegalArgumentException e) {
            jawaban.setText(e.getMessage());
        } catch (Exception e) {
            jawaban.setText("Terjadi kesalahan saat memproses input. Silakan coba lagi.");
        }
    }

    @FXML
    private void switchToImage() throws IOException {
        App.setRoot("imageResizer");
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
