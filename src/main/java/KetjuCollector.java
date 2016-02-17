
import java.sql.ResultSet;


public class KetjuCollector implements Collector {

    @Override
    public Ketju collect(ResultSet rs) {
        try {
        int id = rs.getInt("KetjuId");
        String nimi = rs.getString("Nimi");
        return new Ketju(id, nimi);
        
        } catch (Exception e) {
            System.out.println("Tuli virhe KetjuCollector:collect viestillä:\n" + e.getMessage());
            return null;
        }
    }
    
}
