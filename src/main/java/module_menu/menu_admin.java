package module_menu;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import module_shared.shared;

public class menu_admin extends Application {
    static Stage stage;

    public static void showMenuAdmin() {
        stage = new Stage();

        stage.setTitle(shared.TEXT_ADMINISTRATOR);

        //顶部pane部分
        GridPane titlePane = new GridPane();
        Label titleLabel = new Label(shared.TEXT_ADMINISTRATOR + "界面");
        shared.init_titleLabel_Font(titleLabel);
        titlePane.setAlignment(Pos.CENTER);
        titlePane.add(titleLabel, 0, 0);

        //第一行功能
        GridPane firstline = new GridPane();
        firstline.setPrefWidth(500);

        //角色更改
        GridPane role_change = shared.init_GridPane_Style();
        Label rcLabel = new Label("用户管理");
        Button rcBt = new Button("进入");
        shared.button_change(rcBt);
        shared.init_funcLabel_Font(rcLabel);
        shared.init_Button_Font(rcBt);
        shared.init_Button_Style(rcBt, 40, 100);

        role_change.add(rcLabel, 0, 0);
        role_change.add(rcBt, 0, 1);
        firstline.add(role_change, 0, 0);

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
        showMenuAdmin();
    }
}
