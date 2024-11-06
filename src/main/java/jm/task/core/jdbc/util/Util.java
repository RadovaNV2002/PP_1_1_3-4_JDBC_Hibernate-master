package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_HOST = "jdbc:mysql://localhost:3306/test";
    private static final String DB_LOGIN = "root";
    private static final String DB_PASSWORD = "1234";

    public static Connection getConnection() throws ClassNotFoundException {
        Connection connection;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_HOST, DB_LOGIN, DB_PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ERROR");
            throw new RuntimeException(e);
        }
        return connection;
    }

}
