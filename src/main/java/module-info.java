module com.csc345 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.csc345 to javafx.fxml;
    exports com.csc345;
}
