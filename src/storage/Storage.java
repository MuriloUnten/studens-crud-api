package src.storage;

import java.sql.*;
import java.util.Properties;


public class Storage {
    private static final int DB_PORT = 5432;
    private static final String DB_ADDRESS = "127.0.0.1";
    private static final String DB_NAME = "boletim";

    private Connection connection;
    private String url;
    private Properties properties;

    Storage(String user, String password) {
        url = "jdbc:postgresql://" + DB_ADDRESS +  ":" + DB_PORT + "/" + DB_NAME;
        properties.setProperty("user", user);
        properties.setProperty("password", password);
        connection = DriverManager.getConnection(url, properties);
    }
}
