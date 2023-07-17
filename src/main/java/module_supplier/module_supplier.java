package module_supplier;


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import java.util.ArrayList;

import module_shared.shared;
import module_main.module_main;
import module_supplier.Supplier;

import java.util.List;
import java.sql.*;





public class module_supplier extends Application {
    private Statement st;
    private List<Supplier> supplierList;
    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage primaryStage) {
        try
        {
            module_main.SQL_connect();
            showSupplier();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }




    public void showSupplier()
    {
        String sql="select * from Supplier";
        TableView<Supplier> tableview = new TableView<>();
        supplierList = new ArrayList<>();
        try
        {
            ResultSet rs;
            st = shared.dbConn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next())
            {
                String sno = rs.getString(1);
                String sname = rs.getString(2);
                String SCIF = rs.getString(3);
                Supplier supplier_1 = new Supplier(sno, sname, SCIF);
                supplierList.add(supplier_1);

            }
            rs.close();
            st.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        TableColumn<Supplier, String> idColumn = new TableColumn<>("Sno");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().SnoProperty());
        TableColumn<Supplier, String> nameColumn = new TableColumn<>("Sname");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().SnameProperty());
        TableColumn<Supplier, String> phoneColumn = new TableColumn<>("SCIF");
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().SCIFProperty());

        tableview.getColumns().addAll(idColumn,nameColumn,phoneColumn);

        tableview.setItems(FXCollections.observableList(supplierList));

        Button closeButton = new Button("关闭");
        Stage primaryStage = new Stage();
        shared.init_Button_Style(closeButton,20,40);
        shared.button_change(closeButton);
        closeButton.setOnAction(e -> primaryStage.close());


        Button searchButton = new Button("搜索");
        TextField searchField = new TextField();
        searchField.setPromptText("输入供应商编号或名称");
        shared.button_change(searchButton);
        shared.init_Button_Style(searchButton,20,40);
        searchField.setPrefWidth(700);


        searchButton.setOnAction(e->{
            String keyWord = searchField.getText();
            if(keyWord.isEmpty()) {
                tableview.setItems(FXCollections.observableList(supplierList));
            }
            else {
                List<Supplier> searchResult = new ArrayList<>();
                for (Supplier order : supplierList) {
                    if(order.getSno().contains(keyWord)||order.getSname().contains(keyWord)) {
                        searchResult.add(order);
                    }
                }
                tableview.setItems(FXCollections.observableList(searchResult));
            }

        });
        HBox searchBox = new HBox();
        searchBox.setSpacing(10);
        searchBox.setAlignment(Pos.CENTER);

        searchBox.getChildren().addAll(searchField, searchButton,tableview);


        BorderPane root = new BorderPane();
        root.setTop(searchBox);
        root.setCenter(tableview);
        root.setBottom(closeButton);
        BorderPane.setAlignment(closeButton, Pos.CENTER_RIGHT);
        BorderPane.setMargin(closeButton, new Insets(10));
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("供应商信息");
        primaryStage.show();
    }

}