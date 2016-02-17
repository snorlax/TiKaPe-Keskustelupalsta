
import java.sql.*;

public class Main {

    public static void main(String[] args) {
        DBContacter c = new DBContacter("jdbc:sqlite:TikapeProjektiv0.db");
        c.testaaYhteys();
        c.suljeYhteys();
    }

}
