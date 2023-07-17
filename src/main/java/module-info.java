module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;

    exports module_browse;
    exports module_buyer;
    exports module_login;
    exports module_main;
    exports module_menu;
    exports module_order;
    exports module_purchase;
    exports module_shared;
    exports module_signup;
    exports module_supplier;
    exports module_supplier_good;
    exports module_trolley;
    exports module_userInfo;

    opens module_browse         to javafx.fxml;
    opens module_buyer          to javafx.fxml;
    opens module_login          to javafx.fxml;
    opens module_main           to javafx.fxml;
    opens module_menu           to javafx.fxml;
    opens module_order          to javafx.fxml;
    opens module_purchase       to javafx.fxml;
    opens module_shared         to javafx.fxml;
    opens module_signup         to javafx.fxml;
    opens module_supplier       to javafx.fxml;
    opens module_supplier_good  to javafx.fxml;
    opens module_trolley        to javafx.fxml;
    opens module_userInfo       to javafx.fxml;
}