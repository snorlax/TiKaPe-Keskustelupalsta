import java.sql.*;

public class DBContacter {
    private String databaseName;
    private Statement stmt;
    private Connection connection;

    public DBContacter(String databaseName) {
        this.databaseName = databaseName;
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:TikapeProjektiv0.db");
            this.stmt = connection.createStatement();
        } catch (Exception e) {
            System.out.println("Tuli virhe DBContacter:constructor(String) viestillä:\n" + e.getMessage());
        }
    }
    
    public void testaaYhteys() {
        try {
            ResultSet rs = stmt.executeQuery("SELECT 1;");
        if (rs.next()) {
            System.out.println("Yhteys tietokantaan pelaa! Jes :)");
        }

        stmt.close();
        rs.close();
        
        } catch (Exception e) {
            System.out.println("Tuli virhe DBContacter:testaaYhteys viestillä:\n" + e.getMessage());
        }
    }

    public void suljeYhteys() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Tuli virhe DBContacter:suljeYhteys viestillä:\n" + e.getMessage());
        }
    }
}
