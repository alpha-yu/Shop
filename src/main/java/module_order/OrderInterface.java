package module_order;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import module_login.module_login;
import module_main.module_main;
import module_shared.shared;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

import static module_shared.shared.dbConn;

public class OrderInterface extends Application {
    private List<Order> orders; // 模拟订单数据
    private Order order;
    public OrderInterface(Order order){
        this.order = order;
    }
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Order Interface");
        // 初始化订单数据
        initData();
        //创建页面标题
        String s;
        if(order.getState() == 0){
            s = "订单状态：未处理";
        }else if(order.getState() == 1){
            s = "订单状态：已处理";
        }else{
            s = "订单状态：异常";
        }
        Label titleLabel = new Label("订单批号："+order.getOrderBatchId()+"\n订单顾客："+order.getCustomer()+"\n"+s);
        titleLabel.setStyle("-fx-font-size: 20px;");
        // 创建控件
        TextField searchField = new TextField();
        searchField.setPrefWidth(600);
        searchField.setPromptText("请输入订单号");
        Button searchButton = new Button("搜索");
        Button backButton = new Button("返回");
        //设置‘搜索’按钮颜色
        searchButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        backButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        //当鼠标停留在按钮上时，颜色变深
        searchButton.setOnMouseEntered(event -> {
            searchButton.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white;");
        });
        searchButton.setOnMouseExited(event -> {
            searchButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        });
        backButton.setOnMouseEntered(event -> {
            backButton.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white;");
        });
        backButton.setOnMouseExited(event -> {
            backButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        });
        //返回按钮跳转到上一界面
        backButton.setOnAction(e -> {
            // 在这里实现跳转逻辑，可以打开新窗口或者加载新的界面
            OrderInterfaceOutline root = createOderView();
            Stage newStage = new Stage();
            root.start(newStage);
            primaryStage.close();
        });
        //使文本框于搜索按钮并排
        HBox hBox = new HBox(10, searchField, searchButton, backButton);
        //创建列表
        TableView<Order> tableView = new TableView<>();
        TableColumn<Order, String> orderIdColumn = new TableColumn<>("订单号");
        TableColumn<Order, String> goodIdColumn = new TableColumn<>("商品编号");
        TableColumn<Order, Integer> numColumn = new TableColumn<>("商品数量");
        TableColumn<Order, Double> amountColumn = new TableColumn<>("商品金额");
        TableColumn<Order, Timestamp> timeColumn = new TableColumn<>("创建时间");
//        TableColumn<Order, Integer> stateColumn = new TableColumn<>("订单状态");

        //
        Label titleLabelTip = new Label("该批次商品");
        titleLabelTip.setStyle("-fx-font-size: 15px;");
        // 设置列与Order对象的属性关联
        orderIdColumn.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty());
        goodIdColumn.setCellValueFactory(cellData -> cellData.getValue().goodIdProperty());
        numColumn.setCellValueFactory(cellData -> cellData.getValue().numProperty().asObject());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());

        // 添加列到表格
        tableView.getColumns().addAll(orderIdColumn, goodIdColumn, numColumn, amountColumn, timeColumn);
        // 设置表格数据
        tableView.setItems(FXCollections.observableList(orders));
        //使订单按时间由近到远排序
        timeColumn.setSortType(TableColumn.SortType.DESCENDING);
        tableView.getSortOrder().add(timeColumn);
        tableView.setSortPolicy(null);//禁止表头排序
        // 设置搜索按钮的点击事件
        searchButton.setOnAction(event -> {
            String searchOrderId = searchField.getText();
            if (searchOrderId.isEmpty()) {
                tableView.setItems(FXCollections.observableList(orders));
            } else {
                List<Order> searchResult = new ArrayList<>();
                for (Order order : orders) {
                    if (order.getOrderId().contains(searchOrderId)) {
                        searchResult.add(order);
                    }
                }
                tableView.setItems(FXCollections.observableList(searchResult));
            }
        });

        // 创建布局
        BorderPane borderPane = new BorderPane();
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(titleLabel, hBox, titleLabelTip, tableView);
        borderPane.setCenter(vBox);
        // 创建场景
        Scene scene = new Scene(borderPane, 800, 600);
        // 设置场景并显示
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    // 初始化订单数据
    private void initData() {
        orders = new ArrayList<>();
        //连接数据库，从数据库中读
        module_main.SQL_connect();
        try {
            Statement st;
            ResultSet rs;
            String sql = "select * from Orders " +
                    "where Obno = '"+order.getOrderBatchId()+"';";
            st = shared.dbConn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                try {
                    String Ono = rs.getString(1);
                    String OBno = rs.getString(2);
                    String Obuyer = rs.getString(3);
                    String Gno = rs.getString(4);
                    int Osum = rs.getInt(5);
                    Timestamp Otime = rs.getTimestamp(6);
                    double SumPrice = rs.getDouble(7);
                    int Oinfo = rs.getInt(8);
                    Order order = new Order(Ono, OBno, Obuyer, Gno, Osum, SumPrice, Otime, Oinfo);
                    orders.add(order);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            dbConn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private OrderInterfaceOutline createOderView() {
        return new OrderInterfaceOutline();
    }
}