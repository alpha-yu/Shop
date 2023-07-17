package module_shared;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import module_login.module_login;

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
    //颜色
    public static final String blue_background = "-fx-background-color: #007bff;";
    public static final String darkblue_background = "-fx-background-color: #0056b3;";
    public static final String grey_background = "-fx-background-color: lightgrey;";
    public static final String white_text = "-fx-text-fill:white;";
    public static final String grey_text = "-fx-text-fill:#a9a9a9;";
    //菜单
    public static final Insets menuPadding = new Insets(20, 20, 20, 20);
    public static final double gap = 20;
    public static final double width = 500;
    public static final String round = "-fx-background-radius: 10px;-fx-border-radius: 10px;";
    public static final Font title_font = new Font("黑体", 45);
    public static final Font func_font = new Font("黑体", 25);
    public static final Font bt_font = new Font("黑体", 20);
    public static final Font user_font = new Font("宋体", 18);
    //数据库连接
    public static String dbURL = "jdbc:sqlserver://localhost;DatabaseName=Shop";
    public static Connection dbConn = null;
    public static String userStr = "sa";
    public static String passwordStr = "123456";

    //按钮风格初始化
    public static void init_Button_Style(Button bt, double h, double w) {
        bt.setPrefHeight(h);
        bt.setPrefWidth(w);
        bt.setStyle(blue_background + white_text + shared.round);
    }

    //菜单模块风格初始化
    public static GridPane init_GridPane_Style() {
        GridPane gp = new GridPane();
        gp.setPrefWidth(220);
        gp.setPrefHeight(120);
        gp.setStyle(shared.grey_background + shared.round);
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHalignment(HPos.CENTER);
        gp.getColumnConstraints().add(columnConstraints);
        return gp;
    }

    //主菜单按钮字体初始化
    public static Button init_Button_Font(String str) {
        Button bt = new Button(str);
        bt.setFont(shared.bt_font);
        bt.setAlignment(Pos.CENTER);
        return bt;
    }

    //菜单字体初始化
    public static Label init_funcLabel_Font(String str) {
        Label l = new Label(str);
        l.setFont(shared.func_font);
        l.setAlignment(Pos.CENTER);
        return l;
    }

    //按钮鼠标悬停提示
    public static void button_change(Button bt) {
        bt.setOnMouseEntered(e -> {
            bt.setStyle(darkblue_background + white_text + shared.round);
        });
        bt.setOnMouseExited(e -> {
            bt.setStyle(blue_background + white_text + shared.round);
        });
    }

    //菜单容器初始化创建
    public static GridPane init_titlePane(User user) {
        GridPane tp = new GridPane();
        //界面名称
        Label titleLabel = new Label(AUTH_to_text(user.getAuth()) + "界面");
        shared.init_titleLabel_Font(titleLabel);
        tp.setAlignment(Pos.CENTER);
        tp.add(titleLabel, 0, 0);
        //用户信息
        Label userLabel = new Label("用户名称：" + user.get_userName());
        userLabel.setFont(user_font);
        userLabel.setPrefWidth(400);
        tp.setVgap(gap);
        //登出按钮
        Button btLogout = new Button("登出");
        shared.init_Button_Style(btLogout, userLabel.getHeight(), 60);
        shared.button_change(btLogout);
        btLogout.setOnMouseClicked(e -> {
            module_login.showLogin();
            User.close();
        });

        GridPane info = new GridPane();
        info.add(userLabel, 0, 0);
        info.add(btLogout, 1, 0);
        tp.add(info, 0, 1);


        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHalignment(HPos.CENTER);
        tp.getColumnConstraints().add(columnConstraints);
        return tp;
    }

    //菜单字体初始化
    public static void init_titleLabel_Font(Label l) {
        l.setFont(shared.title_font);
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

    //角色权限值转换为名称
    public static String AUTH_to_text(int AUTH) {
        if (AUTH == AUTH_CUSTOMER) return TEXT_CUSTOMER;
        if (AUTH == AUTH_SELLER) return TEXT_SELLER;
        if (AUTH == AUTH_PURCHASER) return TEXT_PURCHASER;
        if (AUTH == AUTH_MANAGER) return TEXT_MANAGER;
        if (AUTH == AUTH_ADMINISTRATOR) return TEXT_ADMINISTRATOR;
        return "";
    }
}