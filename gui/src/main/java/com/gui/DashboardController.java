package com.gui;

import java.io.IOException;

import javafx.fxml.FXML;

public class DashboardController {

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
