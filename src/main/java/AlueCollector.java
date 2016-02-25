
import java.sql.ResultSet;


public class AlueCollector implements Collector {

    @Override
    public Alue collect(ResultSet rs) {
        try {
        int id = rs.getInt("alueId");
        String nimi = rs.getString("Nimi");
        return new Alue(id, nimi);
        
        } catch (Exception e) {
            System.out.println("Tuli virhe AlueCollector:collect viestillä:\n" + e.getMessage());
            return null;
        }
    }
    
}
