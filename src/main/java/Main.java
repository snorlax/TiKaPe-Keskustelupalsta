
import java.sql.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        DBContacter c = new DBContacter("jdbc:sqlite:Keskustelupalsta.db");
        c.testaaYhteys();
        c.foreignKeytPaalle();
        System.out.println("");
        
//      Viestien lisääminen nykymallin mukaan:
//        ViestiDao vd = new ViestiDao(c);
//        boolean tulos = vd.lisaaVastausviesti(3, "testiketjun kolme avausviesti", "henri P");
//        if (tulos) {
//            System.out.println("Viestin lisäys onnistui!\n");
//        } else {
//            System.out.println("Viestin lisäys ei onnistunut :( \n");
//        }
        
        ArrayList<Alue> alueet = c.queryAndCollect("SELECT * FROM Alue;", new AlueCollector());
        ArrayList<Ketju> ketjut = c.queryAndCollect("SELECT * FROM Ketju;", new KetjuCollector());
        ArrayList<Viesti> viestit = c.queryAndCollect("SELECT * FROM Viesti;", new ViestiCollector());

        System.out.println("Kaikki alueet");
        for (Alue a : alueet) {
            System.out.println(a);
        }

        System.out.println("");

        System.out.println("Kaikki ketjut");
        for (Ketju k : ketjut) {
            System.out.println(k);
        }

        System.out.println("");

        System.out.println("");

        System.out.println("Kaikki viestit:");
        for (Viesti s : viestit) {
            System.out.println(s);
        }
        c.suljeYhteys();
        System.out.println("\n\nYhteys suljettu.");
    }

}
