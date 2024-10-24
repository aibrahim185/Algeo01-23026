package com.gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.gui.matrix.ImageProcessing;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class ImageResizerController {
    
    @FXML
    private ImageView beforeImage; 
    @FXML
    private ImageView afterImage;  
    @FXML
    private TextField widthField;
    @FXML
    private TextField heightField;

    private File selectedFile;
    
    @FXML
    private void resizeImage() {
        if (selectedFile != null) {
            BufferedImage img;
            try {
                img = ImageIO.read(selectedFile);

                double w = Double.parseDouble(widthField.getText());
                double h = Double.parseDouble(heightField.getText());

                BufferedImage resizedImage = ImageProcessing.imageProcessing(img, w, h);
                
                File output = new File("test/image/hasil/hasil_" + selectedFile.getName());
                output.getParentFile().mkdirs();
                ImageIO.write(resizedImage, "jpg", output); 
                Image resultImage = new Image(output.toURI().toString());
                afterImage.setImage(resultImage);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid untuk lebar/tinggi.");
            }
        }
    }

    @FXML
    private void inputFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.gif"));
        selectedFile = fileChooser.showOpenDialog(beforeImage.getScene().getWindow());

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            beforeImage.setImage(image);
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
