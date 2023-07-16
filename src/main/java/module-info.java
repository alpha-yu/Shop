module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;

    exports Manager;
    exports module_shared;
    exports module_login;
    exports module_main;
    exports module_signup;

    opens module_login      to javafx.fxml;
    opens module_main       to javafx.fxml;
    opens module_signup     to javafx.fxml;
    opens Manager           to javafx.fxml;
}