package models;
import java.sql.*;
public class ConnectionClass {
public Connection connection;
    public Connection getConnection()
    {
        String username = "fp510";
        String password = "510";

        try {
        	Class.forName("com.mysql.jdbc.Driver");
        	String DB_URL = "jdbc:mysql://www.papademas.net:3307/510fp?autoReconnect=true&useSSL=false";
        	
        	connection = DriverManager.getConnection(DB_URL,username,password);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to database", e);
        }
        catch(ClassNotFoundException e) {
            System.out.println("null");
        }
        return connection;
    }
}
