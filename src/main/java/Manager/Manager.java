package Manager;

import module_shared.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Manager extends User
{
    @Override
    public  void ShowMenu()
    {

    }

    @Override
    public void start(Stage stage) throws Exception {
        ShowMenu();
    }
}
