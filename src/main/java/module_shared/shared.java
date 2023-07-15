package module_shared;

import java.sql.Connection;

public class shared {
    public static String dbURL = "jdbc:sqlserver://localhost;DatabaseName=Shop";
    public static Connection dbConn = null;
    public static String userStr = "sa";
    public static String passwordStr = "123456";
}