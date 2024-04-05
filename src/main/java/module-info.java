module com.csc345 {
    requires javafx.controls;
    requires javafx.graphics;

    opens com.csc345 to javafx.graphics;
    exports com.csc345;
}
