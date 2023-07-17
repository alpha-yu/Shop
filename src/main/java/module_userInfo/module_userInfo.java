package module_userInfo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import module_shared.shared;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static module_shared.shared.button_change;
import static module_shared.shared.init_Button_Style;

public class module_userInfo extends Application {
    static Stage stage;

    public static void main(String[] args) throws SQLException {
        launch(args);
    }

    public static void createStage(MyText text, method m, TableView tableView, String title, String option) {
        Stage stage = new Stage();
        stage.setTitle(title);
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);//居中
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setHgap(10);
        pane.setVgap(10);

        Label l1 = new Label("用户姓名：");
        l1.setStyle("-fx-font-size: 15px;");
        TextField v1 = new TextField();
        v1.setText(text.getUsername());
        Label l2 = new Label("用户密码：");
        l2.setStyle("-fx-font-size: 15px;");
        TextField v2 = new TextField();
        v2.setText(text.getPsw());
        Label l3 = new Label("用户权限：");
        l3.setStyle("-fx-font-size: 15px;");
        ComboBox<String> v3 = new ComboBox<>();
        ObservableList<String> options = FXCollections.observableArrayList(shared.TEXT_CUSTOMER, shared.TEXT_SELLER, shared.TEXT_PURCHASER, shared.TEXT_MANAGER, shared.TEXT_ADMINISTRATOR);
        v3.setItems(options);
        v3.setValue(shared.AUTH_to_text(text.getAUTH()));
        Label l4 = new Label("联系方式：");
        l4.setStyle("-fx-font-size: 15px;");
        TextField v4 = new TextField();
        v4.setText(text.getUcif());
        Label l5 = new Label("地址：");
        l5.setStyle("-fx-font-size: 15px;");
        TextField v5 = new TextField();
        v5.setText(text.getUaddr());
        Button sure = new Button("确认");
        init_Button_Style(sure, 30, 60);
        button_change(sure);
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
        pane.add(sure, 1, 6);
        GridPane.setHalignment(sure, HPos.RIGHT);//右对齐

        //显示
        Scene scene2 = new Scene(pane, 400, 300);
        stage.setScene(scene2);
        stage.setResizable(false);
        stage.show();

        sure.setOnAction(b -> {
            if (option.equals("alter")) {
                String username = v1.getText();
                String psw = v2.getText();
                String auth = String.valueOf(shared.text_to_AUTH(v3.getValue()));
                String ucif = v4.getText();
                String Uaddr = v5.getText();
                int AUTH = Integer.parseInt(auth);
                m.update(text.getUsername(), username, psw, AUTH, ucif, Uaddr);//修改操作
            } else if (option.equals("add")) {
                String username = v1.getText();
                String psw = v2.getText();
                String auth = String.valueOf(shared.text_to_AUTH(v3.getValue()));
                String ucif = v4.getText();
                String Uaddr = v5.getText();
                int AUTH = Integer.parseInt(auth);
                m.add(username, psw, AUTH, ucif, Uaddr);//修改操作
            }
            stage.close();//关闭当前界面
            m.sel();
            tableView.setItems(FXCollections.observableList(m.orders));
            tableView.refresh();
        });
    }

    public static void showUserInfo() {
        stage = new Stage();
        method m = new method();
        m.sel();
        stage.setTitle("用户管理");
        //创建页面标题
        Label titleLabel = new Label("用户管理");
        titleLabel.setStyle("-fx-font-size: 20px;");
        // 创建控件
        TextField searchField = new TextField();
        searchField.setPrefWidth(600);
        searchField.setPromptText("请输入用户名");
        Button searchButton = new Button("搜索");
        //设置‘搜索’按钮颜色
        init_Button_Style(searchButton, 20, 60);
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
        AUTHColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer state, boolean empty) {
                super.updateItem(state, empty);
                if (empty || state == null) {
                    setText("");
                } else if (state == shared.AUTH_CUSTOMER) {
                    setText("顾客");
                } else if (state == shared.AUTH_SELLER) {
                    setText("售货员");
                } else if (state == shared.AUTH_PURCHASER) {
                    setText("采购员");
                } else if (state == shared.AUTH_MANAGER) {
                    setText("经理");
                } else if (state == shared.AUTH_ADMINISTRATOR) {
                    setText("系统管理员");
                } else {
                    setText("权限异常");
                }
            }
        });
        ucifColumn.setCellValueFactory(cellData -> cellData.getValue().ucifProperty());
        UaddrColumn.setCellValueFactory(cellData -> cellData.getValue().UaddrProperty());

        // 添加列到表格
        tableView.getColumns().addAll(usernameColumn, pswColumn, AUTHColumn, ucifColumn, UaddrColumn);
        // 设置表格数据
        tableView.setItems(FXCollections.observableList(m.orders));

        Button buttonAdd = new Button("增加");
        Button buttonDelete = new Button("删除");
        Button buttonAlter = new Button("修改");
        init_Button_Style(buttonAdd, 30, 60);
        init_Button_Style(buttonDelete, 30, 60);
        init_Button_Style(buttonAlter, 30, 60);
        shared.button_change(buttonAdd);
        shared.button_change(buttonDelete);
        shared.button_change(buttonAlter);
        ;
        //合并按钮
        FlowPane f1 = new FlowPane(buttonAdd, buttonDelete, buttonAlter);
        f1.setHgap(20);//水平间距
        f1.setPadding(shared.menuPadding);//内间距

        //按用户名查询
        searchButton.setOnAction(a -> {
            String username = searchField.getText();
            if (username.isEmpty()) {
                tableView.setItems(FXCollections.observableList(m.orders));
            } else {
                List<MyText> searchResult = new ArrayList<>();
                for (MyText myText : m.orders) {
                    if (myText.getUsername().contains(username)) {
                        searchResult.add(myText);
                    }
                }
                tableView.setItems(FXCollections.observableList(searchResult));
            }
        });

        //增加事件
        buttonAdd.setOnAction(a -> {
            MyText text = new MyText();
            //创建新界面
            createStage(text, m, tableView, "新增用户信息", "add");
        });

        //删除事件
        buttonDelete.setOnAction(a -> {
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
                m.sel();
                tableView.setItems(FXCollections.observableList(m.orders));
                tableView.refresh();
            }
        });

        //修改事件
        buttonAlter.setOnAction(a -> {
            // 获取被选中的行
            MyText text = tableView.getSelectionModel().getSelectedItem();
            if (text == null) {
                new Alert(Alert.AlertType.ERROR, "请选中要修改的用户").showAndWait();
                return;
            }
            //创建新界面
            createStage(text, m, tableView, "修改用户信息", "alter");
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
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    @Override
    public void start(Stage stage) {
        showUserInfo();
    }
}
