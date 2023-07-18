package module_supplier_good;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import module_browse.Good;
import module_main.module_main;
import module_shared.shared;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static module_shared.shared.dbConn;

public class module_Supplier_Good extends Application {

    Good good;
    String username;
    private Statement st;
    private List<Supplier_Good> supplierList;

    public module_Supplier_Good(Good order, String username) {
        this.good = order;
        this.username = username;
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        try {
            module_main.SQL_connect();
            showSupplier_Good();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void showSupplier_Good() {
        ResultSet rs;
        //筛选指定的商品 sql
        module_main.SQL_connect();
        String sql = "select * from Supplier_Good where Gno = ?";
        TableView<Supplier_Good> tableview = new TableView<>();
        supplierList = new ArrayList<>();
        try {
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setString(1, good.getGno());
            rs = ps.executeQuery();
            while (rs.next()) {
                String Sno = rs.getString(1);
                String Gno = rs.getString(2);
                double Inprice = rs.getDouble(3);
                double Infee = rs.getDouble(4);
                Supplier_Good supplier_good = new Supplier_Good(Sno, Gno, Inprice, Infee);
                supplierList.add(supplier_good);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Label title = new Label("可提供商品的供应商");
        title.setStyle("-fx-font-size: 20px");

        Callback<TableColumn<Supplier_Good, Void>, TableCell<Supplier_Good, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Supplier_Good, Void> call(final TableColumn<Supplier_Good, Void> param) {
                final TableCell<Supplier_Good, Void> cell = new TableCell<>() {
                    private final Button button = new Button("采购");

                    {
                        shared.init_Button_Style(button, button.getPrefHeight(), button.getPrefWidth());
                        shared.button_change(button);
                        button.setOnAction(e -> {
                            Supplier_Good order = getTableView().getItems().get(getIndex());
                            if (order != null) {
                                // 在这里实现跳转逻辑，可以打开新窗口或者加载新的界面
                                purchaseGoods(order);
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

        TableColumn<Supplier_Good, String> SnoColumn = new TableColumn<>("供应商编号");
        SnoColumn.setCellValueFactory(cellData -> cellData.getValue().SnoProperty());


        TableColumn<Supplier_Good, String> GnoColumn = new TableColumn<>("物品编号");
        GnoColumn.setCellValueFactory(cellData -> cellData.getValue().GnoProperty());


        TableColumn<Supplier_Good, Double> InpriceColumn = new TableColumn<>("进价");
        InpriceColumn.setCellValueFactory(new PropertyValueFactory<>("inPrice"));


        TableColumn<Supplier_Good, Double> InfeeColumn = new TableColumn<>("运输费用");
        InfeeColumn.setCellValueFactory(new PropertyValueFactory<>("Infee"));


        TableColumn<Supplier_Good, Void> buttonColumn = new TableColumn<>("采购");
        buttonColumn.setCellFactory(cellFactory);


        Stage primaryStage = new Stage();
        Button closeButton = new Button("关闭");
        shared.init_Button_Style(closeButton, 20, 40);
        shared.button_change(closeButton);
        closeButton.setOnAction(e -> primaryStage.close());


        TextField searchField = new TextField();
        searchField.setPrefWidth(700);
        searchField.setPromptText("输入供应商编号");


        Button searchButton = new Button("搜索");
        shared.init_Button_Style(searchButton, 20, 40);
        shared.button_change(searchButton);
        searchButton.setOnAction(e -> {
            String searchWord = searchField.getText();
            if (searchWord.isEmpty()) {
                tableview.setItems(FXCollections.observableList(supplierList));
            } else {
                List<Supplier_Good> searchResult = new ArrayList<>();
                for (Supplier_Good order : supplierList) {
                    if (order.getSno().contains(searchWord)) {
                        searchResult.add(order);
                    }
                }
                tableview.setItems(FXCollections.observableList(searchResult));
            }
        });


        tableview.getColumns().addAll(SnoColumn, GnoColumn, InpriceColumn, InfeeColumn, buttonColumn);
        tableview.setItems(FXCollections.observableList(supplierList));

        HBox searchBox = new HBox();
        searchBox.setSpacing(10);
        searchBox.setAlignment(Pos.CENTER);

        searchBox.getChildren().addAll(searchField, searchButton);
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(title, searchBox, tableview);

        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("供应商物品信息");
        primaryStage.show();
    }


    //order为当前选购的商品
    public void purchaseGoods(Supplier_Good order) {
        Stage purchaseStage = new Stage();
        Label quantityLabel = new Label("数量:");
        TextField quantityField = new TextField();
        quantityLabel.setFont(new Font(15));
        Button purchaseButton = new Button("采购");
        shared.init_Button_Style(purchaseButton, purchaseButton.getPrefHeight(), purchaseButton.getPrefWidth());
        shared.button_change(purchaseButton);


        purchaseButton.setOnAction(event -> {
            String quantity = quantityField.getText();
            if (!quantity.isEmpty() && quantity.matches("\\d+")) {
                int quantityValue = Integer.parseInt(quantity);

                //将采购的商品添加到采购表中
                savePurchaseOrderToSchedule(order, quantityValue);
                purchaseStage.close();
            } else {
                // 显示错误信息，提示用户输入有效的数量
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("错误");
                alert.setHeaderText(null);
                alert.setContentText("请输入有效的数量（正整数）！");
                alert.showAndWait();
            }

        });


        TableColumn<Supplier_Good, String> SnoColumn = new TableColumn<>("供应商编号");
        SnoColumn.setCellValueFactory(cellData -> cellData.getValue().SnoProperty());


        TableColumn<Supplier_Good, String> GnoColumn = new TableColumn<>("物品编号");
        GnoColumn.setCellValueFactory(cellData -> cellData.getValue().GnoProperty());


        TableColumn<Supplier_Good, Double> InpriceColumn = new TableColumn<>("进价");
        InpriceColumn.setCellValueFactory(new PropertyValueFactory<>("inPrice"));


        TableColumn<Supplier_Good, Double> InfeeColumn = new TableColumn<>("运输费用");
        InfeeColumn.setCellValueFactory(new PropertyValueFactory<>("Infee"));

        TableView<Supplier_Good> tableview = new TableView<>();
        List<Supplier_Good> supplierList = new ArrayList<>();

        tableview.getColumns().addAll(SnoColumn, GnoColumn, InpriceColumn, InfeeColumn);
        tableview.setItems(FXCollections.observableList(supplierList));

        HBox hbox = new HBox();
        hbox.getChildren().addAll(quantityLabel, quantityField, purchaseButton);
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(10);
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        layout.getChildren().addAll(hbox, tableview);

        Scene scene = new Scene(layout, 800, 600);

        purchaseStage.setTitle("采购界面");
        purchaseStage.setScene(scene);
        purchaseStage.show();
    }

    //将采购单加入采购表中
    private void savePurchaseOrderToSchedule(Supplier_Good order, int quantityValue) {
        //将订单写到数据库中
        try {
            String sql = "INSERT INTO Purchase " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter strFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            DateTimeFormatter sqlFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String strtime = currentTime.format(strFormatter);
            String sqltime = currentTime.format(sqlFormatter);

            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setString(1, "C" + strtime);
            ps.setString(2, "CP" + strtime);
            ps.setString(3, order.getGno());
            ps.setString(4, order.getSno());
            ps.setString(5, sqltime);
            ps.setString(6, String.valueOf(quantityValue));
            ps.setString(7, String.valueOf(order.getInprice()));
            ps.setString(8, username);
            ps.setString(9, String.valueOf(0));
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}