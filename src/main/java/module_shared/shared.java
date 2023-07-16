package module_shared;

import javafx.scene.control.Button;

import java.sql.Connection;

public class shared {
    //数据库连接
    public static String dbURL = "jdbc:sqlserver://localhost;DatabaseName=Shop";
    public static Connection dbConn = null;
    public static String userStr = "sa";
    public static String passwordStr = "123456";

    //颜色
    public static String blue_background = "-fx-background-color: #007bff;";
    public static String darkblue_background = "-fx-background-color: #0056b3;";
    public static String white_text = "-fx-text-fill:white;";
    public static String grey_text = "-fx-text-fill:#a9a9a9;";

    //按钮鼠标悬停提示
    public static void button_change(Button bt) {
        bt.setOnMouseEntered(e -> {
            bt.setStyle(darkblue_background+white_text);
        });
        bt.setOnMouseExited(e -> {
            bt.setStyle(blue_background+white_text);
        });
    }
}