package module_trolley;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import module_browse.Good;
import module_browse.module_browse;
import module_shared.shared;

import javax.swing.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class module_trolley extends Application {
    final static Connection[] conn = new Connection[1];
    static List<Good> goodList = new ArrayList<>();
    static String username;
    static int auth;
    private static GridPane gridPane = new GridPane();

    public module_trolley() {
    }

    public module_trolley(String username, int auth, List<Good> goodList) {
        this.username = username;
        this.auth = auth;
        module_trolley.goodList = goodList;
    }

    public static void GoodsOutput(List<Good> goodList, GridPane gridPane) {
        for (int i = 0; i < goodList.size(); i++) {
            goodList.set(i, GoodsInfo(gridPane, goodList.get(i), 3, i));
        }
    }


    public static Good GoodsInfo(GridPane rightGirdPane, Good good, int x, int y) {
//        gridPane.getChildren().clear();
        HBox productBox = new HBox(20);
        productBox.setMinSize(700, 120);
        productBox.setMaxSize(700, 120);
        productBox.setPrefSize(700, 120);
//        productBox.setSpacing(0);
        File file = new File("src/main/resources/module_browse/images/" + good.getGno() + ".jpg");
        ImageView productImage;
        if (file.exists()) {
            productImage = new ImageView(new Image("file:src/main/resources/module_browse/images/" + good.getGno() + ".jpg"));
        } else {
            productImage = new ImageView(new Image("file:src/main/resources/module_browse/images/unknown.jpg"));
        }
        productImage.setFitWidth(100);
        productImage.setFitHeight(90);

        // 创建矩形剪辑区域，设置圆角属性
        Rectangle clip = new Rectangle(productImage.getFitWidth(), productImage.getFitHeight());
        clip.setArcWidth(20);
        clip.setArcHeight(20);

        // 将剪辑区域应用于 ImageView
        productImage.setClip(clip);


        VBox productDetails = new VBox(4);
        productDetails.setAlignment(Pos.CENTER_LEFT);
        productDetails.setPrefSize(600, 100);
        Button productName = new Button("商品编号: " + good.getGno() + "\t商品名称: " + good.getGname() + "\t\t优惠：" + good.getGinfo());
        productName.setStyle("-fx-background-color: transparent;-fx-border-color: transparent; -fx-text-fill: black;-fx-underline: false;-fx-padding: 0;");

        //当鼠标停留在按钮上时，颜色变深
        productName.setOnMouseEntered(event -> {

            productName.setStyle("-fx-background-color: transparent;-fx-border-color: transparent; -fx-text-fill: #0056b3;-fx-underline: true;-fx-padding: 0;");
        });
        productName.setOnMouseExited(event -> {
            productName.setStyle("-fx-background-color: transparent;-fx-border-color: transparent; -fx-text-fill: black;-fx-underline: false;-fx-padding: 0;");
        });

        productName.setFont(new Font("华文楷体", 19));

        HBox priceAndUnit = new HBox();
        HBox priceBox = new HBox();
        priceBox.setAlignment(Pos.CENTER);
        DecimalFormat decimalFormat = new DecimalFormat("0.#");
        String result = decimalFormat.format(good.getGprice());
        Label priceLabel = new Label("单价：￥" + result);
        priceLabel.setFont(new Font("Cambria Math", 17));

        Label unitLabel = new Label("\n/件");
        unitLabel.setFont(new Font("华文中宋", 11));


        String result1 = decimalFormat.format(good.getGprice() * good.getNum());
        Label priceSumLabel = new Label("    总价: ￥" + result1 + "\t");
        priceSumLabel.setFont(new Font("Cambria Math", 19));
        priceSumLabel.setTextFill(Paint.valueOf("#a61b29"));

        priceBox.setAlignment(Pos.CENTER);
        priceBox.getChildren().addAll(priceLabel, unitLabel);
        HBox quantityBox = new HBox();
        quantityBox.setAlignment(Pos.CENTER);
        Label goodNumlabel = new Label("商品数量：");
        goodNumlabel.setFont(new Font("Cambria Math", 17));
        Button minusButton = new Button("-");
        minusButton.setFont(new Font("Cambria Math", 9));
        minusButton.setStyle("-fx-border-radius: 25px; -fx-background-radius: 25px;-fx-background-color: #007bff; -fx-text-fill: white;");

        //当鼠标停留在按钮上时，颜色变深
        minusButton.setOnMouseEntered(event -> {
            minusButton.setStyle("-fx-border-radius: 25px; -fx-background-radius: 25px;-fx-background-color: #0056b3; -fx-text-fill: white;");
        });
        minusButton.setOnMouseExited(event -> {
            minusButton.setStyle("-fx-border-radius: 25px; -fx-background-radius: 25px;-fx-background-color: #007bff; -fx-text-fill: white;");
        });


        Label quantityLabel = new Label(" " + good.getNum() + " ");
        quantityLabel.setFont(new Font("Cambria Math", 17));
        Button plusButton = new Button("+");
        plusButton.setFont(new Font("Cambria Math", 9));
        plusButton.setStyle("-fx-border-radius: 25px; -fx-background-radius: 25px;-fx-background-color: #007bff; -fx-text-fill: white;");
        //当鼠标停留在按钮上时，颜色变深
        plusButton.setOnMouseEntered(event -> {
            plusButton.setStyle("-fx-border-radius: 25px; -fx-background-radius: 25px;-fx-background-color: #0056b3; -fx-text-fill: white;");
        });
        plusButton.setOnMouseExited(event -> {
            plusButton.setStyle("-fx-border-radius: 25px; -fx-background-radius: 25px;-fx-background-color: #007bff; -fx-text-fill: white;");
        });

        minusButton.setOnAction(e -> {
            if (Integer.parseInt(quantityLabel.getText().trim()) > 1) {
                quantityLabel.setText(" " + (Integer.parseInt(quantityLabel.getText().trim()) - 1) + " ");
                System.out.println(quantityLabel.getText());
                good.setNum(Integer.parseInt(quantityLabel.getText().trim()));
                System.out.println("trolleyGood是否包含" + good.getGname() + ":" + goodList.contains(good));

                for (int i = 0; i < goodList.size(); i++) {
                    System.out.println(goodList.get(i).getGno());
                }

                String result2 = decimalFormat.format(good.getGprice() * good.getNum());
                priceSumLabel.setText("    总价: ￥" + result2 + "\t");
            }
        });

        plusButton.setOnAction(e -> {
            quantityLabel.setText(" " + (Integer.parseInt(quantityLabel.getText().trim()) + 1) + " ");
            System.out.println(quantityLabel.getText());
            good.setNum(Integer.parseInt(quantityLabel.getText().trim()));
            System.out.println("trolleyGood是否包含" + good.getGname() + ":" + goodList.contains(good));
            if (!goodList.contains(good)) {
                goodList.add(good);
            }
            for (int i = 0; i < goodList.size(); i++) {
                System.out.println(goodList.get(i).getGno());
            }

            String result2 = decimalFormat.format(good.getGprice() * good.getNum());
            priceSumLabel.setText("    总价: ￥" + result2 + "\t");
        });

        Button deleteButton = new Button("删除");
        shared.init_Button_Style(deleteButton, 30, 60);
        shared.button_change(deleteButton);
        deleteButton.setOnAction(actionEvent -> {
            goodList.remove(good);
            gridPane.getChildren().clear();
            GoodsOutput(goodList, gridPane);
        });

        quantityBox.setSpacing(4);
        quantityBox.getChildren().addAll(goodNumlabel, minusButton, quantityLabel, plusButton);
        priceAndUnit.setPrefSize(700, 50);
        priceAndUnit.getChildren().addAll(priceBox, quantityBox, priceSumLabel, deleteButton);
        priceAndUnit.setSpacing(19);
        priceAndUnit.setAlignment(Pos.CENTER_LEFT);
        productDetails.setAlignment(Pos.CENTER_LEFT);
        productDetails.getChildren().addAll(productName, priceAndUnit);
        productBox.getChildren().addAll(productImage, productDetails);
        productBox.setAlignment(Pos.CENTER_LEFT);
        productBox.setStyle("-fx-padding: 20;-fx-background-color: transparent; -fx-border-color: #c0c4c3; -fx-border-radius: 25px; -fx-background-radius: 25px;");

        rightGirdPane.add(productBox, x, y);
        return good;
    }

    public static double getTotalPrice(List<Good> trolleyGood) {
        double totalPrice = 0;
        for (int i = 0; i < trolleyGood.size(); i++) {
            totalPrice += trolleyGood.get(i).getGprice() * trolleyGood.get(i).getNum();
        }
        return totalPrice;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void generateOrder() {

        // 计算总价
        double totalPrice = getTotalPrice(goodList);

        try {
            String sql = "insert into Orders " + "values (?, ?, ?, ?, ?, ?, ?, ?)";
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter strFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            DateTimeFormatter sqlFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String strTime = currentTime.format(strFormatter);
            String sqlTime = currentTime.format(sqlFormatter);

            int i = 0;
            for (; i < goodList.size(); i++) {
                PreparedStatement ps = shared.dbConn.prepareStatement(sql);
                ps.setString(1, "O" + strTime + String.valueOf(i));
                ps.setString(2, "OP" + strTime);
                ps.setString(3, username);
                ps.setString(4, goodList.get(i).getGno());
                ps.setString(5, String.valueOf(goodList.get(i).getNum()));
                ps.setString(6, sqlTime);
                ps.setString(7, String.valueOf(goodList.get(i).getGprice()));
                ps.setString(8, "0");
                ps.executeUpdate();
                ps.close();
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        // 清空购物车
        goodList.clear();
    }

    @Override
    public void start(Stage primaryStage) {

        for (int i = 0; i < goodList.size(); i++) {
            System.out.println(goodList.get(i).getGno());
        }

        Good good1 = new Good("0001", "咖啡豆", 18, "", "", "食品", "");
        Good good2 = new Good("0002", "口红", 65, "", "", "化妆品", "");
//        goodList.add(good1);
//        goodList.add(good2);

        gridPane.setPrefWidth(783);
        gridPane.setMinHeight(443);
        gridPane.setMaxWidth(783);


        Pane root = new Pane();
        root.setPrefSize(800, 600);

        Button backButton = new Button("返回");
        backButton.setLayoutX(34);
        backButton.setLayoutY(25);
        shared.init_Button_Style(backButton, 30, 60);
        shared.button_change(backButton);
        backButton.setFont(new Font(13));
        backButton.setOnAction(actionEvent -> {
            primaryStage.close();
            module_browse module_browse = new module_browse(goodList);
            try {
                module_browse.start(primaryStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Label titleLabel = new Label("购  物  车");
        titleLabel.setLayoutX(342);
        titleLabel.setLayoutY(19);
        titleLabel.setFont(new Font("华文中宋", 27));

        Hyperlink discountLink = new Hyperlink("优 惠 政 策");
        discountLink.setLayoutX(658);
        discountLink.setLayoutY(27);
        discountLink.setUnderline(true);
        discountLink.setFont(new Font("华文中宋", 14));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setLayoutY(79);
        scrollPane.setPrefSize(802, 445);
        scrollPane.setMaxWidth(802);
        scrollPane.setStyle("-fx-background-color: transparent;-fx-border-color: transparent;");

        gridPane.setHgap(13);
        gridPane.setVgap(20);
        gridPane.setStyle("-fx-background-color: transparent;");
        GoodsOutput(goodList, gridPane);

        scrollPane.setContent(gridPane);

        Label totalPriceLabel = new Label("合计总价：");
        totalPriceLabel.setLayoutX(34);
        totalPriceLabel.setLayoutY(525);
        totalPriceLabel.setFont(new Font("华文中宋", 25));
        totalPriceLabel.setTextFill(Paint.valueOf("#a61b29"));

        Label priceLabel = new Label("￥" + getTotalPrice(goodList));
        priceLabel.setLayoutX(150);
        priceLabel.setLayoutY(522);
        priceLabel.setFont(new Font("Cambria Math", 30));
        priceLabel.setTextFill(Paint.valueOf("#a61b29"));

        Button checkoutButton = new Button(" 下 单 ");
        checkoutButton.setLayoutX(650);
        checkoutButton.setLayoutY(525);
        shared.init_Button_Style(checkoutButton, 30, 80);
        shared.button_change(checkoutButton);
        checkoutButton.setFont(new Font(16));
        checkoutButton.setOnAction(actionEvent -> {
            //弹窗提示已经下单成功
            //addToOrders(goodList);
            generateOrder();
            System.out.println("下单成功");
            String title = "下单成功";
            String warning = "购买成功!";
            JOptionPane.showMessageDialog(null, warning, title, JOptionPane.PLAIN_MESSAGE);
            goodList.clear();
            gridPane.getChildren().clear();
            GoodsOutput(goodList, gridPane);
            module_trolley b = new module_trolley(username, auth, goodList);
            b.start(primaryStage);
        });

        root.getChildren().addAll(backButton, titleLabel, discountLink, scrollPane, totalPriceLabel, priceLabel, checkoutButton);

        primaryStage.setTitle("购物车");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}