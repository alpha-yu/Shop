package module_menu;

// 经理
// 王昕阳

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import module_browse.module_browse;
import module_order.OrderInterfaceOutline;
import module_purchase.PurchaseScheduleOutline;
import module_shared.User;
import module_shared.shared;

public class menu_manager extends User {
    shared s = new shared();

    public menu_manager(String _userName, String _password, int Auth) {
        super(_userName, _password, Auth);
    }

    public void ShowMenuManager() {
        stage = new Stage();
        stage.setTitle(shared.TEXT_MANAGER);

        GridPane titlePane = super.get_titlePane();

        //功能Pane：可用于上下滑动查看功能
        GridPane subPane=new GridPane();
        subPane.setAlignment(Pos.CENTER);
        ScrollPane funcPane= shared.Grid_to_Scroll(subPane);

        //第一行功能
        GridPane firstline = new GridPane();
        firstline.setPrefWidth(shared.width);
        firstline.setHgap(shared.gap);
        firstline.setAlignment(Pos.CENTER);

        //商品浏览功能
        GridPane gp1 = shared.init_GridPane_Style();
        Label Label1 = shared.init_funcLabel_Font("商品浏览");
        Button bt1 = shared.init_Button_Font("进入");
        shared.button_change(bt1);
        shared.init_Button_Style(bt1, 40, 100);
        gp1.add(Label1, 0, 0);
        gp1.add(bt1, 0, 1);

        bt1.setOnAction(e -> {
            stage.setTitle(shared.TEXT_CUSTOMER);
            module_browse b = new module_browse();
            try {
                b.start(new Stage());
                stage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        firstline.add(gp1, 0, 0);

        //采购表审批功能
        GridPane gp2 = shared.init_GridPane_Style();
        Label Label2 = shared.init_funcLabel_Font("采购表审批");
        Button bt2 = shared.init_Button_Font("进入");
        shared.button_change(bt2);
        shared.init_Button_Style(bt2, 40, 100);
        gp2.add(Label2, 0, 0);
        gp2.add(bt2, 0, 1);

        bt2.setOnAction(e -> {
            PurchaseScheduleOutline purchaseScheduleOutline = new PurchaseScheduleOutline(shared.AUTH_MANAGER);
            try {
                purchaseScheduleOutline.start(new Stage());
                stage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        firstline.add(gp2,1,0);

        //第二行功能
        GridPane secondline = new GridPane();
        secondline.setPrefWidth(shared.width);
        secondline.setHgap(shared.gap);
        secondline.setAlignment(Pos.CENTER);

        GridPane gp3 = shared.init_GridPane_Style();
        Label Label3 = shared.init_funcLabel_Font("订单查看");
        Button bt3 = shared.init_Button_Font("进入");
        shared.button_change(bt3);
        shared.init_Button_Style(bt3, 40, 100);
        gp3.add(Label3, 0, 0);
        gp3.add(bt3, 0, 1);

        bt3.setOnAction(e -> {
            OrderInterfaceOutline orderInterfaceOutline = new OrderInterfaceOutline(shared.AUTH_MANAGER);
            try {
                orderInterfaceOutline.start(new Stage());
                stage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        secondline.add(gp3, 0, 0);
        subPane.add(firstline,0,0);
        subPane.add(secondline,0,1);
        subPane.setVgap(shared.gap);
        funcPane.setPadding(shared.menuPadding);

        //主体pane组合
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setPadding(shared.menuPadding);
        pane.setHgap(shared.gap);
        pane.add(titlePane, 0, 0);
        pane.add(funcPane, 0, 1);

        Scene scene = new Scene(pane, 800, 600);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws Exception {
        ShowMenuManager();
    }
}