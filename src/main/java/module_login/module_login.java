//负责人：ljy
package module_login;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import module_shared.shared;

import javax.swing.*;
import java.sql.DriverManager;

public class module_login extends Application {
    static Stage stage = new Stage();
    static TextField user = new TextField();
    static TextField psw = new TextField();
    static ComboBox<String> role=new ComboBox<>();

    public static void showLogin() {
        stage.setTitle("Login");
        Font font = new Font("Times New Roman", 18);
        Button btLogin = new Button("Log In");
        Button btSignup = new Button("Sign Up");
        GridPane pane = new GridPane();

        pane.setHgap(5);
        pane.setAlignment(Pos.CENTER);

        btLogin.setPrefWidth(500);
        btSignup.setPrefWidth(500);
        btLogin.setPrefHeight(40);
        btSignup.setPrefHeight(40);
        btLogin.setFont(new Font("黑体", 20));
        btSignup.setFont(new Font("黑体", 20));

        GridPane titlePane = new GridPane();
        Label titleLabel = new Label("SHOP MANAGERMENT");
        titleLabel.setFont(new Font("黑体", 45));
        titlePane.setAlignment(Pos.CENTER);
        titlePane.add(titleLabel,0,0);

        GridPane UserPane = new GridPane();
        Label userLabel = new Label("User Name");
        userLabel.setFont(font);
        user.setFont(font);
        user.setPrefWidth(500);
        UserPane.add(userLabel, 0, 0);
        UserPane.add(user, 0, 1);
        UserPane.setVgap(5);

        GridPane pswPane = new GridPane();
        Label pswLabel = new Label("Password");
        pswLabel.setFont(font);
        psw.setFont(font);
        psw.setPrefWidth(500);
        pswPane.add(pswLabel, 0, 0);
        pswPane.add(psw, 0, 1);
        pswPane.setVgap(5);

        GridPane RolePane =new GridPane();
        Label roleLabel =new Label("Role");
        ObservableList<String> options = FXCollections.observableArrayList("Option 1","Option 2","Option 3");
        role.setItems(options);
        roleLabel.setFont(font);
        role.setPrefWidth(500);
        RolePane.add(roleLabel, 0, 0);
        RolePane.add(role, 0, 1);
        RolePane.setVgap(5);




//        btLogin.setOnMouseClicked(e -> {
//            loginExecute();
//        });
//
//        btLogin.setOnKeyPressed(e -> {
//            if (e.getCode() == KeyCode.ENTER) {
//                loginExecute();
//            }
//        });
//
//        btSignup.setOnMouseClicked(e -> {
//            System.exit(0);
//        });

        pane.setVgap(15);
        pane.setAlignment(Pos.CENTER);
        pane.add(titlePane, 0, 0);
        pane.add(UserPane, 0, 1);
        pane.add(pswPane, 0, 2);
        pane.add(RolePane, 0, 3);
        pane.add(btLogin, 0, 4);
        pane.add(btSignup, 0, 5);
        Scene scene = new Scene(pane, 800, 600);
//        scene.setOnKeyPressed(e -> {
//            if (e.getCode() == KeyCode.ENTER) {
//                loginExecute();
//            }
//        });
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void loginExecute() {

    }

    @Override
    public void start(Stage stage) throws Exception {
        showLogin();
    }
}