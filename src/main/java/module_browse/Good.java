package module_browse;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class Good {
    private String Gno;
    private String Gname;
    private double Gprice;
    private String Ginfo;
    private String MATL;
    private String CATEG;
    private String EXPdate;
    private int num=0;

    public Good() {
    }

    public Good(String gno, String gname, double gprice, String ginfo, String MATL, String CATEG, String EXPdate) {
        Gno = gno;
        Gname = gname;
        Gprice = gprice;
        Ginfo = ginfo;
        this.MATL = MATL;
        this.CATEG = CATEG;
        this.EXPdate = EXPdate;
    }
    public Good(String gno, String gname, String ginfo, int num){
        this(gno, gname, 0, ginfo, "null", "null", "null");
        this.num = num;
    }
    public int getNum(){return num;}
    public void setNum(int n){num=n;}

    public String getGno() {
        return Gno;
    }

    public void setGno(String gno) {
        Gno = gno;
    }

    public String getGname() {
        return Gname;
    }

    public void setGname(String gname) {
        Gname = gname;
    }

    public double getGprice() {
        return Gprice;
    }

    public void setGprice(double gprice) {
        Gprice = gprice;
    }

    public String getGinfo() {
        return Ginfo;
    }

    public void setGinfo(String ginfo) {
        Ginfo = ginfo;
    }

    public String getMATL() {
        return MATL;
    }

    public void setMATL(String MATL) {
        this.MATL = MATL;
    }

    public String getCATEG() {
        return CATEG;
    }

    public void setCATEG(String CATEG) {
        this.CATEG = CATEG;
    }

    public String getEXPdate() {
        return EXPdate;
    }

    public void setEXPdate(String EXPdate) {
        this.EXPdate = EXPdate;
    }

    public ObservableValue<String> goodIdProperty() {
        return new SimpleStringProperty(Gno);
    }

    public ObservableValue<String> goodNameProperty() { return new SimpleStringProperty(Gname); }

    public IntegerProperty numProperty() { return new SimpleIntegerProperty(num);}
}

