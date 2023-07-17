package module_menu;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import module_shared.shared;

public class menu_admin extends Application {
    static Stage stage;

    public static void showMenuAdmin(){
        stage=new Stage();

        stage.setTitle(shared.TEXT_ADMINISTRATOR);

        //顶部pane部分
        GridPane titlePane = new GridPane();
        Label titleLabel = new Label(shared.TEXT_ADMINISTRATOR+"界面");
        titleLabel.setFont(new Font("黑体", 45));
        titlePane.setAlignment(Pos.CENTER);
        titlePane.add(titleLabel, 0, 0);

        GridPane pane=new GridPane();
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setPadding(shared.menuPadding);
        pane.add(titlePane,0,0);

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
