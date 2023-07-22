package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyDatabase {
    final private String host = DatabaseCredential.DB_HOST.toString();
    final private String username = DatabaseCredential.DB_USERNAME.toString();
    final private String dbname = DatabaseCredential.DB_DATABASE.toString();
    final private String password = DatabaseCredential.DB_PASSWORD.toString();
    final private String port = DatabaseCredential.DB_PORT.toString();
    final private String url;

    public MyDatabase() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.url = "jdbc:mysql://"+host+":3306/"+dbname;
    }

    public String getUrl() {
        return "jdbc:mysql://"+host+":"+port+"/"+dbname;
    }

    public Connection getConnection() throws SQLException {
        return  DriverManager.getConnection(url, username, password);
    }
}
