package module_information;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import module_main.module_main;
import module_shared.shared;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static module_shared.shared.*;

public class UserInfo extends Application {
    private String userId = "GK1001";
    private String password;
    private String connection;
    private String userAddress;
    private int auth;
    public UserInfo(String userId, int auth) {
        this.userId = userId;
        this.auth = auth;
    }
    public static HBox initHBox(String title, String info){
        HBox hBox = new HBox(20);
        //hBox.setPrefWidth(30);
        hBox.setPrefHeight(50);
        hBox.setStyle(shared.grey_background + shared.round);
        //hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(0, 0, 0, 20));
        Label label = init_funcLabel_Font(title);
        TextField textField = new TextField(info);
        textField.setDisable(true); // 设置为不可编辑
        textField.setFont(func_font);
        //nameTextField.setStyle("-fx-text-fill: red; -fx-background-color: yellow;");
        hBox.getChildren().addAll(label, textField);
        return hBox;
    }
    //初始化用户信息
    private void initData() {
        //连接数据库，从数据库中读
        String sql = "select * from Users where username = ?;";
        module_main.SQL_connect();
        try {
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setString(1, this.userId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                this.password = rs.getString(2);
                this.connection = rs.getString(4);
                this.userAddress = rs.getString(5);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //将新密码写回数据库
    private void updateDB(){
        module_main.SQL_connect();
        String sql = "UPDATE Users SET psw = ? WHERE username = ?";
        try {
            PreparedStatement statement = dbConn.prepareStatement(sql);
            statement.setString(1, this.password);
            statement.setString(2, this.userId);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("User Information");
        // 创建布局容器
        initData();     //初始化数据
        // 用户姓名
        Label titleTip = new Label("个人信息");
        init_titleLabel_Font(titleTip);
        // 用户ID
        HBox hBoxId = initHBox("账号：", userId);
        //用户联系方式
        HBox hBoxConnet = initHBox("电话：", connection);
        // 用户地址
        HBox hBoxAderss = initHBox("地址：", userAddress);
        // 修改密码按钮
        Button changePasswordButton = init_Button_Font("修改密码");
        init_Button_Style(changePasswordButton, changePasswordButton.getPrefHeight(), changePasswordButton.getPrefWidth());
        button_change(changePasswordButton);
        // 查询订单按钮
        Button searchButton = init_Button_Font("订单查询");
        init_Button_Style(searchButton, searchButton.getPrefHeight(), searchButton.getPrefWidth());
        button_change(searchButton);
        //修改密码
        changePasswordButton.setOnAction(event -> {
            if(this.auth == 4) {
                NewPasswordInputDialog newPasswordInputDialog = new NewPasswordInputDialog();
                String newPassword = newPasswordInputDialog.showAndWait();
                this.password = newPassword;
                showSuccessDialog("修改密码成功");
                updateDB();
            } else {
                PasswordInputDialog passwordInputDialog = new PasswordInputDialog();
                String password = passwordInputDialog.showAndWait();
                // 检查密码是否正确
                if (checkPassword(password)) {
                    NewPasswordInputDialog newPasswordInputDialog = new NewPasswordInputDialog();
                    String newPassword = newPasswordInputDialog.showAndWait();
                    this.password = newPassword;
                    showSuccessDialog("修改密码成功");
                    updateDB();
                } else {
                    showErrorDialog("密码输入错误，请重新输入");
                }
            }
        });
        //查询订单
        searchButton.setOnAction(event -> {
            // 在此处实现订单查询的逻辑
//            OrderInterfaceOutline orderInterfaceOutline = new OrderInterfaceOutline(3);
//            try {
//                orderInterfaceOutline.start(new Stage());
//                primaryStage.close();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
        });

        HBox hBoxButton = new HBox(200);
        hBoxButton.setAlignment(Pos.CENTER);
        hBoxButton.setPadding(new Insets(20));
        hBoxButton.getChildren().addAll(changePasswordButton, searchButton);
        // 创建一个VBox容器，并将布局添加到其中
        VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(100));
        //vbox.setAlignment(Pos.CENTER);
        // 创建一个HBox容器，并将VBox布局添加到其中
        vbox.getChildren().addAll(titleTip, hBoxId, hBoxConnet, hBoxAderss, hBoxButton);
        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    private boolean checkPassword(String password) {
        // 检查密码是否正确的逻辑
        return password.equals(this.password);
    }
    private void updatePassword(String newPassword) {
        // 更新密码的逻辑
        System.out.println("密码已更新为：" + newPassword);
    }
    private void showSuccessDialog(String message) {
        // 弹出密码修改成功提示框的逻辑
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("成功");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showErrorDialog(String message) {
        // 弹出错误提示框的逻辑
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("错误");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public class PasswordInputDialog {
        private TextField passwordField;
        private Button confirmButton;
        private Stage stage;
        private String password;
        //输入密码界面
        public PasswordInputDialog() {
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            Label tip = new Label("密码：");
            tip.setStyle("-fx-font-size: 15px;");
            passwordField = new PasswordField();
            passwordField.setPromptText("请输入密码");
            confirmButton = new Button("确认");
            init_Button_Style(confirmButton, confirmButton.getPrefHeight(), confirmButton.getPrefWidth());
            button_change(confirmButton);
            confirmButton.setOnAction(event -> {
                password = passwordField.getText();
                stage.close();
            });
            HBox hBox = new HBox(10);
            hBox.getChildren().addAll(tip, passwordField);
            hBox.setPadding(new Insets(10));
            VBox vbox = new VBox(10);
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(10));
            vbox.getChildren().addAll(hBox, confirmButton);
            Scene scene = new Scene(vbox);
            stage.setScene(scene);
        }
        public String showAndWait() {
            stage.showAndWait();
            return password;
        }
    }
    public class NewPasswordInputDialog {
        private PasswordField newPasswordField;
        private PasswordField confirmNewPasswordField;
        private Button confirmButton;
        private Stage stage;
        private String newPassword ;
        public NewPasswordInputDialog() {
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            Label tip1 = new Label("输入新密码：");
            tip1.setStyle("-fx-font-size: 15px;");
            newPasswordField = new PasswordField();
            newPasswordField.setPromptText("请输入新密码");
            HBox hBox1 = new HBox(10);
            hBox1.setPadding(new Insets(10));
            hBox1.getChildren().addAll(tip1, newPasswordField);

            Label tip2 = new Label("确认新密码：");
            tip2.setStyle("-fx-font-size: 15px;");
            confirmNewPasswordField = new PasswordField();
            confirmNewPasswordField.setPromptText("请确认新密码");
            HBox hBox2 = new HBox(10);
            hBox2.setPadding(new Insets(10));
            hBox2.getChildren().addAll(tip2, confirmNewPasswordField);

            confirmButton = new Button("确认");
            init_Button_Style(confirmButton, confirmButton.getPrefHeight(), confirmButton.getPrefWidth());
            button_change(confirmButton);
            //确定按钮事件驱动
            confirmButton.setOnAction(event -> {
                String newPassword = newPasswordField.getText();
                String confirmNewPassword = confirmNewPasswordField.getText();
                if (newPassword.equals(confirmNewPassword)) {
                    this.newPassword = newPassword;
                    stage.close();
                } else {
                    showErrorDialog("新密码输入不一致，请重新输入");
                }
            });
            VBox vbox = new VBox(10);
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(10));
            vbox.getChildren().addAll(hBox1, hBox2, confirmButton);
            Scene scene = new Scene(vbox);
            stage.setScene(scene);
        }
        public String showAndWait() {
            stage.showAndWait();
            return newPassword;
        }
    }
}