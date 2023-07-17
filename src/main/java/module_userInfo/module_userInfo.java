package module_userInfo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import module_main.module_main;
import module_shared.shared;

import java.sql.SQLException;
import java.util.Optional;

public class module_userInfo extends Application {
    public static void main(String[] args) throws SQLException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        module_main.SQL_connect();
        method m=new method();
        m.sel("Users");
        primaryStage.setTitle("用户管理");
        //创建页面标题
        Label titleLabel = new Label("用户管理");
        titleLabel.setStyle("-fx-font-size: 20px;");
        // 创建控件
        TextField searchField = new TextField();
        searchField.setPrefWidth(600);
        searchField.setPromptText("请输入用户名");
        Button searchButton = new Button("搜索");
        //设置‘搜索’按钮颜色
        shared.init_Button_Style(searchButton,20,60);
        shared.button_change(searchButton);
        //使文本框于搜索按钮并排
        HBox hBox = new HBox(10, searchField, searchButton);
        //创建列表
        TableView<MyText> tableView = new TableView<>();
        TableColumn<MyText, String> usernameColumn = new TableColumn<>("用户名");
        TableColumn<MyText, String> pswColumn = new TableColumn<>("密码");
        TableColumn<MyText, Integer> AUTHColumn = new TableColumn<>("权限");
        TableColumn<MyText, String> ucifColumn = new TableColumn<>("联系方式");
        TableColumn<MyText, String> UaddrColumn = new TableColumn<>("地址");

        // 设置列与Order对象的属性关联
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        pswColumn.setCellValueFactory(cellData -> cellData.getValue().pswProperty());
        AUTHColumn.setCellValueFactory(cellData -> cellData.getValue().AUTHProperty().asObject());
        ucifColumn.setCellValueFactory(cellData -> cellData.getValue().ucifProperty());
        UaddrColumn.setCellValueFactory(cellData -> cellData.getValue().UaddrProperty());

        // 添加列到表格
        tableView.getColumns().addAll(usernameColumn, pswColumn, AUTHColumn, ucifColumn, UaddrColumn);
        // 设置表格数据
        tableView.setItems(FXCollections.observableList(m.orders));

        Button b1 = new Button("增加");
        Button b2 = new Button("删除");
        Button b3 = new Button("修改");
        Button b4 = new Button("刷新");
        shared.init_Button_Style(b1,30,60);
        shared.init_Button_Style(b2,30,60);
        shared.init_Button_Style(b3,30,60);
        shared.init_Button_Style(b4,30,60);
        shared.button_change(b1);
        shared.button_change(b2);
        shared.button_change(b3);
        shared.button_change(b4);
        //合并按钮
        FlowPane f1 = new FlowPane(b1, b2, b3, b4);
        f1.setHgap(20);//水平间距
        f1.setPadding(shared.menuPadding);//内间距

        //按用户名查询
        searchButton.setOnAction(a -> {
            String username = searchField.getText();
            m.select(username);

            // 设置表格数据
            tableView.setItems(FXCollections.observableList(m.orders));
            BorderPane borderPane = new BorderPane();
            VBox vBox = new VBox();
            vBox.setSpacing(10);
            vBox.setPadding(new Insets(10));
            vBox.getChildren().addAll(titleLabel, hBox, tableView);
            borderPane.setCenter(vBox);
            borderPane.setBottom(f1);
            // 创建场景
            Scene scene = new Scene(borderPane, 800, 600);
            // 设置场景并显示
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        });

