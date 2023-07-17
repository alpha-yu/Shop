package module_menu;

import javafx.stage.Stage;
import module_shared.User;

public class menu_customer extends User {
    public menu_customer(String _userName, String _password, int Auth) {
        super(_userName, _password, Auth);
    }

    public void showMenuCustomer() {
        
    }
    @Override
    public void start(Stage stage) throws Exception {
        showMenuCustomer();
    }
}
