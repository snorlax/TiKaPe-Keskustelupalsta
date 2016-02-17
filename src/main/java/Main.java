
import java.sql.*;

public class Main {

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:TikapeProjektiv0.db");
            Statement stmt = connection.createStatement();
            DBContacter c = new DBContacter(stmt);

            c.testaaYhteys();

            connection.close();
        } catch (Exception e) {
            System.out.println("Tuli virhe Main:main viestillä:\n" + e.getMessage());
        }
    }

}
