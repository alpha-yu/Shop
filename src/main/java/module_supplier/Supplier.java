package module_supplier;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class Supplier {
    private String Sno;
    private String Sname;
    private String SCIF;

    public Supplier(String Sno, String Sname, String SCIF) {
        this.Sno = Sno;
        this.Sname = Sname;
        this.SCIF = SCIF;
    }

    public String getSCIF() {
        return SCIF;
    }

    public void setSCIF(String SCIF) {
        this.SCIF = SCIF;
    }

    public String getSname() {
        return Sname;
    }

    public void setSname(String sname) {
        Sname = sname;
    }

    public String getSno() {
        return Sno;
    }

    public void setSno(String sno) {
        Sno = sno;
    }

    public ObservableValue<String> SnameProperty() {
        return new SimpleStringProperty(Sname);
    }

    public ObservableValue<String> SnoProperty() {
        return new SimpleStringProperty(Sno);
    }

    public ObservableValue<String> SCIFProperty() {
        return new SimpleStringProperty(SCIF);
    }
}
