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
import module_menu.*;
import module_shared.shared;
import module_signup.module_signup;

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
        Button btLogin = shared.init_Button_Font("登录");
        Button btSignup = shared.init_Button_Font("注册");
        GridPane pane = new GridPane();

        pane.setHgap(5);
        pane.setAlignment(Pos.CENTER);

        //按钮大小字体设置
        shared.init_Button_Style(btLogin, 40, shared.width);
        shared.init_Button_Style(btSignup, 40, shared.width);
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
        user.setPrefWidth(shared.width);
        user.setPromptText("请输入您的用户名");
        UserPane.add(userLabel, 0, 0);
        UserPane.add(user, 0, 1);
        UserPane.setVgap(5);

        //password pane部分
        GridPane pswPane = new GridPane();
        Label pswLabel = new Label("密码");
        pswLabel.setFont(font);
        psw.setFont(font);
        psw.setPrefWidth(shared.width);
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
        role.setPrefWidth(shared.width);
        role.setStyle("-fx-font: 18px \"Serif\";");
        roleLabel.setFont(font);
        RolePane.add(roleLabel, 0, 0);
        RolePane.add(role, 0, 1);
        RolePane.setVgap(5);

        //login按钮相应事件，进行登录验证
        btLogin.setOnMouseClicked(e -> {
            loginExecute();
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

    private static void loginExecute() {
        try {
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
                jump_to_menu(user.getText(), psw.getText(), shared.text_to_AUTH(role.getValue()));
            }
            //登录失败
            else {
                String title = "登录失败";
                String warning = "请检查用户名和密码，并选择对应身份类型！";
                JOptionPane.showMessageDialog(null, warning, title, JOptionPane.PLAIN_MESSAGE);
            }
        } catch (SQLException e) {
            String title = "登录失败";
            String warning = "用户名或密码错误！";
            JOptionPane.showMessageDialog(null, warning, title, JOptionPane.PLAIN_MESSAGE);
        }
    }

    private static void jump_to_menu(String name, String psw, int auth) {
        if (auth == shared.AUTH_PURCHASER) {
            menu_supplier menu = new menu_supplier(name, psw, auth);
            stage.close();
            menu.showMenuSupplier();
        }
        if (auth == shared.AUTH_MANAGER) {
            menu_manager menu = new menu_manager(name, psw, auth);
            stage.close();
            menu.ShowMenu();
        }
        if (auth == shared.AUTH_ADMINISTRATOR) {
            menu_admin menu = new menu_admin(name, psw, auth);
            stage.close();
            menu.showMenuAdmin();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        showLogin();
    }
}