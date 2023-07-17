package module_main;

import javafx.application.Application;
import javafx.stage.Stage;
import module_login.module_login;
import module_shared.shared;

import javax.swing.*;
import java.sql.DriverManager;

public class module_main extends Application {
    public static void main(String[] args) {
        SQL_connect();
        launch();
    }

    public static void SQL_connect() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//          System.out.println("Load Success!");
            shared.dbConn = DriverManager.getConnection(shared.dbURL, shared.userStr, shared.passwordStr);
//          System.out.println("Connection Success!");
        } catch (Exception ex) {
            String title = "Connection Error";
            String warning = "Check the user and password!";
            JOptionPane.showMessageDialog(null, warning, title, JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        module_login.showLogin();
    }
}