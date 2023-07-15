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

public class module_login extends Application {
    static Stage stage = new Stage();
    static TextField user = new TextField();
    static TextField psw = new TextField();
    static ComboBox<String> role = new ComboBox<>();

    public static void showLogin() {
        stage.setTitle("Login");
        Font font = new Font("Times New Roman", 18);
        Button btLogin = new Button("Log In");
        Button btSignup = new Button("Sign Up");
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
        btLogin.setStyle("-fx-background-color: rgb(100,197,255);");
        btSignup.setStyle("-fx-background-color: rgb(100,197,255);");
        button_change(btLogin);
        button_change(btSignup);

        //顶部pane部分
        GridPane titlePane = new GridPane();
        Label titleLabel = new Label("SHOP MANAGERMENT");
        titleLabel.setFont(new Font("黑体", 45));
        titlePane.setAlignment(Pos.CENTER);
        titlePane.add(titleLabel, 0, 0);

        //user pane部分
        String userTip = "Please enter your user name";
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
        String pswTip = "Please enter your passward";
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

        //user文本框焦点相应事件，在文本框中给出提示
        tip_focusListener(user, userTip);
        //psw文本框焦点相应事件，在文本框中给出提示
        tip_focusListener(psw, pswTip);

        //role pane部分
        GridPane RolePane = new GridPane();
        Label roleLabel = new Label("Role");
        ObservableList<String> options = FXCollections.observableArrayList("customer", "seller", "purchaser", "manager", "administrator");
        role.setItems(options);
        role.setValue("customer");
        role.setPrefWidth(500);
        role.setStyle("-fx-font: 18px \"Serif\";");
        roleLabel.setFont(font);
        RolePane.add(roleLabel, 0, 0);
        RolePane.add(role, 0, 1);
        RolePane.setVgap(5);

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

    //文本框焦点相应方法：有内容不处理，无内容且失去焦点改为提示
    public static void tip_focusListener(TextField tf, String tip) {
        tf.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && tf.getText().isEmpty()) {
                tf.setText(tip);
                tf.setStyle("-fx-text-fill:#a9a9a9;");
            } else if (tf.getText().equals(tip)) {
                tf.setText("");
                tf.setStyle("-fx-text-fill:black;");
            }
        });
    }

    public static void button_change(Button bt) {
        bt.setOnMouseEntered(e -> {
            bt.setStyle("-fx-background-color: rgb(150,210,255);");
        });
        bt.setOnMouseExited(e -> {
            bt.setStyle("-fx-background-color: rgb(100,197,255);");
        });
    }

    @Override
    public void start(Stage stage) throws Exception {
        showLogin();
    }
}