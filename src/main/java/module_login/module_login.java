//负责人：ljy
package module_login;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import module_shared.*;
import module_signup.*;
import module_menu.*;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class module_login extends Application {
    static Stage stage;
    static TextField user;
    static PasswordField psw;
    static ComboBox<String> role;

    public static void showLogin() {
        stage = new Stage();
        user = new TextField();
        psw = new PasswordField();
        role = new ComboBox<>();

        stage.setTitle("登录");
        Font font = new Font("宋体", 18);
        Button btLogin = new Button("登录");
        Button btSignup = new Button("注册");
        GridPane pane = new GridPane();

        pane.setHgap(5);
        pane.setAlignment(Pos.CENTER);

        //按钮大小字体设置
        Font btFont = new Font("黑体", 20);
        btLogin.setPrefWidth(500);
        btSignup.setPrefWidth(500);
        btLogin.setPrefHeight(40);
        btSignup.setPrefHeight(40);
        btLogin.setFont(btFont);
        btSignup.setFont(btFont);
        btLogin.setStyle(shared.blue_background + shared.white_text);
        btSignup.setStyle(shared.blue_background + shared.white_text);
        shared.button_change(btLogin);
        shared.button_change(btSignup);

        //顶部pane部分
        GridPane titlePane = new GridPane();
        Label titleLabel = new Label("超市管理系统");
        titleLabel.setFont(new Font("黑体", 45));
        titlePane.setAlignment(Pos.CENTER);
        titlePane.add(titleLabel, 0, 0);

        //user pane部分
        GridPane UserPane = new GridPane();
        Label userLabel = new Label("用户名");
        userLabel.setFont(font);
        user.setFont(font);
        user.setPrefWidth(500);
        user.setPromptText("请输入您的用户名");
        UserPane.add(userLabel, 0, 0);
        UserPane.add(user, 0, 1);
        UserPane.setVgap(5);

        //password pane部分
        GridPane pswPane = new GridPane();
        Label pswLabel = new Label("密码");
        pswLabel.setFont(font);
        psw.setFont(font);
        psw.setPrefWidth(500);
        psw.setPromptText("请输入您的密码");
        pswPane.add(pswLabel, 0, 0);
        pswPane.add(psw, 0, 1);
        pswPane.setVgap(5);

        //role pane部分
        GridPane RolePane = new GridPane();
        Label roleLabel = new Label("用户类型");
        ObservableList<String> options = FXCollections.observableArrayList(shared.TEXT_CUSTOMER, shared.TEXT_SELLER, shared.TEXT_PURCHASER, shared.TEXT_MANAGER, shared.TEXT_ADMINISTRATOR);
        role.setItems(options);
        role.setValue(shared.TEXT_CUSTOMER);
        role.setPrefWidth(500);
        role.setStyle("-fx-font: 18px \"Serif\";");
        roleLabel.setFont(font);
        RolePane.add(roleLabel, 0, 0);
        RolePane.add(role, 0, 1);
        RolePane.setVgap(5);

        //login按钮相应事件，进行登录验证
        btLogin.setOnMouseClicked(e -> {
            if(loginExecute()){
                jump_to_menu(role.getValue());
            }
        });

        //signup按钮相应事件，跳转至signup界面，关闭当前界面
        btSignup.setOnMouseClicked(e -> {
            stage.close();
            module_signup.showSignup();
        });

        //主体pane最终组合
        pane.setVgap(15);
        pane.setAlignment(Pos.CENTER);
        pane.add(titlePane, 0, 0);
        pane.add(UserPane, 0, 1);
        pane.add(pswPane, 0, 2);
        pane.add(RolePane, 0, 3);
        pane.add(btLogin, 0, 4);
        pane.add(btSignup, 0, 5);
        Scene scene = new Scene(pane, 800, 600);

        //提供回车方法进行确认
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                loginExecute();
            }
        });
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private static boolean loginExecute() {
        try {
            //空用户名判断
            if (user.getText() == null || user.getText().trim().isEmpty()) {
                String title = "用户名错误";
                String warning = "请输入用户名";
                JOptionPane.showMessageDialog(null, warning, title, JOptionPane.PLAIN_MESSAGE);
                return false;
            }
            //空密码判断
            if (psw.getText() == null || psw.getText().trim().isEmpty()) {
                String title = "密码错误";
                String warning = "请输入密码";
                JOptionPane.showMessageDialog(null, warning, title, JOptionPane.PLAIN_MESSAGE);
                return false;
            }

            String sql = "select * from Users where username = ? and psw = ? and AUTH = ?";
            PreparedStatement ps = shared.dbConn.prepareStatement(sql);
            ps.setString(1, user.getText());
            ps.setString(2, psw.getText());
            ps.setString(3, String.valueOf(shared.text_to_AUTH(role.getValue())));
            ResultSet rs = ps.executeQuery();

            //登陆成功
            if (rs.next()) {
                String title = "登录成功";
                String warning = "登陆成功";
                JOptionPane.showMessageDialog(null, warning, title, JOptionPane.PLAIN_MESSAGE);
                return true;
            }
            //登录失败
            else{
                String title = "登录失败";
                String warning = "请检查用户名和密码，并选择对应身份类型！";
                JOptionPane.showMessageDialog(null, warning, title, JOptionPane.PLAIN_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            String title = "登录失败";
            String warning = "用户名或密码错误！";
            JOptionPane.showMessageDialog(null, warning, title, JOptionPane.PLAIN_MESSAGE);
            return false;
        }
    }

    private static void jump_to_menu(String role) {
        int auth=shared.text_to_AUTH(role);
        if(auth==shared.AUTH_CUSTOMER){
            stage.close();
        }
        if(auth==shared.AUTH_SELLER){
            stage.close();
        }
        if(auth==shared.AUTH_PURCHASER){
            stage.close();
        }
        if(auth==shared.AUTH_MANAGER){
            stage.close();
        }
        if(auth==shared.AUTH_ADMINISTRATOR) {
            menu_admin.showMenuAdmin();
            stage.close();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        showLogin();
    }
}