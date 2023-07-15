package module_login;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import module_shared.shared;

import javax.swing.*;
import java.sql.DriverManager;

public class module_login extends Application{
    static Stage stage = new Stage();
    static TextField user = new TextField("sa");
    static TextField password = new TextField("123456");
    static TextField DB = new TextField("Java");

    @Override
    public void start(Stage stage) throws Exception {
        showLogin();
    }

    public static void showLogin() {
        stage.setTitle("Login");
        Button btOK = new Button("OK");
        Button btCancel = new Button("Cancel");
        Font font = new Font("Times New Roman", 13);

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(8);
        GridPane setPane = new GridPane();

        btOK.setPrefWidth(70);
        btCancel.setPrefWidth(70);
        setPane.setAlignment(Pos.CENTER);
        setPane.setHgap(10);
        setPane.add(btOK, 0, 0);
        setPane.add(btCancel, 1, 0);

        user.setFont(font);
        user.setPrefWidth(100);
        password.setFont(font);
        password.setPrefWidth(100);
        DB.setFont(font);
        DB.setPrefWidth(100);
        DB.setEditable(true);
        GridPane UserPane = new GridPane();
        GridPane PasswordPane = new GridPane();
        GridPane DBPane = new GridPane();

        Label UserLabel = new Label("User:");
        UserLabel.setPrefWidth(80);
        UserPane.add(UserLabel, 0, 0);
        UserPane.add(user, 1, 0);
        Label PasswordLabel = new Label("Password:");
        PasswordLabel.setPrefWidth(80);
        PasswordPane.add(PasswordLabel, 0, 0);
        PasswordPane.add(password, 1, 0);
        Label DBLabel = new Label("Database:");
        DBLabel.setPrefWidth(80);
        DBPane.add(DBLabel, 0, 0);
        DBPane.add(DB, 1, 0);
        pane.add(UserPane, 0, 0);
        pane.add(PasswordPane, 0, 1);
        pane.add(DBPane, 0, 2);
        pane.add(setPane, 0, 3);

        btOK.setOnMouseClicked(e -> {
            loginExecute();
        });

        btOK.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                loginExecute();
            }
        });

        btCancel.setOnMouseClicked(e -> {
            System.exit(0);
        });

        Scene scene = new Scene(pane, 220, 160);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                loginExecute();
            }
        });
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void loginExecute() {
        try {
            shared.userStr = user.getText();
            shared.passwordStr = password.getText();
            String DBStr = DB.getText();
            String DBTemp = shared.dbURL + DBStr;

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Load Success!");

            shared.dbConn = DriverManager.getConnection(DBTemp, shared.userStr, shared.passwordStr);
            shared.dbURL = DBTemp;
            System.out.println("Connection Success!");
            stage.close();
        } catch (Exception ex) {
            String title = "Connection Error";
            String warning = "Check the user and password!";
            JOptionPane.showMessageDialog(null, warning, title, JOptionPane.PLAIN_MESSAGE);
        }
    }

//    public static void main(String[] args){
//        launch();
//    }
}