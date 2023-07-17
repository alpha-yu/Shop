package module_menu;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import module_shared.User;
import module_shared.shared;
import module_browse.module_browse;

public class menu_customer extends User {
    public menu_customer(String _userName, String _password, int Auth) {
        super(_userName, _password, Auth);
    }

    shared SharedModule = new shared();
    public void showMenuCustomer() {
        Stage stage = new Stage();
        stage.setTitle(shared.TEXT_CUSTOMER);

        module_browse b = new module_browse();
        try {
            b.start(new Stage());
            stage.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void start(Stage stage) throws Exception {
        showMenuCustomer();
    }
}
