package module_menu;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import module_order.OrderInterfaceOutline;
import module_purchase.PurchaseScheduleOutline;
import module_shared.User;
import module_shared.shared;
import module_buyer.PurchaseMenu;

public class menu_supplier extends User {

    public menu_supplier(String _userName, String _password, int Auth) {
        super(_userName, _password, Auth);
    }

    public void showMenuSupplier() {
        stage = new Stage();
        stage.setTitle(shared.TEXT_PURCHASER);

        //title pane部分
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

        //采购申请
        GridPane supply = shared.init_GridPane_Style();
        Label sLabel = shared.init_funcLabel_Font("采购申请");
        Button sBt = shared.init_Button_Font("进入");
        shared.button_change(sBt);
        shared.init_Button_Style(sBt, 40, 100);

        sBt.setOnAction(e -> {
            PurchaseMenu purchaseMenu = new PurchaseMenu();
            try {
                purchaseMenu.start(new Stage());
                stage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        supply.add(sLabel, 0, 0);
        supply.add(sBt, 0, 1);
        //采购表状态
        GridPane supplyInfo = shared.init_GridPane_Style();
        Label siLabel = shared.init_funcLabel_Font("采购表状态");
        Button siBt = shared.init_Button_Font("进入");
        shared.button_change(siBt);
        shared.init_Button_Style(siBt, 40, 100);

        sBt.setOnAction(e -> {
            PurchaseScheduleOutline purchaseScheduleOutline = new PurchaseScheduleOutline(shared.AUTH_PURCHASER);
            try {
                purchaseScheduleOutline.start(new Stage());
                stage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        supplyInfo.add(siLabel, 0, 0);
        supplyInfo.add(siBt, 0, 1);

        firstline.add(supply, 0, 0);
        firstline.add(supplyInfo, 1, 0);
        subPane.add(firstline,0,0);

        //主体pane组合
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setPadding(shared.menuPadding);
        pane.setVgap(shared.gap);
        pane.add(titlePane, 0, 0);
        pane.add(funcPane, 0, 1);

        Scene scene = new Scene(pane, 800, 600);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws Exception {
        showMenuSupplier();
    }
}
