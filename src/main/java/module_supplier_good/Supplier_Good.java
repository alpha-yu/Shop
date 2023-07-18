package module_supplier_good;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class Supplier_Good {
    String Sno;
    String Gno;
    double Inprice;
    double Infee;

    public Supplier_Good(String Sno, String Gno, double Inprice, double Infee) {
        this.Sno = Sno;
        this.Gno = Gno;
        this.Inprice = Inprice;
        this.Infee = Infee;
    }

    public String getSno() {
        return Sno;
    }

    public void setSno(String sno) {
        Sno = sno;
    }

    public String getGno() {
        return Gno;
    }

    public void setGno(String gno) {
        Gno = gno;
    }

    public double getInprice() {
        return Inprice;
    }

    public void setInprice(double inprice) {
        Inprice = inprice;
    }

    public double getInfee() {
        return Infee;
    }

    public void setInfee(double infee) {
        Infee = infee;
    }

    public ObservableValue<String> SnoProperty() {
        return new SimpleStringProperty(Sno);
    }

    public ObservableValue<String> GnoProperty() {
        return new SimpleStringProperty(Gno);
    }

    public DoubleProperty inPriceProperty() {
        return new SimpleDoubleProperty(Inprice);
    }

    public DoubleProperty inFeeProperty() {
        return new SimpleDoubleProperty(Infee);
    }

}