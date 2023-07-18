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
import javafx.util.Callback;
import module_main.module_main;
import module_shared.shared;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderInterfaceOutline extends Application {
    private List<Order> orders; // 模拟订单数据
    private int auth;
    private String id;

    public OrderInterfaceOutline(int auth) {
        this.auth = auth;
    }

    public OrderInterfaceOutline(int auth, String id) {
        this.auth = auth;
        this.id = id;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Order Interface Outline");
        // 初始化订单数据
        initData();
        //创建页面标题
        Label titleLabel = new Label("订单批次查询界面");
        titleLabel.setStyle("-fx-font-size: 20px;");
        // 创建控件
        TextField searchField = new TextField();
        searchField.setPrefWidth(600);
        searchField.setPromptText("请输入订单批号");
        Button searchButton = new Button("搜索");
        //设置‘搜索’按钮颜色
        searchButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        //当鼠标停留在按钮上时，颜色变深
        searchButton.setOnMouseEntered(event -> {
            searchButton.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white;");
        });
        searchButton.setOnMouseExited(event -> {
            searchButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        });
        //使文本框于搜索按钮并排
        HBox hBox = new HBox(10, searchField, searchButton);
        //创建列表
        TableView<Order> tableView = new TableView<>();
        TableColumn<Order, String> orderBatchIdColumn = new TableColumn<>("订单批次号");
        TableColumn<Order, String> customerColumn = new TableColumn<>("购买顾客");
        TableColumn<Order, Integer> numColumn = new TableColumn<>("商品总数");
        TableColumn<Order, Double> amountColumn = new TableColumn<>("总金额");
        TableColumn<Order, Timestamp> timeColumn = new TableColumn<>("创建时间");
        TableColumn<Order, Integer> stateColumn = new TableColumn<>("订单状态");
        TableColumn<Order, Void> buttonColumn = new TableColumn<>("操作");
        //
        Label titleLabelTip = new Label("近期订单批次");
        titleLabelTip.setStyle("-fx-font-size: 15px;");
        // 设置列与Order对象的属性关联
        orderBatchIdColumn.setCellValueFactory(cellData -> cellData.getValue().orderBatchIdProperty());
        customerColumn.setCellValueFactory(cellData -> cellData.getValue().customerProperty());
        numColumn.setCellValueFactory(cellData -> cellData.getValue().numProperty().asObject());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
        stateColumn.setCellValueFactory(cellData -> cellData.getValue().stateProperty().asObject());
        //回调函数自定义单元格内容
//        stateColumn.setCellValueFactory(cellData -> {
//            IntegerProperty stateProperty = cellData.getValue().stateProperty();
//            return stateProperty.asObject();
//        });
        stateColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer state, boolean empty) {
                super.updateItem(state, empty);
                if (empty || state == null) {
                    setText("");
                } else if (state == 0) {
                    setText("未处理");
                } else if (state == 1) {
                    setText("已处理");
                } else {
                    setText("状态异常");
                }
            }
        });

        // 添加列到表格
        tableView.getColumns().addAll(orderBatchIdColumn, customerColumn, numColumn, amountColumn, timeColumn, stateColumn, buttonColumn);
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
                    if (order.getOrderBatchId().contains(searchOrderId)) {
                        searchResult.add(order);
                    }
                }
                tableView.setItems(FXCollections.observableList(searchResult));
            }
        });
        //点击查看按钮事件
        Callback<TableColumn<Order, Void>, TableCell<Order, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Order, Void> call(final TableColumn<Order, Void> param) {
                final TableCell<Order, Void> cell = new TableCell<>() {
                    private final Button button = new Button("查看");

                    {
                        button.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
                        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white;"));
                        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;"));
                        button.setOnAction(e -> {
                            Order order = getTableView().getItems().get(getIndex());
                            if (order != null) {
                                // 在这里实现跳转逻辑，可以打开新窗口或者加载新的界面
                                OrderInterface root = createOderView(order);
                                Stage newStage = new Stage();
                                root.start(newStage);
                                primaryStage.close();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                        }
                    }
                };
                return cell;
            }
        };
        buttonColumn.setCellFactory(cellFactory);

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
            String sql;
            if (auth == 0 || auth == 1) {
                sql = "select * from V_Order " +
                        "where Obuyer = '" + id + "';";
            } else if (auth == 3) {
                sql = "select * from V_Order";
            } else {
                sql = "select * from V_Order " +
                        "where Obuyer = '!@#$%^&*(';";
            }
            st = shared.dbConn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                try {
                    String OBno = rs.getString(1);
                    String Obuyer = rs.getString(2);
                    int Osum = rs.getInt(3);
                    double SumPrice = rs.getDouble(4);
                    Timestamp Otime = rs.getTimestamp(5);
                    int Oinfo = rs.getInt(6);
                    Order order = new Order(OBno, Obuyer, Osum, SumPrice, Otime, Oinfo);
                    orders.add(order);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private OrderInterface createOderView(Order selectedItem) {
        return new OrderInterface(selectedItem);
    }
}