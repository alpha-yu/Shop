package module_menu;

import javafx.stage.Stage;
import module_browse.module_browse;
import module_shared.User;

public class menu_seller extends User {
    public menu_seller(String _userName, String _password, int Auth) {
        super(_userName, _password, Auth);
    }

    public void showMenuSeller() {

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
        showMenuSeller();
    }
}
