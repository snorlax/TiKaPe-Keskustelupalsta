
import java.sql.ResultSet;


public class AlueCollector implements Collector {

    @Override
    public String collect(ResultSet rs) {
        try {
        return rs.getString("Nimi");
        
        } catch (Exception e) {
            System.out.println("Tuli virhe AlueCollector:collect viestillä:\n" + e.getMessage());
            return null;
        }
    }
    
}
