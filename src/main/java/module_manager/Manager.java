package module_manager;

// 经理
// 王昕阳
import javafx.geometry.Insets;
import module_order.OrderInterfaceOutline;
import module_purchase.PurchaseScheduleOutline;
import module_shared.shared;
import module_shared.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Manager extends User
{
//    @Override
    public void  ShowMenu()
    {
        shared SharedModule= new shared();

        Stage stage = new Stage();
        stage.setTitle("ManagerMenu");

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setHgap(50);
        pane.setVgap(50);

        Font btFont = new Font("黑体", 20);

        Button bt1 = new Button("商品浏览");
        SharedModule.init_Button_Style(bt1, 40, 200);
        SharedModule.button_change(bt1);
        SharedModule.init_GridPane_Style();

        bt1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                // 按下按钮时触发的函数
                //function();

                // 切换场景
                //primaryStage.setScene(scene);
            }
        });

        Button bt2 = new Button("采购表审批");
        SharedModule.init_Button_Style(bt2, 40, 200);
        SharedModule.button_change(bt2);

        bt2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                // 按下按钮时触发的函数
                PurchaseScheduleOutline purchaseScheduleOutline = new PurchaseScheduleOutline(1);
                try {
                    purchaseScheduleOutline.start(new Stage());
                    stage.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        Button bt3 = new Button("订单信息查询");
        SharedModule.init_Button_Style(bt3, 40, 200);
        SharedModule.button_change(bt3);

        bt3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                OrderInterfaceOutline orderInterfaceOutline = new OrderInterfaceOutline(3);
                try {
                    orderInterfaceOutline.start(new Stage());
                    stage.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        Button bt4 = new Button("折扣设置");
        SharedModule.init_Button_Style(bt4, 40, 200);
        SharedModule.button_change(bt4);
        bt4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                // 按下按钮时触发的函数
                //function();

                // 切换场景
                //primaryStage.setScene(scene);
            }
        });

        pane.add(bt1 ,0, 0);
        pane.add(bt2 ,1, 0);
        pane.add(bt3 ,0, 1);
        pane.add(bt4 ,1, 1);

        Scene scene =new Scene(pane,800,600);
        stage.setScene(scene);
        stage.show();
    }

    public static void button_change(Button bt) {
        bt.setOnMouseEntered(e -> {
            bt.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white;");
        });
        bt.setOnMouseExited(e -> {
            bt.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        });
    }

    @Override
    public void start(Stage stage) throws Exception {
        ShowMenu();
    }
}