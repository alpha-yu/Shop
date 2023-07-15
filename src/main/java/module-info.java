module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;

    exports module_shared;
    exports module_login;

    opens module_login to javafx.fxml;
}