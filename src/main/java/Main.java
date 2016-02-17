
import java.sql.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        DBContacter c = new DBContacter("jdbc:sqlite:TikapeProjektiv0.db");
        c.testaaYhteys();
        c.foreignKeytPaalle();
        ArrayList<String> alueet = c.queryAndCollect("SELECT * FROM Alue;", new AlueCollector());
        ArrayList<String> ketjut = c.queryAndCollect("SELECT * FROM Ketju;", new KetjuCollector());
        ArrayList<String> viestit = c.queryAndCollect("SELECT * FROM Viesti;", new ViestiCollector());
        System.out.println("\n" + alueet);
        System.out.println(ketjut);
        System.out.println(viestit);
        c.suljeYhteys();
    }

}
