
import java.sql.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        DBContacter c = new DBContacter("jdbc:sqlite:Keskustelupalsta.db");
        c.testaaYhteys();
        c.foreignKeytPaalle();
        System.out.println("");
          
        c.suljeYhteys();
        System.out.println("\n\nYhteys suljettu.");
    }
    
    public void tulostaKaikkiViestit(DBContacter c) {
        ArrayList<Viesti> viestit = c.queryAndCollect("SELECT * FROM Viesti;", new ViestiCollector());
        System.out.println("Kaikki viestit:");
        for (Viesti s : viestit) {
            System.out.println(s);
        }
    }
    
    public void tulostaKaikkiKetjut(DBContacter c) {
        ArrayList<Ketju> ketjut = c.queryAndCollect("SELECT * FROM Ketju;", new KetjuCollector());
        System.out.println("Kaikki ketjut");
        for (Ketju k : ketjut) {
            System.out.println(k);
        }
    }
    
    public void tulostaKaikkiAlueet(DBContacter c) {
        ArrayList<Alue> alueet = c.queryAndCollect("SELECT * FROM Alue;", new AlueCollector());
        System.out.println("Kaikki alueet");
        for (Alue a : alueet) {
            System.out.println(a);
        }
    }
    
    public void uusiViesti(DBContacter c) {
        ViestiDao vd = new ViestiDao(c);
        boolean tulos = vd.lisaaVastausviesti(3, "testiketjun kolme avausviesti", "henri P");
        if (tulos) {
            System.out.println("Viestin lisäys onnistui!\n");
        } else {
            System.out.println("Viestin lisäys ei onnistunut :( \n");
        }
    }

}
