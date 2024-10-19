module com.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires atlantafx.base;

    opens com.gui to javafx.fxml;
    exports com.gui;
}
