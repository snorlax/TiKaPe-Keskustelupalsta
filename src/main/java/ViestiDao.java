
import java.util.ArrayList;


public class ViestiDao {
    private DBContacter c;

    public ViestiDao(DBContacter c) {
        this.c = c;
    }
    
    //Alla olevistahan saa helposti viestimäärät ArrayList.size()
    public ArrayList<Viesti> kaikkiViestit() {
        return c.queryAndCollect("SELECT * FROM Viesti;", new ViestiCollector());
    }
    public ArrayList<Viesti> viestitKetjusta(int ketjuId) {
        return c.queryAndCollect("SELECT * FROM Viesti"
                + "WHERE KetjuId = " + ketjuId + ";", new ViestiCollector());
    }
    public ArrayList<Viesti> viestitAlueelta(int alueId) {
        return c.queryAndCollect("SELECT * FROM Viesti, Ketju"
                + "WHERE Vieti.KetjuId = Ketju.ketjuId AND Ketju.AlueId = "
                + alueId + ";", new ViestiCollector());
    }
    
    public boolean lisaaVastausviesti(int ketjuId, String otsikko,
            String sisalto, String kayttajatunnus) {
        //tähän testejä stringeille, että ovat kelvollisia?
        //ja palautettaisiin false, jos vääränlaisia. Vai aiemmin jo testit?
        
        return c.update("INSERT INTO Viesti (Otsikko, Sisalto, Nickname, aika, avausviesti)"
                + " VALUES (" + otsikko + ", " + sisalto + ", " + kayttajatunnus + ", "
                + "CURRENT_TIMESTAMP, 0);");
    }
}