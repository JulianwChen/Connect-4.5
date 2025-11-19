module chen.julian {
    requires javafx.controls;
    requires javafx.fxml;

    opens chen.julian to javafx.fxml;
    exports chen.julian;
}
