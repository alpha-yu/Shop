//负责人：ljy
package module_signup;

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
import module_login.module_login;
import module_shared.shared;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class module_signup extends Application {
    static Stage stage;
    static TextField user;
    static PasswordField psw;
    static PasswordField pswcheck;
    static ComboBox<String> role;

    public static void showSignup() {
        stage = new Stage();
        user = new TextField();
        psw = new PasswordField();
        pswcheck = new PasswordField();
        role = new ComboBox<>();

        stage.setTitle("注册");
        Font font = new Font("宋体", 18);
        Button btSignup = shared.init_Button_Font("注册");
        Button btBack = shared.init_Button_Font("返回");

        GridPane pane = new GridPane();
        pane.setHgap(5);
        pane.setAlignment(Pos.CENTER);

        //按钮大小字体设置
        shared.init_Button_Style(btSignup, 40, 500);
        shared.init_Button_Style(btBack, 40, 500);
        shared.button_change(btSignup);
        shared.button_change(btBack);

        //顶部pane部分
        GridPane titlePane = new GridPane();
        Label titleLabel = new Label("注册");
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
        psw.setPromptText("请输入密码");
        pswPane.add(pswLabel, 0, 0);
        pswPane.add(psw, 0, 1);
        pswPane.setVgap(5);

        //password pane部分
        GridPane pswcheckPane = new GridPane();
        Label pswcheckLabel = new Label("密码确认");
        pswcheckLabel.setFont(font);
        pswcheck.setFont(font);
        pswcheck.setPrefWidth(500);
        pswcheck.setPromptText("请再次输入密码");
        pswcheckPane.add(pswcheckLabel, 0, 0);
        pswcheckPane.add(pswcheck, 0, 1);
        pswcheckPane.setVgap(5);

        //role pane部分
        GridPane RolePane = new GridPane();
        Label roleLabel = new Label("用户类型");
        ObservableList<String> options = FXCollections.observableArrayList(shared.TEXT_CUSTOMER);
        role.setItems(options);
        role.setValue(shared.TEXT_CUSTOMER);
        role.setPrefWidth(500);
        role.setStyle("-fx-font: 18px \"Serif\";");
        roleLabel.setFont(font);
        RolePane.add(roleLabel, 0, 0);
        RolePane.add(role, 0, 1);
        RolePane.setVgap(5);

        //signup按钮相应事件，进行注册检验
        btSignup.setOnMouseClicked(e -> {
            signup_check();
        });

        //back按钮相应事件，返回登录页面
        btBack.setOnMouseClicked(e -> {
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

        //提供回车方法进行确认
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                signup_check();
            }
        });
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    //注册验证
    private static void signup_check() {
        //空用户名判断
        if (user.getText() == null || user.getText().trim().isEmpty()) {
            String title = "用户名错误";
            String warning = "请输入用户名";
            JOptionPane.showMessageDialog(null, warning, title, JOptionPane.PLAIN_MESSAGE);
            return;
        }
        //空密码判断
        if (psw.getText() == null || psw.getText().trim().isEmpty()) {
            String title = "密码错误";
            String warning = "请输入密码";
            JOptionPane.showMessageDialog(null, warning, title, JOptionPane.PLAIN_MESSAGE);
            return;
        }
        //密码二次确认检验
        if (!psw.getText().equals(pswcheck.getText())) {
            String title = "密码错误";
            String warning = "两次输入的密码不相同\n请检查后再次尝试";
            JOptionPane.showMessageDialog(null, warning, title, JOptionPane.PLAIN_MESSAGE);
            return;
        }

        //用户名重复判断
        try {
            String sql = "insert into Users(username,psw,AUTH) values(?,?,?)";
            PreparedStatement ps = shared.dbConn.prepareStatement(sql);
            ps.setString(1, user.getText());
            ps.setString(2, psw.getText());
            ps.setString(3, String.valueOf(shared.AUTH_CUSTOMER));
            ps.executeUpdate();

            String title = "成功";
            String warning = "注册成功！";
            JOptionPane.showMessageDialog(null, warning, title, JOptionPane.PLAIN_MESSAGE);
            stage.close();
            module_login.showLogin();
        } catch (SQLException e) {
            String title = "失败";
            String warning = "用户名重复！";
            JOptionPane.showMessageDialog(null, warning, title, JOptionPane.PLAIN_MESSAGE);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        showSignup();
    }
}