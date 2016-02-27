package foorumi.viesti;


import foorumi.Collector;
import java.sql.ResultSet;

public class ViestiCollector implements Collector {

    @Override
    public Viesti collect(ResultSet rs) {
        try {
        int id = rs.getInt("ViestiId");
        String nickname = rs.getString("Nickname");
        String sisalto = rs.getString("sisalto");
        String aika = rs.getString("Aika");
        return new Viesti(id, nickname, sisalto, aika);
        
        } catch (Exception e) {
            System.out.println("Tuli virhe ViestiCollector:collect viestillä:\n" + e.getMessage());
            return null;
        }
    }
    
}
