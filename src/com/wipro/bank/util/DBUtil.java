package com.wipro.bank.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

    private static Connection connection = null;

    public static Connection getDBConnection() {
        if (connection == null) {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                connection = DriverManager.getConnection(
                        "jdbc:oracle:thin:@localhost:1521:xe",
                        "system",
                        "2005"
                );
                System.out.println("Oracle connected to eclipse");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
