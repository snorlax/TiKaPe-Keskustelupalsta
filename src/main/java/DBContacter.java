import java.sql.*;

public class DBContacter {
    private Statement stmt;

    public DBContacter(Statement stmt) {
        this.stmt = stmt;
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
}