        //删除事件
        b2.setOnAction(a -> {
            // 获取被选中的行
            MyText text = tableView.getSelectionModel().getSelectedItem();
            if (text == null) {
                new Alert(Alert.AlertType.ERROR, "请选中要删除的用户").showAndWait();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "确定删除？");
            Optional<ButtonType> btn = alert.showAndWait();
            if (btn.get() == ButtonType.OK) {
                //删除
                m.del(text.getUsername());
                // 设置表格数据
                method conn = new method();
                conn.sel("Users");
                tableView.setItems(FXCollections.observableList(conn.orders));
                BorderPane borderPane = new BorderPane();
                VBox vBox = new VBox();
                vBox.setSpacing(10);
                vBox.setPadding(new Insets(10));
                vBox.getChildren().addAll(titleLabel, hBox, tableView);
                borderPane.setCenter(vBox);
                borderPane.setBottom(f1);
                // 创建场景
                Scene scene = new Scene(borderPane, 800, 600);
                // 设置场景并显示
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);
                primaryStage.show();
            }
        });

        //修改事件
        b3.setOnAction(a -> {
            // 获取被选中的行
            MyText text = tableView.getSelectionModel().getSelectedItem();
            if (text == null) {
                new Alert(Alert.AlertType.ERROR, "请选中要修改的用户").showAndWait();
                return;
            }
            //创建新界面
            Stage stage = new Stage();
            stage.setTitle("增加学生");
            GridPane pane = new GridPane();
            pane.setAlignment(Pos.CENTER);//居中
            pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
            pane.setHgap(5.5);
            pane.setVgap(5.5);

            BorderPane borderPane2 = new BorderPane();
            Label l1 = new Label("用户姓名：");
            TextField v1 = new TextField();
            v1.setText(text.getUsername());
            Label l2 = new Label("用户密码：");
            TextField v2 = new TextField();
            v2.setText(text.getPsw());
            Label l3 = new Label("用户权限：");
            TextField v3 = new TextField();
            String x = text.getAUTH() + "";
            v3.setText(x);
            Label l4 = new Label("联系方式：");
            TextField v4 = new TextField();
            v4.setText(text.getUcif());
            Label l5 = new Label("     地址：");
            TextField v5 = new TextField();
            v5.setText(text.getUaddr());
            Button sure = new Button("确认");
            pane.add(l1, 0, 0);
            pane.add(v1, 1, 0);
            pane.add(l2, 0, 1);
            pane.add(v2, 1, 1);
            pane.add(l3, 0, 2);
            pane.add(v3, 1, 2);
            pane.add(l4, 0, 3);
            pane.add(v4, 1, 3);
            pane.add(l5, 0, 4);
            pane.add(v5, 1, 4);
            pane.add(sure, 1, 5);
            GridPane.setHalignment(sure, HPos.RIGHT);//右对齐

            //显示
            Scene scene2 = new Scene(pane);
            stage.setScene(scene2);
            stage.setResizable(false);
            stage.show();

            sure.setOnAction(b -> {
                //获取值
                String username = v1.getText();
                String psw = v2.getText();
                String auth = v3.getText();
                String ucif = v4.getText();
                String Uaddr = v5.getText();
                int AUTH = Integer.parseInt(auth);
                m.update(text.getUsername(), username, psw, AUTH, ucif, Uaddr);//修改操作
                stage.close();//关闭当前界面

                // 设置表格数据
                method conn = new method();
                conn.sel("Users");
                tableView.setItems(FXCollections.observableList(conn.orders));
                BorderPane borderPane = new BorderPane();
                VBox vBox = new VBox();
                vBox.setSpacing(10);
                vBox.setPadding(new Insets(10));
                vBox.getChildren().addAll(titleLabel, hBox, tableView);
                borderPane.setCenter(vBox);
                borderPane.setBottom(f1);
                // 创建场景
                Scene scene = new Scene(borderPane, 800, 600);
                // 设置场景并显示
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);
                primaryStage.show();
            });
        });

        //增加事件
        b1.setOnAction(a -> {
            //创建新界面
            Stage stage = new Stage();
            stage.setTitle("增加学生");
            GridPane pane = new GridPane();
            pane.setAlignment(Pos.CENTER);//居中
            pane.setPadding(shared.menuPadding);
            pane.setHgap(5.5);
            pane.setVgap(5.5);

            BorderPane borderPane2 = new BorderPane();
            Label l1 = new Label("用户姓名：");
            TextField v1 = new TextField();

            Label l2 = new Label("用户密码：");
            TextField v2 = new TextField();

            Label l3 = new Label("用户权限：");
            TextField v3 = new TextField();

            Label l4 = new Label("联系方式：");
            TextField v4 = new TextField();

            Label l5 = new Label("地址：");
            TextField v5 = new TextField();

            Button sure = new Button("确认");
            pane.add(l1, 0, 0);
            pane.add(v1, 1, 0);
            pane.add(l2, 0, 1);
            pane.add(v2, 1, 1);
            pane.add(l3, 0, 2);
            pane.add(v3, 1, 2);
            pane.add(l4, 0, 3);
            pane.add(v4, 1, 3);
            pane.add(l5, 0, 4);
            pane.add(v5, 1, 4);
            pane.add(sure, 1, 5);
            GridPane.setHalignment(sure, HPos.RIGHT);//右对齐

            //显示
            Scene scene2 = new Scene(pane);
            stage.setScene(scene2);
            stage.setResizable(false);
            stage.show();

            sure.setOnAction(b -> {
                //获取值
                String username = v1.getText();
                String psw = v2.getText();
                String auth = v3.getText();
                String ucif = v4.getText();
                String Uaddr = v5.getText();
                int AUTH = Integer.parseInt(auth);
                m.add(username, psw, AUTH, ucif, Uaddr);//修改操作
                stage.close();//关闭当前界面

                // 设置表格数据
                method conn = new method();
                conn.sel("Users");
                tableView.setItems(FXCollections.observableList(conn.orders));
                BorderPane borderPane = new BorderPane();
                VBox vBox = new VBox();
                vBox.setSpacing(10);
                vBox.setPadding(new Insets(10));
                vBox.getChildren().addAll(titleLabel, hBox, tableView);
                borderPane.setCenter(vBox);
                borderPane.setBottom(f1);
                // 创建场景
                Scene scene = new Scene(borderPane, 800, 600);
                // 设置场景并显示
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);
                primaryStage.show();
            });
        });

        //刷新界面
        b4.setOnAction(a -> {
            m.sel("Users");
            tableView.setItems(FXCollections.observableList(m.orders));
            BorderPane borderPane = new BorderPane();
            VBox vBox = new VBox();
            vBox.setSpacing(10);
            vBox.setPadding(new Insets(10));
            vBox.getChildren().addAll(titleLabel, hBox, tableView);
            borderPane.setCenter(vBox);
            borderPane.setBottom(f1);
            // 创建场景
            Scene scene = new Scene(borderPane, 800, 600);
            // 设置场景并显示
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        });

        // 创建布局
        BorderPane borderPane = new BorderPane();
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(titleLabel, hBox, tableView);
        borderPane.setCenter(vBox);
        borderPane.setBottom(f1);
        // 创建场景
        Scene scene = new Scene(borderPane, 800, 600);
        // 设置场景并显示
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
