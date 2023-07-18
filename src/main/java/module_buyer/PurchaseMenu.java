package module_buyer;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import module_browse.Good;
import module_main.module_main;
import module_shared.shared;
import module_supplier_good.module_Supplier_Good;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static module_shared.shared.*;

public class PurchaseMenu extends Application {
    private List<Good> goods; // 订单数据
    private String username;

    public PurchaseMenu(String username) {
        this.username = username;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
//        module_main.SQL_connect();
        primaryStage.setTitle("采购");
        // 初始化订单数据
        initData();
        //创建页面标题
        Label titleLabel = new Label("商品采购界面");
        titleLabel.setStyle("-fx-font-size: 20px;");
        // 创建控件
        TextField searchField = new TextField();
        searchField.setPrefWidth(600);
        searchField.setPromptText("请输入商品名称/编号");
        Button searchButton = new Button("搜索");
        //设置‘搜索’按钮颜色
        init_Button_Style(searchButton, searchButton.getPrefHeight(), searchButton.getPrefWidth());
        //当鼠标停留在按钮上时，颜色变深
        button_change(searchButton);
        //使文本框于搜索按钮并排
        HBox hBox = new HBox(10, searchField, searchButton);
        //创建列表
        TableView<Good> tableView = new TableView<>();

        TableColumn<Good, String> goodIdColumn = new TableColumn<>("商品编号");
        TableColumn<Good, String> goodNameColumn = new TableColumn<>("商品名称");
        TableColumn<Good, Integer> numColumn = new TableColumn<>("库存");
        TableColumn<Good, Void> buttonColumn = new TableColumn<>("操作");
        //
        Label titleLabelTip = new Label("仓库短缺商品");
        titleLabelTip.setStyle("-fx-font-size: 15px;");
        Label tip = new Label("(点击“查看”进行供应商选择和采购操作)");
        tip.setStyle("-fx-font-size: 10px;");
        // 设置列与Order对象的属性关联
        goodIdColumn.setCellValueFactory(cellData -> cellData.getValue().goodIdProperty());
        goodNameColumn.setCellValueFactory(cellData -> cellData.getValue().goodNameProperty());
        numColumn.setCellValueFactory(cellData -> cellData.getValue().numProperty().asObject());
        //回调函数自定义单元格宽度
        goodIdColumn.setPrefWidth(190);
        goodNameColumn.setPrefWidth(190);
        numColumn.setPrefWidth(190);
        buttonColumn.setPrefWidth(210);

        // 添加列到表格
        tableView.getColumns().addAll(goodIdColumn, goodNameColumn, numColumn, buttonColumn);
        // 设置表格数据
        tableView.setItems(FXCollections.observableList(goods));
        //使商品按数量由少到多排序
        numColumn.setSortType(TableColumn.SortType.ASCENDING);
        tableView.getSortOrder().add(numColumn);
        tableView.setSortPolicy(null);//禁止表头排序
        // 设置搜索按钮的点击事件
        searchButton.setOnAction(event -> {
            String searchOrderId = searchField.getText();
            if (searchOrderId.isEmpty()) {
                tableView.setItems(FXCollections.observableList(goods));
            } else {
                List<Good> searchResult = new ArrayList<>();
                for (Good good : goods) {
                    if (good.getGno().contains(searchOrderId) || good.getGname().contains(searchOrderId)) {
                        searchResult.add(good);
                    }
                }
                tableView.setItems(FXCollections.observableList(searchResult));
            }
        });
        //点击查看按钮事件
        Callback<TableColumn<Good, Void>, TableCell<Good, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Good, Void> call(final TableColumn<Good, Void> param) {
                final TableCell<Good, Void> cell = new TableCell<>() {
                    private final Button button = new Button("查看");

                    {
                        init_Button_Style(button, button.getPrefHeight(), button.getPrefWidth());
                        button_change(button);
                        button.setOnAction(e -> {
                            Good order = getTableView().getItems().get(getIndex());
                            if (order != null) {
                                // 在这里实现跳转逻辑，可以打开新窗口或者加载新的界面
                                module_Supplier_Good root = createOderView(order, username);
                                Stage newStage = new Stage();
                                root.start(newStage);
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox box = new HBox(button);
                            box.setAlignment(Pos.CENTER);
                            setGraphic(box);
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
        vBox.getChildren().addAll(titleLabel, hBox, titleLabelTip, tip, tableView);
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
        goods = new ArrayList<>();
        //连接数据库，从数据库中读
        module_main.SQL_connect();
        try {
            Statement st;
            ResultSet rs;
            String sql = "select g.Gno, g.Gname, g.Ginfo, v.Ssum " +
                    "from Good g, V_sell v " +
                    "where g.Gno = v.Gno;";
            st = shared.dbConn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                try {
                    String Gno = rs.getString(1);
                    String Gname = rs.getString(2);
                    String Brand = rs.getString(3);
                    int Ssum = rs.getInt(4);

                    Good good = new Good(Gno, Gname, Brand, Ssum);
                    goods.add(good);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            dbConn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private module_Supplier_Good createOderView(Good order, String username) {
        return new module_Supplier_Good(order, username);
    }

}
