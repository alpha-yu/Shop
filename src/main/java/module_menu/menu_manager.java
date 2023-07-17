package module_menu;

// 经理
// 王昕阳

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import module_order.OrderInterfaceOutline;
import module_purchase.PurchaseScheduleOutline;
import module_shared.User;
import module_shared.shared;

public class menu_manager extends User {
    shared s = new shared();

    public menu_manager(String _userName, String _password, int Auth) {
        super(_userName, _password, Auth);
    }

    //    @Override
    public void ShowMenuManager() {
        int Auth = 3;
        stage = new Stage();
        stage.setTitle(shared.TEXT_PURCHASER);

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setHgap(50);
        pane.setVgap(50);

        Font btFont = new Font("黑体", 20);

        Button bt1 = new Button("商品浏览");
        s.init_Button_Style(bt1, 40, 200);
        s.button_change(bt1);
        s.init_GridPane_Style();

        bt1.setOnAction(e -> {
            // 按下按钮时触发的函数
            //function();

            // 切换场景
            //primaryStage.setScene(scene);
        });

        Button bt2 = new Button("采购表审批");
        s.init_Button_Style(bt2, 40, 200);
        s.button_change(bt2);

        bt2.setOnAction(e -> {
            PurchaseScheduleOutline purchaseScheduleOutline = new PurchaseScheduleOutline(1);
            try {
                purchaseScheduleOutline.start(new Stage());
                stage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button bt3 = new Button("订单信息查询");
        s.init_Button_Style(bt3, 40, 200);
        s.button_change(bt3);

        bt3.setOnAction(e -> {
            OrderInterfaceOutline orderInterfaceOutline = new OrderInterfaceOutline(1);
            try {
                orderInterfaceOutline.start(new Stage());
                stage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button bt4 = new Button("折扣设置");
        s.init_Button_Style(bt4, 40, 200);
        s.button_change(bt4);
        bt4.setOnAction(e -> {
            // 按下按钮时触发的函数
            //function();

            // 切换场景
            //primaryStage.setScene(scene);
        });

        pane.add(bt1, 0, 0);
        pane.add(bt2, 1, 0);
        pane.add(bt3, 0, 1);
        pane.add(bt4, 1, 1);

        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws Exception {
        ShowMenuManager();
    }
}