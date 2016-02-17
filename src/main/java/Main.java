
import java.sql.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:TikapeProjektiv0.db");

        Statement stmt = connection.createStatement();

        testaaYhteys(stmt);

        connection.close();
    }
    
    public static void testaaYhteys(Statement stmt) throws Exception {
        ResultSet rs = stmt.executeQuery("SELECT 1;");
        if (rs.next()) {
            System.out.println("connection success");
        }

        stmt.close();
        rs.close();
    }
    
}
