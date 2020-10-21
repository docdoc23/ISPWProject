package main.java.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private String dbURI = "jdbc:postgresql://localhost:5432/FERSA";
    private String user = "postgres";
    private String password = "postgres";

    public DataSource() {
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(this.dbURI, this.user, this.password);
        return connection;
    }
}