package module_userInfo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class MyText {
    private String username;//用户名
    private String psw;     //商品编号
    private int AUTH;       //购买数量
    private String ucif;    //联系方式
    private String Uaddr;   //地址

    public MyText() {}
    public MyText(String username, String psw, int AUTH,String ucif,String Uaddr) {
        this.AUTH=AUTH;
        this.psw=psw;
        this.username=username;
        this.ucif=ucif;
        this.Uaddr=Uaddr;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public int getAUTH() {
        return AUTH;
    }

    public void setAUTH(int AUTH) {
        this.AUTH = AUTH;
    }
    //将属性转换为可监听对象
    public ObservableValue<String> usernameProperty() {
        return new SimpleStringProperty(username);
    }

    public ObservableValue<String> pswProperty() {
        return new SimpleStringProperty(psw);
    }

    public IntegerProperty AUTHProperty() { return new SimpleIntegerProperty(AUTH);}

    public ObservableValue<String> ucifProperty() {
        return new SimpleStringProperty(ucif);
    }

    public ObservableValue<String> UaddrProperty() {
        return new SimpleStringProperty(Uaddr);
    }

    public String getUcif() {
        return ucif;
    }

    public void setUcif(String ucif) {
        this.ucif = ucif;
    }

    public String getUaddr() {
        return Uaddr;
    }

    public void setUaddr(String Uaddr) {
        this.Uaddr = Uaddr;
    }
}