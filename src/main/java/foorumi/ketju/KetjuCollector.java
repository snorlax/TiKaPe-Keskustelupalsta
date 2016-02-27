package foorumi.ketju;


import foorumi.Collector;
import java.sql.ResultSet;


public class KetjuCollector implements Collector {

    @Override
    public Ketju collect(ResultSet rs) {
        try {
        int id = rs.getInt("KetjuId");
        String nimi = rs.getString("Nimi");
        int alueId = rs.getInt("AlueId");
        return new Ketju(id, nimi, alueId);
        
        } catch (Exception e) {
            System.out.println("Tuli virhe KetjuCollector:collect viestillä:\n" + e.getMessage());
            return null;
        }
    }
    
}
