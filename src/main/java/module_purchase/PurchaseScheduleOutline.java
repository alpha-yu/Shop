package module_purchase;

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

public class PurchaseScheduleOutline extends Application {
    private List<PurchaseSchedule> orders; // 模拟订单数据
    //private User user;
    private int auth;

    public PurchaseScheduleOutline(int auth) {
        this.auth = auth;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("PurchaseSchedule Outline");
        // 初始化订单数据
        initData();
        //创建页面标题
        Label titleLabel = new Label("采购批次查询界面");
        titleLabel.setStyle("-fx-font-size: 20px;");
        // 创建控件
        TextField searchField = new TextField();
        searchField.setPrefWidth(600);
        searchField.setPromptText("请输入采购批号");
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
        TableView<PurchaseSchedule> tableView = new TableView<>();
        TableColumn<PurchaseSchedule, String> purchaseBatchIdColumn = new TableColumn<>("订单批次号");
        TableColumn<PurchaseSchedule, Double> amountColumn = new TableColumn<>("采购总金额");
        TableColumn<PurchaseSchedule, Timestamp> timeColumn = new TableColumn<>("采购时间");
        TableColumn<PurchaseSchedule, String> buyerIdColumn = new TableColumn<>("采购员");
//        TableColumn<PurchaseSchedule, Integer> stateColumn = new TableColumn<>("订单状态");
        TableColumn<PurchaseSchedule, Void> buttonColumn = new TableColumn<>("操作");
        //
        Label titleLabelTip = new Label("近期采购批次");
        titleLabelTip.setStyle("-fx-font-size: 15px;");
        Label labelTip = new Label("（点击”查看“按钮进行采购审批）");
        labelTip.setStyle("-fx-font-size: 10px;");
        // 设置列与Order对象的属性关联
        purchaseBatchIdColumn.setCellValueFactory(cellData -> cellData.getValue().purchaseBatchIdProperty());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
        buyerIdColumn.setCellValueFactory(cellData -> cellData.getValue().buyerIdProperty());

        // 添加列到表格
        tableView.getColumns().addAll(purchaseBatchIdColumn, amountColumn, timeColumn, buyerIdColumn, buttonColumn);
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
                List<PurchaseSchedule> searchResult = new ArrayList<>();
                for (PurchaseSchedule order : orders) {
                    if (order.getPurchaseBatchId().contains(searchOrderId)) {
                        searchResult.add(order);
                    }
                }
                tableView.setItems(FXCollections.observableList(searchResult));
            }
        });
        //点击查看按钮事件
        Callback<TableColumn<PurchaseSchedule, Void>, TableCell<PurchaseSchedule, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<PurchaseSchedule, Void> call(final TableColumn<PurchaseSchedule, Void> param) {
                final TableCell<PurchaseSchedule, Void> cell = new TableCell<>() {
                    private final Button button = new Button("查看");

                    {
                        button.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
                        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white;"));
                        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;"));
                        button.setOnAction(e -> {
                            PurchaseSchedule order = getTableView().getItems().get(getIndex());
                            if (order != null) {
                                // 在这里实现跳转逻辑，可以打开新窗口或者加载新的界面
                                PurchaseScheduleInterface root = createOderView(order);
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
        vBox.getChildren().addAll(titleLabel, hBox, titleLabelTip, labelTip, tableView);
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
        module_main.SQL_connect();
        try {
            Statement st;
            ResultSet rs;
            String sql = "select * from V_Purchase";
            st = shared.dbConn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                try {
                    String PBno = rs.getString(1);
                    int Psum = rs.getInt(2);
                    double SumPrice = rs.getDouble(3);
                    Timestamp Ptime = rs.getTimestamp(4);
                    String Pperson = rs.getString(5);
                    PurchaseSchedule order = new PurchaseSchedule(PBno, Psum, SumPrice, Pperson, Ptime);
                    orders.add(order);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PurchaseScheduleInterface createOderView(PurchaseSchedule selectedItem) {
        return new PurchaseScheduleInterface(selectedItem, auth);
    }
}