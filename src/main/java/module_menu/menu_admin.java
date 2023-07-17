package module_menu;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import module_shared.User;
import module_shared.shared;

public class menu_admin extends User {
    shared s=new shared();

    public menu_admin(String _userName, String _password, int Auth) {
        super(_userName, _password, Auth);
    }

    public void showMenuAdmin() {
        stage = new Stage();
        stage.setTitle(shared.TEXT_ADMINISTRATOR);

        //title pane部分
        GridPane titlePane = super.get_titlePane();

        //功能Pane：可用于上下滑动查看功能
        GridPane funcPane=new GridPane();
        //第一行功能
        GridPane firstline = new GridPane();
        firstline.setPrefWidth(shared.width);
        firstline.setHgap(shared.gap);
        firstline.setAlignment(Pos.CENTER);

        //角色更改
        GridPane role_change = shared.init_GridPane_Style();
        Label rcLabel = shared.init_funcLabel_Font("用户管理");
        Button rcBt = shared.init_Button_Font("进入");
        shared.button_change(rcBt);
        shared.init_Button_Style(rcBt, 40, 100);

        role_change.add(rcLabel, 0, 0);
        role_change.add(rcBt, 0, 1);
        firstline.add(role_change, 0, 0);

        funcPane.add(firstline,0,0);

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
        showMenuAdmin();
    }
}
