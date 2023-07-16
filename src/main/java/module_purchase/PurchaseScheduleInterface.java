package module_purchase;

import javafx.application.Application;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import module_login.module_login;
import module_main.module_main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

import static module_shared.shared.dbConn;

public class PurchaseScheduleInterface extends Application {
    private List<PurchaseSchedule> orders; // 模拟采购表数据
    private PurchaseSchedule order;
    public PurchaseScheduleInterface(PurchaseSchedule order){
        this.order = order;
    }
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Purchase Schedule Interface");
        // 初始化订单数据
        initData();
        //创建页面标题
        Label titleLabel = new Label("采购单界面");
        titleLabel.setStyle("-fx-font-size: 20px;");
        // 创建控件
        TextField searchField = new TextField();
        searchField.setPrefWidth(600);
        searchField.setPromptText("请输入订采购单号");
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
            PurchaseScheduleOutline root = createOderView();
            Stage newStage = new Stage();
            root.start(newStage);
            primaryStage.close();
            updateDB();
        });
        //使文本框于搜索按钮并排
        HBox hBox = new HBox(10, searchField, searchButton, backButton);
        //创建列表
        TableView<PurchaseSchedule> tableView = new TableView<>();
        TableColumn<PurchaseSchedule, String> purchaseIdColumn = new TableColumn<>("采购单号");
        TableColumn<PurchaseSchedule, String> goodIdColumn = new TableColumn<>("商品编号");
        TableColumn<PurchaseSchedule, String> supplierIdColumn = new TableColumn<>("供应商号");
        TableColumn<PurchaseSchedule, Integer> numColumn = new TableColumn<>("采购数量");
        TableColumn<PurchaseSchedule, Double> amountColumn = new TableColumn<>("金额");
        TableColumn<PurchaseSchedule, Timestamp> timeColumn = new TableColumn<>("创建时间");
        TableColumn<PurchaseSchedule, String> buyerIdColumn = new TableColumn<>("采购员");
        TableColumn<PurchaseSchedule, Integer> stateColumn = new TableColumn<>("订单状态");
        TableColumn<PurchaseSchedule, Void> buttonColumn = new TableColumn<>("操作");

        //
        Label titleLabelTip = new Label("批次:【"+order.getPurchaseBatchId()+"】的采购单");
        titleLabelTip.setStyle("-fx-font-size: 15px;");
        // 设置列与Order对象的属性关联
        purchaseIdColumn.setCellValueFactory(cellData -> cellData.getValue().purchaseIdProperty());
        goodIdColumn.setCellValueFactory(cellData -> cellData.getValue().goodIdProperty());
        supplierIdColumn.setCellValueFactory(cellData -> cellData.getValue().supplierIdProperty());
        numColumn.setCellValueFactory(cellData -> cellData.getValue().numProperty().asObject());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
        buyerIdColumn.setCellValueFactory(cellData -> cellData.getValue().buyerIdProperty());
        stateColumn.setCellValueFactory(cellData -> cellData.getValue().stateProperty().asObject());
        //回调函数自定义单元格内容
        stateColumn.setCellValueFactory(cellData -> {
            IntegerProperty stateProperty = cellData.getValue().stateProperty();
            return stateProperty.asObject();
        });
        stateColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer state, boolean empty) {
                super.updateItem(state, empty);
                if (empty || state == null) {
                    setText("");
                } else if (state == -1) {
                    setText("被驳回");
                } else if (state == 0) {
                    setText("待审批");
                } else if (state == 1) {
                    setText("已批准");
                } else {
                    setText("状态异常");
                }
            }
        });
        //添加审批操作
        Callback<TableColumn<PurchaseSchedule, Void>, TableCell<PurchaseSchedule, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<PurchaseSchedule, Void> call(final TableColumn<PurchaseSchedule, Void> param) {
                final TableCell<PurchaseSchedule, Void> cell = new TableCell<PurchaseSchedule, Void>() {
                    private final Button rejectButton = new Button("驳回");
                    private final Button approveButton = new Button("批准");
                    {
                        rejectButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                        rejectButton.setOnMouseEntered(event -> {
                            rejectButton.setStyle("-fx-background-color: darkred; -fx-text-fill: white;");
                        });
                        rejectButton.setOnMouseExited(event -> {
                            rejectButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                        });
                        approveButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                        approveButton.setOnMouseEntered(event -> {
                            approveButton.setStyle("-fx-background-color: darkgreen; -fx-text-fill: white;");
                        });
                        approveButton.setOnMouseExited(event -> {
                            approveButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                        });

                        rejectButton.setOnAction(event -> {
                            PurchaseSchedule order = getTableView().getItems().get(getIndex());
                            order.setState(-1);
                            rejectButton.setDisable(true);
                            approveButton.setDisable(true);
                            rejectButton.setStyle("-fx-background-color: darkred; -fx-text-fill: white;");
                            approveButton.setStyle("-fx-background-color: darkgreen; -fx-text-fill: white;");
                            tableView.refresh();
                        });

                        approveButton.setOnAction(event -> {
                            PurchaseSchedule order = getTableView().getItems().get(getIndex());
                            order.setState(1);
                            rejectButton.setDisable(true);
                            approveButton.setDisable(true);
                            rejectButton.setStyle("-fx-background-color: darkred; -fx-text-fill: white;");
                            approveButton.setStyle("-fx-background-color: darkgreen; -fx-text-fill: white;");
                            tableView.refresh();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttons = new HBox(rejectButton, approveButton);
                            buttons.setSpacing(10);
                            setGraphic(buttons);
                            PurchaseSchedule order = getTableView().getItems().get(getIndex());
                            if (order.getState() == -1 || order.getState() == 1) {
                                rejectButton.setDisable(true);
                                approveButton.setDisable(true);
                            } else {
                                rejectButton.setDisable(false);
                                approveButton.setDisable(false);
                            }
                        }
                        updateDB();
                    }
                };
                return cell;
            }
        };
        buttonColumn.setCellFactory(cellFactory);

        // 添加列到表格
        tableView.getColumns().addAll(purchaseIdColumn, goodIdColumn, supplierIdColumn, numColumn, amountColumn, timeColumn, buyerIdColumn, stateColumn, buttonColumn);
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
                    if (order.getPurchaseId().contains(searchOrderId)) {
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
        module_main.SQL_connect();
        try {
            Statement st;
            ResultSet rs;
            String sql = "select * from Purchase "+
                    "where PBno = '"+order.getPurchaseBatchId()+"';";
            st = dbConn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                try {
                    String Pno = rs.getString(1);
                    String PBno = rs.getString(2);
                    String Gno = rs.getString(3);
                    String Sno = rs.getString(4);
                    int Psum = rs.getInt(6);
                    double Pprice = rs.getDouble(7);
                    Timestamp Ptime = rs.getTimestamp(5);
                    String Pperson = rs.getString(8);
                    int Pflag = rs.getInt(9);
                    PurchaseSchedule order = new PurchaseSchedule(Pno, PBno, Gno, Sno, Psum, Pprice, Pperson, Ptime, Pflag);
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
    //更新审批表状态
    private void updateDB(){
        module_main.SQL_connect();
        String sql = "UPDATE Purchase SET Pflag = ? WHERE Pno = ?";
        try {
            PreparedStatement statement = dbConn.prepareStatement(sql);
            for (PurchaseSchedule order : orders) {
                statement.setInt(1, order.getState());
                statement.setString(2, order.getPurchaseId());
                statement.executeUpdate();  // 执行插入操作
            }
            dbConn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private PurchaseScheduleOutline createOderView() {
        return new PurchaseScheduleOutline();
    }
}