package module_order;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import module_browse.Good;

import java.sql.Timestamp;
import java.util.List;

public class Order {
    private String orderId;     //订单号
    private String orderBatchId;//订单批次号
    private String customer;    //购买用户
    private String goodId;      //商品编号
    private int num;            //购买数量
    private double amount;      //总金额
    private Timestamp time;     //订单时间
    private int state;          //记录订单状态
    public Order(String orderId, String orderBatchId, String customer, String goodId, int num, double amount, Timestamp time, int state) {
        this.orderId = orderId;
        this.orderBatchId = orderBatchId;
        this.customer = customer;
        this.goodId = goodId;
        this.num = num;
        this.amount = amount;
        this.time = time;
        this.state = state;
    }
    public Order(String orderBatchId, String customer, int num, double amount, Timestamp time, int state){
        this("0", orderBatchId, customer, "0", num, amount, time,state);
    }



    public String getOrderId() {
        return orderId;
    }
    public String getCustomer() {
        return customer;
    }
    public double getAmount() {
        return amount;
    }

    public String getOrderBatchId() {
        return orderBatchId;
    }

    public void setState(int i) {
        this.state = i;
    }
    public int getState() {
        return state;
    }
    //将属性转换为可监听对象
    public ObservableValue<String> orderIdProperty() {
        return new SimpleStringProperty(orderId);
    }
    public ObservableValue<String> orderBatchIdProperty() {
        return new SimpleStringProperty(orderBatchId);
    }
    public ObservableValue<String> customerProperty() {
        return new SimpleStringProperty(customer);
    }
    public ObservableValue<String> goodIdProperty() {
        return new SimpleStringProperty(goodId);
    }
    public IntegerProperty numProperty() { return new SimpleIntegerProperty(num);}
    public DoubleProperty amountProperty() {
        return new SimpleDoubleProperty(amount);
    }
    public ObservableValue<Timestamp> timeProperty() {
        if (time == null) {
            time = new Timestamp(System.currentTimeMillis());
        }
        return new SimpleObjectProperty<>(time);
    }
    public IntegerProperty stateProperty() { return new SimpleIntegerProperty(state);
    }


}