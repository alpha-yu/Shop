package module_shared;

import javafx.scene.control.Button;

import java.sql.Connection;

public class shared {
    //权限
    public static final String TEXT_CUSTOMER = "顾客";
    public static final String TEXT_SELLER = "售货员";
    public static final String TEXT_PURCHASER = "采购员";
    public static final String TEXT_MANAGER = "经理";
    public static final String TEXT_ADMINISTRATOR = "系统管理员";
    public static final int AUTH_CUSTOMER = 0;
    public static final int AUTH_SELLER = 1;
    public static final int AUTH_PURCHASER = 2;
    public static final int AUTH_MANAGER = 3;
    public static final int AUTH_ADMINISTRATOR = 4;
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
            bt.setStyle(darkblue_background + white_text);
        });
        bt.setOnMouseExited(e -> {
            bt.setStyle(blue_background + white_text);
        });
    }

    //角色名称转换为权限值
    public static int text_to_AUTH(String str) {
        if (str.equals(TEXT_CUSTOMER)) return AUTH_CUSTOMER;
        if (str.equals(TEXT_SELLER)) return AUTH_SELLER;
        if (str.equals(TEXT_PURCHASER)) return AUTH_PURCHASER;
        if (str.equals(TEXT_MANAGER)) return AUTH_MANAGER;
        if (str.equals(TEXT_ADMINISTRATOR)) return AUTH_ADMINISTRATOR;
        return -1;
    }
}