package module_menu;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import module_shared.shared;

public class menu_supplier extends Application {
    static Stage stage;

    public static void showMenuSupplier() {
        stage = new Stage();
        stage.setTitle(shared.TEXT_PURCHASER);

        //顶部pane部分
        GridPane titlePane = new GridPane();
        Label titleLabel = new Label(shared.TEXT_PURCHASER + "界面");
        shared.init_titleLabel_Font(titleLabel);
        titlePane.setAlignment(Pos.CENTER);
        titlePane.add(titleLabel, 0, 0);

        //第一行功能
        GridPane firstline = new GridPane();
        firstline.setPrefWidth(500);
        firstline.setHgap(shared.gap);
        firstline.setAlignment(Pos.CENTER);

        //采购
        GridPane supply = shared.init_GridPane_Style();
        Label sLabel = shared.init_funcLabel_Font("采购申请");
        Button sBt = shared.init_Button_Font("进入");
        shared.button_change(sBt);
        shared.init_Button_Style(sBt, 40, 100);
        supply.add(sLabel, 0, 0);
        supply.add(sBt, 0, 1);
        //采购
        GridPane supplyInfo = shared.init_GridPane_Style();
        Label siLabel = shared.init_funcLabel_Font("采购表状态");
        Button siBt = shared.init_Button_Font("进入");
        shared.button_change(siBt);
        shared.init_Button_Style(siBt, 40, 100);

        supplyInfo.add(siLabel, 0, 0);
        supplyInfo.add(siBt, 0, 1);
        firstline.add(supply, 0, 0);
        firstline.add(supplyInfo, 1, 0);

        //主体pane组合
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setPadding(shared.menuPadding);
        pane.setVgap(shared.gap);
        pane.add(titlePane, 0, 0);
        pane.add(firstline, 0, 1);

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
