package module_trolley;
import javafx.application.Application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import module_browse.Good;
import module_browse.module_browse;

import java.io.File;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static module_browse.module_browse.JDBC;


public class module_trolley extends Application {
    public module_trolley() {
    }

    static List<Good> goodList=new ArrayList<>();
    private static GridPane gridPane=new GridPane();

    public module_trolley(List<Good> goodList) {
        module_trolley.goodList = goodList;
    }

    @Override
    public void start(Stage primaryStage) {

        for(int i=0;i<goodList.size();i++)
        {
            System.out.println(goodList.get(i).getGno());
        }


        Good good1=new Good("0001","咖啡豆",18,"","","食品","");
        Good good2=new Good("0002","口红",65,"","","化妆品","");
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
        backButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        //当鼠标停留在按钮上时，颜色变深
        backButton.setOnMouseEntered(event -> {
            backButton.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white;");
        });
        backButton.setOnMouseExited(event -> {
            backButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        });
        backButton.setFont(new Font(13));
        backButton.setOnAction(actionEvent -> {
            primaryStage.close();
            module_browse module_browse=new module_browse(goodList);
            try {
                module_browse.start(primaryStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


        Label titleLabel = new Label("购  物  车");
        titleLabel.setLayoutX(342);
        titleLabel.setLayoutY(19);
        titleLabel.setFont(new Font("华文中宋",27));

        Hyperlink discountLink = new Hyperlink("优 惠 政 策");
        discountLink.setLayoutX(658);
        discountLink.setLayoutY(27);
        discountLink.setUnderline(true);
        discountLink.setFont(new Font("华文中宋",14));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setLayoutY(79);
        scrollPane.setPrefSize(802, 445);
        scrollPane.setMaxWidth(802);
        scrollPane.setStyle("-fx-background-color: transparent;-fx-border-color: transparent;");

        gridPane.setHgap(13);
        gridPane.setVgap(20);
        gridPane.setStyle("-fx-background-color: transparent;");
        GoodsOutput(goodList,gridPane);

        scrollPane.setContent(gridPane);

        Label totalPriceLabel = new Label("合计总价：");
        totalPriceLabel.setLayoutX(34);
        totalPriceLabel.setLayoutY(525);
        totalPriceLabel.setFont(new Font("华文中宋",25));
        totalPriceLabel.setTextFill(Paint.valueOf("#a61b29"));

        Label priceLabel = new Label("￥"+getTotalPrice(goodList));
        priceLabel.setLayoutX(150);
        priceLabel.setLayoutY(522);
        priceLabel.setFont(new Font("Cambria Math",30));
        priceLabel.setTextFill(Paint.valueOf("#a61b29"));

        Button checkoutButton = new Button(" 下 单 ");
        checkoutButton.setLayoutX(650);
        checkoutButton.setLayoutY(525);
        checkoutButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        //当鼠标停留在按钮上时，颜色变深
        checkoutButton.setOnMouseEntered(event -> {
            checkoutButton.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white;");
        });
        checkoutButton.setOnMouseExited(event -> {
            checkoutButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        });
        checkoutButton.setFont(new Font(16));

        checkoutButton.setOnAction(actionEvent -> {
            //弹窗提示已经下单成功
//            addToOrders(goodList);
            System.out.println("下单成功");
            goodList.clear();
        });

        root.getChildren().addAll(backButton, titleLabel, discountLink, scrollPane, totalPriceLabel, priceLabel, checkoutButton);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();


    }


    final static Connection[] conn = new Connection[1];
//    public static void addToOrders(List<Good> goodList)
//    {
//        String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=Shop";
//        String userName="sa";
//        String userPwd="123456";
//        try {
//            conn[0] = DriverManager.getConnection(dbURL, userName, userPwd);
//            // 从数据库中获取数据
//            Statement stmt = conn[0].createStatement();
//            ResultSet rs = stmt.executeQuery("insert into Orders values("+);
//            while (rs.next()){
//                System.out.println(rs.getString("Gno") + "\t" + rs.getString("Gname") + "\t" + rs.getString("Gprice") + "\t" + rs.getString("Ginfo") + "\t" + rs.getString("MATL") + "\t" + rs.getString("CATEG") + "\t" + rs.getString("EXPdate"));
//                goodList.add(new Good(rs.getString("Gno"),rs.getString("Gname"), rs.getDouble("Gprice"),rs.getString("Ginfo"),rs.getString("MATL"),rs.getString("CATEG"),rs.getString("EXPdate")));
//            }
//
//        }catch(SQLException e){
//            e.printStackTrace();
//            System.out.println("数据库连接失败");
//        }
//    }

    public static void GoodsOutput(List<Good> goodList,GridPane gridPane)
    {
        for (int i=0;i<goodList.size();i++)
        {
            goodList.set(i,GoodsInfo(gridPane,goodList.get(i),3,i));
        }
    }

    public static Good GoodsInfo(GridPane rightGirdPane,Good good,int x,int y)
    {
//        gridPane.getChildren().clear();
        HBox productBox = new HBox(20);
        productBox.setMinSize(700,120);
        productBox.setMaxSize(700,120);
        productBox.setPrefSize(700, 120);
//        productBox.setSpacing(0);
        File file=new File("src/main/resources/module_browse/images/"+good.getGno()+".jpg");
        ImageView productImage;
        if (file.exists())
        {
            productImage = new ImageView(new Image("file:src/main/resources/module_browse/images/"+good.getGno()+".jpg"));
        }
        else
        {
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
        Button productName = new Button("商品编号: "+good.getGno()+"\t商品名称: "+good.getGname()+"\t\t优惠："+good.getGinfo());
        productName.setStyle("-fx-background-color: transparent;-fx-border-color: transparent; -fx-text-fill: black;-fx-underline: false;-fx-padding: 0;");

        //当鼠标停留在按钮上时，颜色变深
        productName.setOnMouseEntered(event -> {

            productName.setStyle("-fx-background-color: transparent;-fx-border-color: transparent; -fx-text-fill: #0056b3;-fx-underline: true;-fx-padding: 0;");
        });
        productName.setOnMouseExited(event -> {
            productName.setStyle("-fx-background-color: transparent;-fx-border-color: transparent; -fx-text-fill: black;-fx-underline: false;-fx-padding: 0;");
        });

        productName.setFont(new Font("华文楷体",19));

        HBox priceAndUnit = new HBox();
        HBox priceBox = new HBox();
        priceBox.setAlignment(Pos.CENTER);
        DecimalFormat decimalFormat = new DecimalFormat("0.#");
        String result = decimalFormat.format(good.getGprice());
        Label priceLabel = new Label("单价：￥"+result);
        priceLabel.setFont(new Font("Cambria Math",17));

        Label unitLabel = new Label("\n/件");
        unitLabel.setFont(new Font("华文中宋",11));


        String result1 = decimalFormat.format(good.getGprice()*good.getNum());
        Label priceSumLabel = new Label("    总价: ￥"+result1+"\t");
        priceSumLabel.setFont(new Font("Cambria Math",19));
        priceSumLabel.setTextFill(Paint.valueOf("#a61b29"));

        priceBox.setAlignment(Pos.CENTER);
        priceBox.getChildren().addAll(priceLabel, unitLabel);
        HBox quantityBox = new HBox();
        quantityBox.setAlignment(Pos.CENTER);
        Label goodNumlabel = new Label("商品数量：");
        goodNumlabel.setFont(new Font("Cambria Math",17));
        Button minusButton = new Button("-");
        minusButton.setFont(new Font("Cambria Math",9));
        minusButton.setStyle("-fx-border-radius: 25px; -fx-background-radius: 25px;-fx-background-color: #007bff; -fx-text-fill: white;");

        //当鼠标停留在按钮上时，颜色变深
        minusButton.setOnMouseEntered(event -> {
            minusButton.setStyle("-fx-border-radius: 25px; -fx-background-radius: 25px;-fx-background-color: #0056b3; -fx-text-fill: white;");
        });
        minusButton.setOnMouseExited(event -> {
            minusButton.setStyle("-fx-border-radius: 25px; -fx-background-radius: 25px;-fx-background-color: #007bff; -fx-text-fill: white;");
        });


        Label quantityLabel = new Label(" "+good.getNum()+" ");
        quantityLabel.setFont(new Font("Cambria Math",17));
        Button plusButton = new Button("+");
        plusButton.setFont(new Font("Cambria Math",9));
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
//                goodNum=Integer.parseInt(quantityLabel.getText().trim());
                good.setNum(Integer.parseInt(quantityLabel.getText().trim()));
                System.out.println("trolleyGood是否包含"+good.getGname()+":"+goodList.contains(good));

                for(int i=0;i<goodList.size();i++)
                {
                    System.out.println(goodList.get(i).getGno());
                }

                String result2 = decimalFormat.format(good.getGprice()*good.getNum());
                priceSumLabel.setText("    总价: ￥"+result2+"\t");
            }
        });

        plusButton.setOnAction(e -> {
            quantityLabel.setText(" "+(Integer.parseInt(quantityLabel.getText().trim())+1)+" ");
            System.out.println(quantityLabel.getText());
//            goodNum=Integer.parseInt(quantityLabel.getText().trim());
            good.setNum(Integer.parseInt(quantityLabel.getText().trim()));
            System.out.println("trolleyGood是否包含"+good.getGname()+":"+goodList.contains(good));
            if(!goodList.contains(good))
            {
                goodList.add(good);
            }
            for(int i=0;i<goodList.size();i++)
            {
                System.out.println(goodList.get(i).getGno());
            }

            String result2 = decimalFormat.format(good.getGprice()*good.getNum());
            priceSumLabel.setText("    总价: ￥"+result2+"\t");
        });

        Button deleteButton=new Button("删除");
        deleteButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        //当鼠标停留在按钮上时，颜色变深
        deleteButton.setOnMouseEntered(event -> {
            deleteButton.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white;");
        });
        deleteButton.setOnMouseExited(event -> {
            deleteButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        });
        deleteButton.setOnAction(actionEvent -> {
            goodList.remove(good);
            gridPane.getChildren().clear();
            GoodsOutput(goodList,gridPane);
        });


        quantityBox.setSpacing(4);
        quantityBox.getChildren().addAll(goodNumlabel,minusButton, quantityLabel, plusButton);
        priceAndUnit.setPrefSize(700,50);
        priceAndUnit.getChildren().addAll(priceBox, quantityBox,priceSumLabel,deleteButton);
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

    public static double getTotalPrice(List<Good> trolleyGood)
    {
        double totalPrice=0;
        for(int i=0;i<trolleyGood.size();i++)
        {
            totalPrice+=trolleyGood.get(i).getGprice()*trolleyGood.get(i).getNum();
        }
        return totalPrice;
    }

    public static void main(String[] args) {
        launch(args);
    }

}