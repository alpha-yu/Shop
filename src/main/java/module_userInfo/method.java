package module_userInfo;

import module_shared.shared;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class method {
    List<MyText> orders;

    //查询并将数据库读入orders
    public void sel() {
        orders = new ArrayList<>();
        try {//try catch判断是否有异常
            Statement sqlStatement = shared.dbConn.createStatement();//创建sql语句
            String sql = "select * from Users";
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
            String sql = "SELECT * FROM Users WHERE username = ?";
            PreparedStatement pstmt = shared.dbConn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
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
            String sql = "DELETE FROM Users WHERE username = ?";
            PreparedStatement pstmt = shared.dbConn.prepareStatement(sql);
            pstmt.setString(1, username);
            int rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //增加
    public void add(String username, String psw, int AUTH, String ucif, String Uaddr) {
        try {
            String sql = "INSERT INTO Users (username, psw, AUTH, UCIF, Uaddr) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = shared.dbConn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, psw);
            pstmt.setInt(3, AUTH);
            pstmt.setString(4, ucif);
            pstmt.setString(5, Uaddr);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //更新
    public void update(String username, String updateUsername, String updatePsw, int updateAUTH, String ucif, String Uaddr) {
        try {
            String sql = "UPDATE Users SET username = ?, psw = ?, AUTH = ?, UCIF = ?, Uaddr = ? WHERE username = ?";
            PreparedStatement pstmt = shared.dbConn.prepareStatement(sql);
            pstmt.setString(1, updateUsername);
            pstmt.setString(2, updatePsw);
            pstmt.setInt(3, updateAUTH);
            pstmt.setString(4, ucif);
            pstmt.setString(5, Uaddr);
            pstmt.setString(6, username);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}