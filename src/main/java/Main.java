
import java.sql.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        DBContacter c = new DBContacter("jdbc:sqlite:Keskustelupalsta.db");
        c.testaaYhteys();
        c.foreignKeytPaalle(); System.out.println("");
        
        //boolean b = c.update("INSERT INTO Viesti (Nickname, otsikko, sisalto, aika, avausviesti, ketjuId)"
        //        + " values ('testaaja', 'otsikkoni', 'sisaltoni', CURRENT_TIMESTAMP, 0, 1);");
        //System.out.println(b + "\n");
        
        ArrayList<String> alueet = c.queryAndCollect("SELECT * FROM Alue;", new AlueCollector());
        ArrayList<Ketju> ketjut = c.queryAndCollect("SELECT * FROM Ketju;", new KetjuCollector());
        ArrayList<Viesti> viestit = c.queryAndCollect("SELECT * FROM Viesti;", new ViestiCollector());
        
        System.out.println("Kaikki viestit:");
        for (Viesti s : viestit) {
            System.out.println(s);
        }
        c.suljeYhteys();
    }

}
