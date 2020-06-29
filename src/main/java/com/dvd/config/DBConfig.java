package com.dvd.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConfig {
    // Connection reference
    private static Connection connection;

    private DBConfig() {
    }

    public static Connection getInstance() {
        if (connection == null) {
            try {
                // get MySQL DB driver from jar
                Class.forName("com.mysql.cj.jdbc.Driver");
                // get driver connection object and assign to connection reference
                connection = DriverManager.getConnection("jdbc:mysql://localhost/software_deployment?user=30007873&password=30007873&useSSL=false&useUnicode=true&serverTimezone=UTC");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        // return connection
        return connection;
    }
}
