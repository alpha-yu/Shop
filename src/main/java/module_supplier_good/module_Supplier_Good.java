package module_supplier_good;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import module_main.module_main;
import module_shared.shared;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class module_Supplier_Good extends Application {

    private Statement st;
    private List<Supplier_Good> supplierList;
    public static void main(String[] args) {
        launch(args);
    }

//    public void connectedDatabase()
//    {
//        try
//        {
//            module_login.loginExecute();
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void start(Stage primaryStage) {
        try
        {
//            connectedDatabase();
            module_main.SQL_connect();
            showSupplier_Good();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }


    public void showSupplier_Good()
    {
        ResultSet rs;
        String sql="select * from Supplier_Good";
        TableView<Supplier_Good> tableview = new TableView<>();
        supplierList = new ArrayList<>();
        try {
            st = shared.dbConn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next())
            {
                String Sno = rs.getString("Sno");
                String Gno = rs.getString("Gno");
                double Inprice = rs.getDouble("Inprice");
                double Infee = rs.getDouble("Infee");
                Supplier_Good supplier_good = new Supplier_Good(Sno,Gno,Inprice,Infee);
                supplierList.add(supplier_good);

            }
            rs.close();
            st.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }


        TableColumn<Supplier_Good, String> SnoColumn = new TableColumn<>("Sno");
        SnoColumn.setCellValueFactory(cellData -> cellData.getValue().SnoProperty());


        TableColumn<Supplier_Good, String> GnoColumn = new TableColumn<>("Gno");
        GnoColumn.setCellValueFactory(cellData -> cellData.getValue().GnoProperty());


        TableColumn<Supplier_Good, Double> InpriceColumn = new TableColumn<>("Inprice");
        InpriceColumn.setCellValueFactory(new PropertyValueFactory<>("inPrice"));


        TableColumn<Supplier_Good, Double> InfeeColumn = new TableColumn<>("Infee");
        InfeeColumn.setCellValueFactory(new PropertyValueFactory<>("Infee"));


        tableview.getColumns().addAll(SnoColumn,GnoColumn,InpriceColumn,InfeeColumn);
        tableview.setItems(FXCollections.observableList(supplierList));


        Stage primaryStage = new Stage();
        Button closeButton = new Button("关闭");
        shared.init_Button_Style(closeButton,20,40);
        shared.button_change(closeButton);
        closeButton.setOnAction(e -> primaryStage.close());


        TextField searchField = new TextField();
        searchField.setPrefWidth(700);
        searchField.setPromptText("输入供应商编号");


        Button searchButton = new Button("搜索");
        shared.init_Button_Style(searchButton,20,40);
        shared.button_change(searchButton);
        searchButton.setOnAction(e->{
            String searchWord = searchField.getText();
            if(searchWord.isEmpty())
            {
                tableview.setItems(FXCollections.observableList(supplierList));
            }
            else {
                List<Supplier_Good> searchResult = new ArrayList<>();
                for (Supplier_Good order : supplierList) {
                    if(order.getSno().contains(searchWord)) {
                        searchResult.add(order);
                    }
                }
                tableview.setItems(FXCollections.observableList(searchResult));
            }
        });


        HBox searchBox = new HBox();
        searchBox.setSpacing(10);
        searchBox.setAlignment(Pos.CENTER);

        searchBox.getChildren().addAll(searchField,searchButton,tableview);

        BorderPane root = new BorderPane();
        root.setTop(searchBox);
        root.setCenter(tableview);
        root.setBottom(closeButton);
        BorderPane.setAlignment(closeButton, Pos.CENTER_RIGHT);
        BorderPane.setMargin(closeButton, new Insets(10));
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("供应商物品信息");
        primaryStage.show();
    }
}