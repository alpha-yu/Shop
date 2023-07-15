package module_shared;

import java.sql.Connection;

public class shared {
    public static String dbURL = "jdbc:sqlserver://localhost;DatabaseName=";
    public static Connection dbConn = null;
    public static String userStr = null;
    public static String passwordStr = null;
}