
import java.sql.ResultSet;
import java.sql.Timestamp;


public class ViestiCollector implements Collector {

    @Override
    public Viesti collect(ResultSet rs) {
        try {
        int id = rs.getInt("ViestiId");
        String nickname = rs.getString("Nickname");
        String otsikko = rs.getString("Otsikko");
        String sisalto = rs.getString("sisalto");
        String aika = rs.getString("Aika");
        boolean onkoAvausviesti = rs.getBoolean("Avausviesti");
        return new Viesti(id, nickname, otsikko, sisalto, aika, onkoAvausviesti);
        
        } catch (Exception e) {
            System.out.println("Tuli virhe ViestiCollector:collect viestillä:\n" + e.getMessage());
            return null;
        }
    }
    
}
