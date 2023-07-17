module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;

    exports module_shared;
    exports module_login;
    exports module_main;
    exports module_signup;
    exports module_order;
    exports module_purchase;
    exports module_menu;
    exports module_buyer;
    exports module_information;
    exports module_browse;
    exports module_userInfo;
    exports module_supplier_good;

    opens module_login          to javafx.fxml;
    opens module_main           to javafx.fxml;
    opens module_signup         to javafx.fxml;
    opens module_order          to javafx.fxml;
    opens module_purchase       to javafx.fxml;
    opens module_menu           to javafx.fxml;
    opens module_buyer          to javafx.fxml;
    opens module_browse         to javafx.fxml;
    opens module_userInfo       to javafx.fxml;
    opens module_information    to javafx.fxml;
    opens module_supplier_good  to javafx.fxml;
}