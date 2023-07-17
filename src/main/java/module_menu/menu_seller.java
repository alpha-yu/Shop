package module_menu;

import javafx.stage.Stage;
import module_shared.User;

public class menu_seller extends User {
    public menu_seller(String _userName, String _password, int Auth) {
        super(_userName, _password, Auth);
    }

    public void showMenuSeller() {

    }

    @Override
    public void start(Stage stage) throws Exception {
        showMenuSeller();
    }
}
