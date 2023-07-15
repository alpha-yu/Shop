package module_shared;

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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;

// 用户基类，其他用户类型继承此基类
// 王昕阳
public abstract class User extends Application{
    private String _userName;
    private String _password;
    private int auth;
    private String UCIF;
    private String Uaddr;

    public String get_userName() {
        return _userName;
    }

    public void set_userName(String _userName) {
        this._userName = _userName;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }

    public String getUCIF() {
        return UCIF;
    }

    public void setUCIF(String UCIF) {
        this.UCIF = UCIF;
    }

    public String getUaddr() {
        return Uaddr;
    }

    public void setUaddr(String uaddr) {
        Uaddr = uaddr;
    }

    public abstract void ShowMenu();
}
