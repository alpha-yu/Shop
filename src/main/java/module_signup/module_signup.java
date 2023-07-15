//负责人：ljy
package module_signup;

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
import module_login.*;
import module_shared.shared;

import javax.swing.*;

public class module_signup extends Application {
    static Stage stage = new Stage();
    static TextField user = new TextField();
    static TextField psw = new TextField();
    static TextField pswcheck = new TextField();
    static ComboBox<String> role = new ComboBox<>();
    static String userTip = "Please enter the user name";
    static String pswTip = "Please enter the password";
    static String pswcheckTip = "Please enter the same passward again";

    public static void showSignup() {
        stage.setTitle("Sign Up");
        Font font = new Font("Times New Roman", 18);
        Button btSignup = new Button("Sign Up");
        Button btBack = new Button("BACK");

        GridPane pane = new GridPane();

        pane.setHgap(5);
        pane.setAlignment(Pos.CENTER);

        //按钮大小字体设置
        Font btFont = new Font("黑体", 20);
        String lightblue = "-fx-background-color: rgb(100,197,255);";
        btSignup.setPrefWidth(500);
        btBack.setPrefWidth(500);
        btSignup.setPrefHeight(40);
        btBack.setPrefHeight(40);
        btSignup.setFont(btFont);
        btBack.setFont(btFont);
        btSignup.setStyle(lightblue);
        btBack.setStyle(lightblue);
        shared.button_change(btSignup);
        shared.button_change(btBack);

        //顶部pane部分
        GridPane titlePane = new GridPane();
        Label titleLabel = new Label("SIGN UP");
        titleLabel.setFont(new Font("黑体", 45));
        titlePane.setAlignment(Pos.CENTER);
        titlePane.add(titleLabel, 0, 0);

        //user pane部分
        GridPane UserPane = new GridPane();
        Label userLabel = new Label("User Name");
        userLabel.setFont(font);
        user.setFont(font);
        user.setPrefWidth(500);
        user.setText(userTip);
        user.setStyle("-fx-text-fill:#a9a9a9;");
        UserPane.add(userLabel, 0, 0);
        UserPane.add(user, 0, 1);
        UserPane.setVgap(5);

        //password pane部分
        GridPane pswPane = new GridPane();
        Label pswLabel = new Label("Password");
        pswLabel.setFont(font);
        psw.setFont(font);
        psw.setPrefWidth(500);
        psw.setText(pswTip);
        psw.setStyle("-fx-text-fill:#a9a9a9;");
        pswPane.add(pswLabel, 0, 0);
        pswPane.add(psw, 0, 1);
        pswPane.setVgap(5);

        //password pane部分
        GridPane pswcheckPane = new GridPane();
        Label pswcheckLabel = new Label("Confirm Password");
        pswcheckLabel.setFont(font);
        pswcheck.setFont(font);
        pswcheck.setPrefWidth(500);
        pswcheck.setText(pswcheckTip);
        pswcheck.setStyle("-fx-text-fill:#a9a9a9;");
        pswcheckPane.add(pswcheckLabel, 0, 0);
        pswcheckPane.add(pswcheck, 0, 1);
        pswcheckPane.setVgap(5);

        //user文本框焦点相应事件，在文本框中给出提示
        shared.tip_focusListener(user, userTip);
        //psw文本框焦点相应事件，在文本框中给出提示
        shared.tip_focusListener(psw, pswTip);
        //pswcheck文本框焦点相应事件，在文本框中给出提示
        shared.tip_focusListener(pswcheck, pswcheckTip);

        //role pane部分
        GridPane RolePane = new GridPane();
        Label roleLabel = new Label("Role");
        ObservableList<String> options = FXCollections.observableArrayList("customer");
        role.setItems(options);
        role.setValue("customer");
        role.setPrefWidth(500);
        role.setStyle("-fx-font: 18px \"Serif\";");
        roleLabel.setFont(font);
        RolePane.add(roleLabel, 0, 0);
        RolePane.add(role, 0, 1);
        RolePane.setVgap(5);

        //signup按钮相应事件，进行注册检验
        btSignup.setOnMouseClicked(e->{
            signup_check();
        });

        //back按钮相应事件，返回登录页面
        btBack.setOnMouseClicked(e->{
            stage.close();
            module_login.showLogin();
        });

        //主体pane最终组合
        pane.setVgap(15);
        pane.setAlignment(Pos.CENTER);
        pane.add(titlePane, 0, 0);
        pane.add(UserPane, 0, 1);
        pane.add(pswPane, 0, 2);
        pane.add(pswcheckPane, 0, 3);
        pane.add(RolePane, 0, 4);
        pane.add(btSignup, 0, 5);
        pane.add(btBack, 0, 6);
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

    //注册验证
    public static void signup_check(){
        if(user.getText().equals(userTip)){
            String title = "Username Error";
            String warning = "Please enter the user name!";
            JOptionPane.showMessageDialog(null, warning, title, JOptionPane.PLAIN_MESSAGE);
            return;
        }
        if(!psw.getText().equals(pswcheck.getText())){
            String title = "Password Error";
            String warning = "The password and confirmation password are different.\nPlease check and try again!";
            JOptionPane.showMessageDialog(null, warning, title, JOptionPane.PLAIN_MESSAGE);
            return;
        }

    }

    @Override
    public void start(Stage stage) throws Exception {
        showSignup();
    }
}