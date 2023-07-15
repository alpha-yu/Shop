package Manager;

// 经理
// 王昕阳
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import module_shared.User;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class Manager extends User
{
//    private Order order;
//    private Browse browse;
//    private Purchase Purchase;
//    private Off off;

    @Override
    public void ShowMenu()
    {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setHgap(50);
        pane.setVgap(50);

        Font btFont = new Font("黑体", 20);

        Button bt1 = new Button("商品浏览");
        bt1.setPrefWidth(200);
        bt1.setPrefHeight(40);
        bt1.setFont(btFont);
        bt1.setStyle("-fx-background-color: rgb(100,197,255);");
        button_change(bt1);

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
        bt2.setPrefWidth(200);
        bt2.setPrefHeight(40);
        bt2.setFont(btFont);
        bt2.setStyle("-fx-background-color: rgb(100,197,255);");
        button_change(bt2);
        bt2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                // 按下按钮时触发的函数
                //function();

                // 切换场景
                //primaryStage.setScene(scene);
            }
        });

        Button bt3 = new Button("订单信息查询");
        bt3.setPrefWidth(200);
        bt3.setPrefHeight(40);
        bt3.setFont(btFont);
        bt3.setStyle("-fx-background-color: rgb(100,197,255);");
        button_change(bt3);

        bt3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                // 按下按钮时触发的函数
                //function();

                // 切换场景
                //primaryStage.setScene(scene);
            }
        });

        Button bt4 = new Button("折扣设置");
        bt4.setPrefWidth(200);
        bt4.setPrefHeight(40);
        bt4.setFont(btFont);
        bt4.setStyle("-fx-background-color: rgb(100,197,255);");
        button_change(bt4);
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

        Stage stage = new Stage();
        stage.setTitle("ManagerMenu");

        Scene scene =new Scene(pane,800,600);
        stage.setScene(scene);
        stage.show();
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
        ShowMenu();
    }
}
