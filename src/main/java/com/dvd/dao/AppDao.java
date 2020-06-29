package com.dvd.dao;

import com.dvd.config.DBConfig;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AppDao {

    public static void createDBAndTables() {
        try {
            // get DB connection object
//            Connection connection = DBConfig.getInstance();
            Connection connection = DBConfig.getInstance();
            // get result set catalogs
            ResultSet resultSet = connection.getMetaData().getCatalogs();
            // loop through the result set
            while (resultSet.next()) {
                // get the database name from the result set of catalogs; the position 1 from result set of catalogs has the DB name
                String dbName = resultSet.getString(1);
                // validate database name
                if (dbName.equalsIgnoreCase("software_deployment")) {
                    Statement statement = connection.createStatement();
                    // drop the database if it already exists
                    String query = "DROP SCHEMA IF EXISTS software_deployment";
                    // execute query
                    statement.executeUpdate(query);
                }
            }
            // define create statement or query for DB creation
            BufferedReader reader = new BufferedReader(new FileReader("sql/dvd.sql"));
            ScriptRunner scriptExecutor = new ScriptRunner(connection);
            scriptExecutor.runScript(reader);
        } catch (IOException | SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
