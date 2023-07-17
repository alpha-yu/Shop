module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;
    requires mssql.jdbc;

    exports module_manager;
    exports module_shared;
    exports module_login;
    exports module_main;
    exports module_signup;
    exports module_order;
    exports module_purchase;
    exports module_menu;
    exports module_supplier;
    exports module_supplier_good;

    opens module_login          to javafx.fxml;
    opens module_main           to javafx.fxml;
    opens module_signup         to javafx.fxml;
    opens module_manager        to javafx.fxml;
    opens module_order          to javafx.fxml;
    opens module_purchase       to javafx.fxml;
    opens module_menu           to javafx.fxml;
    opens module_supplier       to javafx.fxml;
    opens module_supplier_good  to javafx.fxml;
}