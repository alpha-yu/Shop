package module_purchase;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

import java.sql.Timestamp;

public class PurchaseSchedule {
    private String purchaseId;  //采购编号
    private String purchaseBatchId; //采购批次号
    private String goodId;      //商品编号
    private String supplierId;  //供应商编号
    private int num;            //采购数量
    private double amount;     //采购金额
    private Timestamp time;     //记录订单时间
    private String buyerId;       //采购员
    private int state;          //采购表状态
    public PurchaseSchedule(String purchaseId, String purchaseBatchId, String goodId, String supplierId, int num, double amount, String buyerId, Timestamp time, int state) {
        this.purchaseId = purchaseId;
        this.purchaseBatchId = purchaseBatchId;
        this.goodId = goodId;
        this.supplierId = supplierId;
        this.num = num;
        this.amount = amount;
        this.buyerId = buyerId;
        this.time = time;
        this.state = state;
    }
    public PurchaseSchedule(String purchaseBatchId, int num, double amount, String buyerId, Timestamp time) {
        this("0", purchaseBatchId, "0", "0", num, amount, buyerId, time, 0);
    }
    public String getPurchaseId() {
        return purchaseId;
    }
    public String getGoodId() {
        return goodId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public Timestamp getTime() {
        return time;
    }

    public int getNum() {
        return num;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public double getAmount() {
        return amount;
    }
    public void setState(int i) {
        this.state = i;
    }
    public int getState() {
        return state;
    }

    public String getPurchaseBatchId() {
        return purchaseBatchId;
    }

    //将属性转换为可监听对象
    public ObservableValue<String> purchaseIdProperty() {
        return new SimpleStringProperty(purchaseId);
    }
    public ObservableValue<String> purchaseBatchIdProperty() {
        return new SimpleStringProperty(purchaseBatchId);
    }
    public ObservableValue<String> goodIdProperty() {
        return new SimpleStringProperty(goodId);
    }
    public ObservableValue<String> supplierIdProperty() {
        return new SimpleStringProperty(supplierId);
    }
    public IntegerProperty numProperty() { return new SimpleIntegerProperty(num); }
    public DoubleProperty amountProperty() {
        return new SimpleDoubleProperty(amount);
    }
    public ObservableValue<String> buyerIdProperty() {
        return new SimpleStringProperty(buyerId);
    }
    public ObservableValue<Timestamp> timeProperty() {
        if (time == null) {
            time = new Timestamp(System.currentTimeMillis());
        }
        return new SimpleObjectProperty<>(time);
    }
    public IntegerProperty stateProperty() { return new SimpleIntegerProperty(state); }
}