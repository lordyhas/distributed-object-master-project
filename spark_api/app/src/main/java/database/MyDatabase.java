package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyDatabase {

    final private String host = DatabaseCredential.DB_HOST.name();
    final private String username = DatabaseCredential.DB_USERNAME.name();
    final private String dbname = DatabaseCredential.DB_DATABASE.name();
    final private String password = DatabaseCredential.DB_PASSWORD.name();

    final private String url;

    public MyDatabase() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        this.url = "jdbc:mysql://"+host+":3306/"+dbname;
    }

    public String getUrl() {
        return "jdbc:mysql://"+host+":3306/"+dbname;
    }

    public Connection getConnection() throws SQLException {
        return  DriverManager.getConnection(url, username, password);
    }

    public static void dbTest () throws ClassNotFoundException, SQLException {

        String host = DatabaseCredential.DB_HOST.getStatus();
        String username = DatabaseCredential.DB_USERNAME.getStatus();
        String dbname = DatabaseCredential.DB_DATABASE.getStatus();
        String password = DatabaseCredential.DB_PASSWORD.getStatus();

        Class.forName("com.mysql.cj.jdbc.Driver");

        //String url = "jdbc:mysql://hassankajila.com:3306/u601424401_on_web";
        //Connection conn = DriverManager.getConnection(url, "u601424401_lordyhas", "lordyhas+5)0zTwDy*e");


        String url = "jdbc:mysql://"+host+":3306/"+dbname;
        Connection conn = DriverManager.getConnection(url, username, password);

        ResultSet rs = conn.prepareStatement("show tables").executeQuery();

        while(rs.next()){
            String s = rs.getString(1);
            System.out.println(s);
        }

        conn.close();
    }
}
