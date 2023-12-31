package module_shared;

import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

// 用户基类，其他用户类型继承此基类
// 王昕阳
public abstract class User extends Application {
    protected static Stage stage;
    private String _userName;
    private String _password;
    private int auth;
    private String UCIF;
    private String Uaddr;

    public User(String _userName, String _password, int Auth) {
        set_userName(_userName);
        set_password(_password);
        setAuth(Auth);
    }

    public static void close() {
        stage.close();
    }

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

    public GridPane get_titlePane() {
        return shared.init_titlePane(this);
    }
}
