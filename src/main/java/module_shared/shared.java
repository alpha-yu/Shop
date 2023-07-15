package module_shared;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;

public class shared {
    public static String dbURL = "jdbc:sqlserver://localhost;DatabaseName=Shop";
    public static Connection dbConn = null;
    public static String userStr = "sa";
    public static String passwordStr = "123456";

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

    //按钮鼠标悬停高亮提示
    public static void button_change(Button bt) {
        bt.setOnMouseEntered(e -> {
            bt.setStyle("-fx-background-color: rgb(150,210,255);");
        });
        bt.setOnMouseExited(e -> {
            bt.setStyle("-fx-background-color: rgb(100,197,255);");
        });
    }
}