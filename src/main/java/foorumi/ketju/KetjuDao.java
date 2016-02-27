package foorumi.ketju;


import foorumi.DBContacter;
import java.util.ArrayList;


public class KetjuDao {
    private DBContacter c;

    public KetjuDao(DBContacter c) {
        this.c = c;
    }
    
    // määrät saa allaolevista helposti size()-metodilla
    public ArrayList<Ketju> kaikkiKetjut() {
        return c.queryAndCollect("SELECT * FROM Ketju", new KetjuCollector());
    }
    public ArrayList<Ketju> ketjutAlueelta(int alueId) {
        return c.queryAndCollect("SELECT * FROM Ketju WHERE alueID = " + alueId, new KetjuCollector());
    }
    public boolean lisaaKetju(int alueId, String nimi) {
        //testejä stringille/alueidlle?
        return c.update("INSERT INTO Ketju (nimi, alueId) VALUES"
                + " (" + nimi + ", " + alueId + ")");
    }
}
