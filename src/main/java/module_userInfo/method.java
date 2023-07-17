package module_userInfo;

import module_shared.shared;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class method {
    List<MyText> orders;

    //查询并将数据库读入orders
    public void sel(String tableName) {
        orders = new ArrayList<>();
        try {//try catch判断是否有异常
            Statement sqlStatement = shared.dbConn.createStatement();//创建sql语句
            String sql = "select * from " + tableName;
            ResultSet rs = sqlStatement.executeQuery(sql);//执行sql语句

            while (rs.next()) {
                orders.add(new MyText(rs.getString("username"), rs.getString("psw"), rs.getInt("AUTH"), rs.getString("ucif"), rs.getString("Uaddr")));
                System.out.println();//控制格式
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDbURL() {
        return shared.dbURL;
    }

    //查询
    public void select(String username) {
        orders = new ArrayList<>();
        try {
            Statement sqlStatement = shared.dbConn.createStatement();//创建sql语句
            String sql = "select * from Users where username = " + "\'" + username + "\'";
            System.out.println(sql);
            ResultSet rs = sqlStatement.executeQuery(sql);//执行sql语句
            while (rs.next()) {
                orders.add(new MyText(rs.getString("username"), rs.getString("psw"), rs.getInt("AUTH"), rs.getString("ucif"), rs.getString("Uaddr")));
                System.out.println("1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///删除
    public void del(String username) {
        try {
            Statement sqlStatement = shared.dbConn.createStatement();//创建sql语句
            String sql = "delete from Users where username = " + "\'" + username + "\'";
            int rs = sqlStatement.executeUpdate(sql);//执行sql语句
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //增加
    public void add(String username, String psw, int AUTH, String ucif, String Uaddr) {
        try {
            Statement sqlStatement = shared.dbConn.createStatement();//创建sql语句
            String sql = "insert into Users values " + "(" + "\'" + username + "\'," + "\'" + psw + "\'," + AUTH + ",\'" + ucif + "\' ," + "\'" + Uaddr + "\'" + ")";
            int rs = sqlStatement.executeUpdate(sql);//执行sql语句
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //更新
    public void update(String username, String updateUsername, String updatePsw, int updateAUTH, String ucif, String Uaddr) {
        try {
            Statement sqlStatement = shared.dbConn.createStatement();//创建sql语句
            String sql = "update Users set username = " + "\'" + updateUsername + "\' ,psw = " + "\'" + updatePsw + "\' ,AUTH = " + updateAUTH + ",UCIF = " + "\'" + ucif + "\' ," + "Uaddr = " + "\'" + Uaddr + "\' " + "where username = " + "\'" + username + "\'";
            int rs = sqlStatement.executeUpdate(sql);//执行sql语句
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}