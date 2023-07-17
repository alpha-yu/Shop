package module_browse;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import module_trolley.module_trolley;

import java.io.File;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class module_browse extends Application {

    final static Connection[] conn = new Connection[1];
    public static TextField textField = new TextField();
    public static List<Good> goodList = new ArrayList<Good>();
    public static HashSet<String> cateSet = new HashSet<String>();
    public static String cateString = new String("");
    public static GridPane rightGridPane = new GridPane();
    public static int goodStore = 0;
    static List<Good> trolleyGoods = new ArrayList<>();

    public module_browse() { }

    public module_browse(List<Good> trolleyGoods) {
        module_browse.trolleyGoods = trolleyGoods;
    }

    public static void cateSetOutput(VBox leftVBox, HashSet<String> cateSet) {
        for (String value : cateSet) {
            Separator separator = new Separator();
            Button allButton = new Button(value.replaceAll("", "  "));
            allButton.setFont(new Font("华文楷体", 20));
            allButton.setStyle("-fx-background-color: transparent;-fx-border-color: transparent; -fx-text-fill: black;");

            //当鼠标停留在按钮上时，颜色变深
            allButton.setOnMouseEntered(event -> {
                allButton.setStyle("-fx-background-color: transparent;-fx-border-color: transparent; -fx-text-fill: #007bff;");
            });
            allButton.setOnMouseExited(event -> {
                allButton.setStyle("-fx-background-color: transparent;-fx-border-color: transparent; -fx-text-fill: black;");
            });
            allButton.setOnAction(actionEvent -> {
//                System.out.println(allButton.getText().replaceAll("  ",""));
                allButtonEvent(allButton.getText().replaceAll("  ", ""));
            });
            leftVBox.getChildren().addAll(allButton, separator);
        }
    }

    public static void allButtonEvent(String s) {
//        System.out.println("s="+s);
        rightGridPane.getChildren().clear();
        goodList.clear();
        cateString = s;
//        System.out.println("cateString="+cateString);
        goodList = JDBC(2);
        GoodsOutput(goodList, rightGridPane);

    }

    public static HashSet<String> getCateSet(List<Good> goodList) {
        HashSet<String> cateSet = new HashSet<>();
        for (int i = 0; i < goodList.size(); i++) {
            cateSet.add(goodList.get(i).getCATEG());
        }
        return cateSet;
    }

    //显示GoodsList中所有的商品信息
    public static void GoodsOutput(List<Good> goodList, GridPane gridPane) {
        for (int i = 0; i < goodList.size(); i++) {
            goodList.set(i, GoodsInfo(gridPane, goodList.get(i), i % 2, i / 2));
        }
    }


//    public static int getGoodStore()
//    {
//
//    }


    public static Good GoodsInfo(GridPane rightGirdPane, Good good, int x, int y) {

        HBox productBox = new HBox(2);
        productBox.setMinSize(280, 160);
        productBox.setMaxSize(280, 160);
        productBox.setPrefSize(280, 160);
//        productBox.setSpacing(0);
        File file = new File("src/main/resources/module_browse/images/" + good.getGno() + ".jpg");
        ImageView productImage;
        if (file.exists()) {
            productImage = new ImageView(new Image("file:src/main/resources/module_browse/images/" + good.getGno() + ".jpg"));
        } else {
            productImage = new ImageView(new Image("file:src/main/resources/module_browse/images/unknown.jpg"));
        }
        productImage.setFitWidth(119);
        productImage.setFitHeight(104);

        // 创建矩形剪辑区域，设置圆角属性
        Rectangle clip = new Rectangle(productImage.getFitWidth(), productImage.getFitHeight());
        clip.setArcWidth(20);
        clip.setArcHeight(20);

        // 将剪辑区域应用于 ImageView
        productImage.setClip(clip);

        VBox productDetails = new VBox(4);
        productDetails.setAlignment(Pos.CENTER_LEFT);
        productDetails.setPrefSize(152, 100);
        Button productName = new Button(good.getGname());
        productName.setStyle("-fx-background-color: transparent;-fx-border-color: transparent; -fx-text-fill: black;-fx-underline: false;-fx-padding: 0;");

        //当鼠标停留在按钮上时，颜色变深
        productName.setOnMouseEntered(event -> {

            productName.setStyle("-fx-background-color: transparent;-fx-border-color: transparent; -fx-text-fill: #0056b3;-fx-underline: true;-fx-padding: 0;");
        });
        productName.setOnMouseExited(event -> {
            productName.setStyle("-fx-background-color: transparent;-fx-border-color: transparent; -fx-text-fill: black;-fx-underline: false;-fx-padding: 0;");
        });

        productName.setFont(new Font("华文楷体", 25));

        HBox priceAndUnit = new HBox();
        priceAndUnit.setPrefSize(151, 120);
        HBox priceBox = new HBox();
        priceBox.setAlignment(Pos.CENTER);
        DecimalFormat decimalFormat = new DecimalFormat("0.#");
        String result = decimalFormat.format(good.getGprice());
        Label priceLabel = new Label("￥" + result);
        priceLabel.setFont(new Font("Cambria Math", 17));

        Label unitLabel = new Label("\n/件");
        unitLabel.setFont(new Font("华文中宋", 11));

        priceBox.setAlignment(Pos.CENTER);
        priceBox.getChildren().addAll(priceLabel, unitLabel);
        HBox quantityBox = new HBox();
        quantityBox.setAlignment(Pos.CENTER);
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


        int gn = good.getNum();
        int index = -1;

        // 遍历列表查找指定 Gname
        for (int i = 0; i < trolleyGoods.size(); i++) {
            if (trolleyGoods.get(i).getGname().equals(good.getGname())) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            gn = 0;
        } else {
            gn = trolleyGoods.get(index).getNum();
        }
        Label quantityLabel = new Label(" " + gn + " ");
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
            if (Integer.parseInt(quantityLabel.getText().trim()) > 0) {
                quantityLabel.setText(" " + (Integer.parseInt(quantityLabel.getText().trim()) - 1) + " ");
                System.out.println(quantityLabel.getText());
//                goodNum=Integer.parseInt(quantityLabel.getText().trim());
                good.setNum(Integer.parseInt(quantityLabel.getText().trim()));
                System.out.println("trolleyGood是否包含" + good.getGname() + ":" + trolleyGoods.contains(good));
                if (trolleyGoods.contains(good) && good.getNum() == 0) {
                    trolleyGoods.remove(good);
                }
                for (int i = 0; i < trolleyGoods.size(); i++) {
                    System.out.println(trolleyGoods.get(i).getGno());
                }
            }
        });

        plusButton.setOnAction(e -> {
            quantityLabel.setText(" " + (Integer.parseInt(quantityLabel.getText().trim()) + 1) + " ");
            System.out.println(quantityLabel.getText());
//            goodNum=Integer.parseInt(quantityLabel.getText().trim());
            good.setNum(Integer.parseInt(quantityLabel.getText().trim()));
            System.out.println("trolleyGood是否包含" + good.getGname() + ":" + trolleyGoods.contains(good));
            if (!trolleyGoods.contains(good)) {
                trolleyGoods.add(good);
            }
            for (int i = 0; i < trolleyGoods.size(); i++) {
                System.out.println(trolleyGoods.get(i).getGno());
            }
        });


        quantityBox.setSpacing(4);
        quantityBox.getChildren().addAll(minusButton, quantityLabel, plusButton);
        priceAndUnit.setPrefSize(100, 50);
        priceAndUnit.getChildren().addAll(priceBox, quantityBox);
        priceAndUnit.setSpacing(19);
        productDetails.setAlignment(Pos.CENTER_LEFT);
        productDetails.getChildren().addAll(productName, priceAndUnit);
        productBox.getChildren().addAll(productImage, productDetails);
        productBox.setAlignment(Pos.CENTER);
        productBox.setStyle("-fx-background-color: transparent; -fx-border-color: #c0c4c3; -fx-border-radius: 25px; -fx-background-radius: 25px;");

        rightGirdPane.add(productBox, x, y);
        return good;
    }

    public static List<Good> JDBC(int flag) {
        List<Good> goodList = new ArrayList<>();
        // 获取数据库连接
        String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=Shop";
        String userName = "sa";
        String userPwd = "123456";
        try {
            conn[0] = DriverManager.getConnection(dbURL, userName, userPwd);
            // 从数据库中获取数据
            Statement stmt = conn[0].createStatement();
            if (flag == 0)//全部
            {
                goodList = AllGoods(stmt);
            }
            if (flag == 1)//搜索
            {
                goodList = Search(stmt, textField.getText());
            }
            if (flag == 2)//分类
            {
                goodList = CateGoods(stmt, cateString);
            }
            if (flag == 3)//库存量
            {

            }


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库连接失败");
        }
        return goodList;
    }

    public static List<Good> CateGoods(Statement stmt, String s) {
        List<Good> goodList = new ArrayList<Good>();
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("select * from Good where CATEG=\'" + s + "\'");
            System.out.println("select * from Good where CATEG=\'" + s + "\'");
            while (rs.next()) {
                System.out.println(rs.getString("Gno") + "\t" + rs.getString("Gname") + "\t" + rs.getString("Gprice") + "\t" + rs.getString("Ginfo") + "\t" + rs.getString("MATL") + "\t" + rs.getString("CATEG") + "\t" + rs.getString("EXPdate"));
                goodList.add(new Good(rs.getString("Gno"), rs.getString("Gname"), rs.getDouble("Gprice"), rs.getString("Ginfo"), rs.getString("MATL"), rs.getString("CATEG"), rs.getString("EXPdate")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return goodList;
    }

    public static List<Good> AllGoods(Statement stmt) {
        rightGridPane.getChildren().clear();
        List<Good> goodList = new ArrayList<Good>();
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("select * from Good");
            while (rs.next()) {
                //                 student = new StudentOutput(rs.getInt("sno"),rs.getString("sname"), rs.getInt("sage"), rs.getString("sex"), rs.getString("dname"),rs.getString("cnmae"),rs.getDouble("score"));
                System.out.println(rs.getString("Gno") + "\t" + rs.getString("Gname") + "\t" + rs.getString("Gprice") + "\t" + rs.getString("Ginfo") + "\t" + rs.getString("MATL") + "\t" + rs.getString("CATEG") + "\t" + rs.getString("EXPdate"));
                goodList.add(new Good(rs.getString("Gno"), rs.getString("Gname"), rs.getDouble("Gprice"), rs.getString("Ginfo"), rs.getString("MATL"), rs.getString("CATEG"), rs.getString("EXPdate")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return goodList;
    }

    public static List<Good> Search(Statement stmt, String s) {
        List<Good> goodList = new ArrayList<Good>();
        if (s != "") {

            ResultSet rs = null;

            try {
                rs = stmt.executeQuery("select * from Good where Gname LIKE \'%" + s + "%\'");
                if (rs.next()) {
                    do {
                        //                 student = new StudentOutput(rs.getInt("sno"),rs.getString("sname"), rs.getInt("sage"), rs.getString("sex"), rs.getString("dname"),rs.getString("cnmae"),rs.getDouble("score"));
                        System.out.println(rs.getString("Gno") + "\t" + rs.getString("Gname") + "\t" + rs.getString("Gprice") + "\t" + rs.getString("Ginfo") + "\t" + rs.getString("MATL") + "\t" + rs.getString("CATEG") + "\t" + rs.getString("EXPdate"));
                        goodList.add(new Good(rs.getString("Gno"), rs.getString("Gname"), rs.getDouble("Gprice"), rs.getString("Ginfo"), rs.getString("MATL"), rs.getString("CATEG"), rs.getString("EXPdate")));
                    } while (rs.next());
                } else {
                    HBox hBox = new HBox();
                    rightGridPane.getChildren().clear();
                    rightGridPane.add(hBox, 0, 0);
                    Label label = new Label("未搜索到相关商品！");
                    label.setFont(new Font("华文中宋", 15));
                    hBox.setAlignment(Pos.CENTER);
                    hBox.getChildren().add(label);
                    System.out.println("未搜索到相关商品");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("未输入商品名称！");
        }
        return goodList;
    }

//    public static void main(String[] args) {
//        launch(args);
//    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        GridPane root = new GridPane();
        root.setPrefSize(800, 600);
        root.setHgap(18);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(10);
        col1.setPrefWidth(157);
        col1.setMaxWidth(195);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setMinWidth(10);
        col2.setPrefWidth(243);
        col2.setMaxWidth(243);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPrefWidth(100);
        col3.setHgrow(Priority.SOMETIMES);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPrefWidth(100);
        col4.setHgrow(Priority.SOMETIMES);
        root.getColumnConstraints().addAll(col1, col2, col3, col4);

        RowConstraints row1 = new RowConstraints();
        row1.setPrefHeight(91);
        row1.setVgrow(Priority.SOMETIMES);
        RowConstraints row2 = new RowConstraints();
        row2.setPrefHeight(30);
        row2.setVgrow(Priority.SOMETIMES);
        RowConstraints row3 = new RowConstraints();
        row3.setPrefHeight(30);
        row3.setVgrow(Priority.SOMETIMES);
        RowConstraints row4 = new RowConstraints();
        row4.setPrefHeight(30);
        row4.setVgrow(Priority.SOMETIMES);
        RowConstraints row5 = new RowConstraints();
        row5.setPrefHeight(30);
        row5.setVgrow(Priority.SOMETIMES);
        RowConstraints row6 = new RowConstraints();
        row6.setPrefHeight(30);
        row6.setVgrow(Priority.SOMETIMES);
        RowConstraints row7 = new RowConstraints();
        row7.setPrefHeight(30);
        row7.setVgrow(Priority.SOMETIMES);
        RowConstraints row8 = new RowConstraints();
        row8.setPrefHeight(30);
        row8.setVgrow(Priority.SOMETIMES);
        RowConstraints row9 = new RowConstraints();
        row9.setPrefHeight(30);
        row9.setVgrow(Priority.SOMETIMES);
        RowConstraints row10 = new RowConstraints();
        row10.setPrefHeight(30);
        row10.setVgrow(Priority.SOMETIMES);
        RowConstraints row11 = new RowConstraints();
        row11.setPrefHeight(30);
        row11.setVgrow(Priority.SOMETIMES);
        root.getRowConstraints().addAll(row1, row2, row3, row4, row5, row6, row7, row8, row9, row10, row11);

        Pane searchPane = new Pane();
        searchPane.toFront();
        textField.toBack();
        searchPane.setPrefSize(800, 200);

        textField.setLayoutX(27);
        textField.setLayoutY(25);
        textField.setPrefSize(566, 36);
        textField.setPromptText("请输入商品名称");
        Button searchButton = new Button("搜索");
        searchButton.toFront();
        searchButton.setOnAction(actionEvent -> {
            rightGridPane.getChildren().clear();
            goodList.clear();
            goodList = JDBC(1);
            GoodsOutput(goodList, rightGridPane);
        });
        searchButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        //当鼠标停留在按钮上时，颜色变深
        searchButton.setOnMouseEntered(event -> {
            searchButton.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white;");
        });
        searchButton.setOnMouseExited(event -> {
            searchButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        });
        searchButton.setLayoutX(109);
        searchButton.setLayoutY(31);
        searchButton.setPrefSize(40, 23);
        searchPane.getChildren().addAll(textField);
        GridPane.setColumnSpan(searchPane, 3);

//        ImageView serachImageView=new ImageView(new Image("file:C:\\Users\\XX\\Downloads\\搜索.png"));
//        searchButton.setGraphic(serachImageView);
//        serachImageView.setFitHeight(22);serachImageView.setFitWidth(22);
//        searchButton.setStyle("-fx-background-color: transparent;\n" +
//                "    -fx-padding: 0;\n" +
//                "    -fx-background-insets: 0;\n" +
//                "    -fx-background-radius: 0;\n" +
//                "    -fx-border-width: 0;\n" +
//                "    -fx-focus-traversable: false;");

        Pane buttonPane = new Pane();
        buttonPane.setPrefSize(100, 200);
        Button cartButton = new Button("购物车");
        cartButton.setFont(new Font(13));
        Button myButton = new Button("我的");
        myButton.setFont(new Font(13));

        cartButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        //当鼠标停留在按钮上时，颜色变深
        cartButton.setOnMouseEntered(event -> {
            cartButton.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white;");
        });
        cartButton.setOnMouseExited(event -> {
            cartButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        });
        cartButton.setOnAction(actionEvent -> {
            primaryStage.close();
            module_trolley trolley = new module_trolley(trolleyGoods);
            trolley.start(primaryStage);
        });

        myButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        //当鼠标停留在按钮上时，颜色变深
        myButton.setOnMouseEntered(event -> {
            myButton.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white;");
        });
        myButton.setOnMouseExited(event -> {
            myButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        });

        myButton.setLayoutX(270);
        myButton.setLayoutY(30);
//        ImageView cartImageView=new ImageView(new Image("file:C:\\Users\\XX\\Downloads\\购物车.png"));
//        cartButton.setGraphic(cartImageView);
//        cartImageView.setFitHeight(30);cartImageView.setFitWidth(30);
//        cartButton.setStyle("-fx-background-color: transparent;\n" +
//                "    -fx-padding: 0;\n" +
//                "    -fx-background-insets: 0;\n" +
//                "    -fx-background-radius: 0;\n" +
//                "    -fx-border-width: 0;\n" +
//                "    -fx-focus-traversable: false;");


//        ImageView myImageView=new ImageView(new Image("file:C:\\Users\\XX\\Downloads\\我的 (2).png"));
//        myButton.setGraphic(myImageView);
//        myImageView.setFitHeight(30);myImageView.setFitWidth(30);
//        myButton.setStyle("-fx-background-color: transparent;\n" +
//                "    -fx-padding: 0;\n" +
//                "    -fx-background-insets: 0;\n" +
//                "    -fx-background-radius: 0;\n" +
//                "    -fx-border-width: 0;\n" +
//                "    -fx-focus-traversable: false;");


        cartButton.setLayoutX(195);
        cartButton.setLayoutY(30);
        buttonPane.getChildren().addAll(searchButton, cartButton, myButton);
        GridPane.setColumnIndex(buttonPane, 2);

        ScrollPane leftScrollPane = new ScrollPane();
        leftScrollPane.setPrefSize(200, 200);
        VBox leftVBox = new VBox(6);
        leftVBox.setAlignment(Pos.TOP_CENTER);
        leftVBox.setPrefWidth(140);
        leftVBox.setTranslateY(10);
        Label categoryLabel = new Label("分   类");


        Separator separator1 = new Separator();
        Button allButton = new Button("全  部");
        allButton.setFont(new Font("华文楷体", 20));
        allButton.setStyle("-fx-background-color: transparent;-fx-border-color: transparent; -fx-text-fill: black;");

        //当鼠标停留在按钮上时，颜色变深
        allButton.setOnMouseEntered(event -> {
            allButton.setStyle("-fx-background-color: transparent;-fx-border-color: transparent; -fx-text-fill: #007bff;");
        });
        allButton.setOnMouseExited(event -> {
            allButton.setStyle("-fx-background-color: transparent;-fx-border-color: transparent; -fx-text-fill: black;");
        });


        Separator separator2 = new Separator();


        categoryLabel.setFont(new Font("华文中宋", 21));
        myButton.setOnMouseEntered(event -> {
            myButton.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white;");
        });
        myButton.setOnMouseExited(event -> {
            myButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        });

        leftVBox.getChildren().addAll(categoryLabel, separator1, allButton, separator2);
        leftScrollPane.setContent(leftVBox);
        leftVBox.setStyle("-fx-background-color: transparent;-fx-border-color: transparent; -fx-border-radius: 25px; -fx-background-radius: 25px;");
        GridPane.setRowIndex(leftScrollPane, 1);
        GridPane.setRowSpan(leftScrollPane, 10);

        ScrollPane rightScrollPane = new ScrollPane();

        rightScrollPane.setPrefSize(200, 200);

//        rightGridPane.setGridLinesVisible(true);
        rightGridPane.setPrefSize(590, 508);

        rightGridPane.setStyle("-fx-background-color: transparent;");


//————————————————————————————————————以下是动产分割线————————————————————————————————————————————————————


//        GoodsInfo(rightGridPane,new Good("0006","九阳电饭煲",280,"目前是空","合金","家电","NULL"),0,0);
//
//        GoodsInfo(rightGridPane,new Good("0006","九阳电饭煲",280,"目前是空","合金","家电","NULL"),1,0);
//        GoodsInfo(rightGridPane,new Good("0006","九阳电饭煲",280,"目前是空","合金","家电","NULL"),0,1);
//        GoodsInfo(rightGridPane,new Good("0006","九阳电饭煲",280,"目前是空","合金","家电","NULL"),1,1);
//        GoodsInfo(rightGridPane,new Good("0006","九阳电饭煲",280,"目前是空","合金","家电","NULL"),0,2);
//        GoodsInfo(rightGridPane,new Good("0006","九阳电饭煲",280,"目前是空","合金","家电","NULL"),1,2);
//        GoodsInfo(rightGridPane,new Good("0006","九阳电饭煲",280,"目前是空","合金","家电","NULL"),0,3);
//        GoodsInfo(rightGridPane,new Good("0006","九阳电饭煲",280,"目前是空","合金","家电","NULL"),1,3);

        goodList = JDBC(0);
        GoodsOutput(goodList, rightGridPane);

        cateSet = getCateSet(goodList);
        cateSetOutput(leftVBox, cateSet);

        allButton.setOnAction(actionEvent -> {
            goodList.clear();
            goodList = JDBC(0);
            GoodsOutput(goodList, rightGridPane);
        });


//        HBox hBox=new HBox();
//        hBox.setStyle("-fx-background-color: transparent; -fx-border-color: #c0c4c3; -fx-border-radius: 25px; -fx-background-radius: 25px;");
//        rightGridPane.add(hBox, 1, 0);
//        HBox hBox1=new HBox();
//        hBox1.setStyle("-fx-background-color: transparent; -fx-border-color: #c0c4c3; -fx-border-radius: 25px; -fx-background-radius: 25px;");
//        rightGridPane.add(hBox1, 0, 1);
//        HBox hBox2=new HBox();
//        hBox2.setStyle("-fx-background-color: transparent; -fx-border-color: #c0c4c3; -fx-border-radius: 25px; -fx-background-radius: 25px;");
//        rightGridPane.add(hBox2, 1, 1);

        rightGridPane.setHgap(13);
        rightGridPane.setVgap(20);


        rightScrollPane.setContent(rightGridPane);
        GridPane.setColumnIndex(rightScrollPane, 1);
        GridPane.setColumnSpan(rightScrollPane, 3);
        GridPane.setRowIndex(rightScrollPane, 1);
        GridPane.setRowSpan(rightScrollPane, 10);
        rightScrollPane.setStyle("-fx-background-color: transparent;-fx-border-color: transparent; -fx-border-radius: 25px; -fx-background-radius: 25px;");
        leftScrollPane.setStyle("-fx-background-color: transparent;-fx-border-color: transparent; -fx-border-radius: 25px; -fx-background-radius: 25px;");

        root.getChildren().addAll(searchPane, buttonPane, leftScrollPane, rightScrollPane);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}