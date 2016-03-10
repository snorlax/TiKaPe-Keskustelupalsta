package foorumi;

import java.net.URI;
import java.sql.*;
import java.util.*;

public class DBContacter {
    private String databaseAddress;

    public DBContacter(String databaseName) {
        this.databaseAddress = databaseName;
        
        init();
    }
    
    public void testaaYhteys() {
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT 1;");
        if (rs.next()) {
            System.out.println("Yhteys tietokantaan pelaa! Jes :)");
        }

        stmt.close();
        rs.close();
        
        } catch (Exception e) {
            System.out.println("Tuli virhe DBContacter:testaaYhteys viestillä:\n" + e.getMessage());
        }
    }
    public void foreignKeytPaalle() {
        try {
            Statement stmt = getConnection().createStatement();
            stmt.execute("PRAGMA foreign_keys = ON;");
            stmt.close();
            System.out.println("Foreign key -checkit päällä!");
        
        } catch (Exception e) {
            System.out.println("Tuli virhe DBContacter:testaaYhteys viestillä:\n" + e.getMessage());
        }
    }
    

    public void suljeYhteys() {
        try {
            getConnection().close();
        } catch (Exception e) {
            System.out.println("Tuli virhe DBContacter:suljeYhteys viestillä:\n" + e.getMessage());
        }
    }

    private void init() {
        List<String> lauseet = null;
        if (this.databaseAddress.contains("postgres")) {
            lauseet = postgreLauseet();
        } else {
            lauseet = sqliteLauseet();
        }
        

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    public Connection getConnection() throws SQLException {
        if (this.databaseAddress.contains("postgres")) {
            try {
                URI dbUri = new URI(databaseAddress);

                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

                return DriverManager.getConnection(dbUrl, username, password);
            } catch (Throwable t) {
                System.out.println("Error: " + t.getMessage());
                t.printStackTrace();
            }
        }

        return DriverManager.getConnection(databaseAddress);
    }

    private List<String> postgreLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        // heroku käyttää SERIAL-avainsanaa uuden tunnuksen automaattiseen luomiseen
        lista.add("CREATE TABLE Alue (AlueId SERIAL PRIMARY KEY, Nimi varchar(50) NOT NULL);");
        lista.add("CREATE TABLE Ketju (KetjuId SERIAL PRIMARY KEY, Nimi varchar(1000) NOT NULL,"
                + " AlueId integer NOT NULL, FOREIGN KEY(AlueId) REFERENCES Alue(AlueId));");
        lista.add("CREATE TABLE Viesti (ViestiId SERIAL PRIMARY KEY, Nickname varchar(50) NOT NULL,"
                + " Sisalto varchar(3000) NOT NULL,"
                + " Aika timestamp NOT NULL, KetjuId integer NOT NULL,"
                + " FOREIGN KEY(KetjuId) REFERENCES Ketju(KetjuId));");
        //alueet
        lista.add("INSERT INTO Alue (Nimi) VALUES ('Ohjelmointi');");
        lista.add("INSERT INTO Alue (Nimi) VALUES ('Myydään/Ostetaan -alue');");
        lista.add("INSERT INTO Alue (Nimi) VALUES ('Laskarivinkit');");
        lista.add("INSERT INTO Alue (Nimi) VALUES ('Ruoka/Juoma');");
        
        //ketjut + avausviestit
        lista.add("INSERT INTO Ketju (Nimi, AlueId) VALUES ('Java', 1);");
        lista.add("INSERT INTO Viesti (Nickname, Sisalto, Aika, KetjuId) VALUES"
                + " ('Käpistelijä #1', 'Java on ihan taivaallinen kieli kyllä minä sanon!', "
                + "CURRENT_TIMESTAMP, 1);");
        lista.add("INSERT INTO Ketju (Nimi, AlueId) VALUES ('Python', 1);");
        lista.add("INSERT INTO Viesti (Nickname, Sisalto, Aika, KetjuId) VALUES"
                + " ('Käpistelijä #1', 'Pythonia kukaan käytä...turha ketju :(', "
                + "CURRENT_TIMESTAMP, 2);");
        
        lista.add("INSERT INTO Ketju (Nimi, AlueId) VALUES ('Antiikkia, antiikkia', 2);");
        lista.add("INSERT INTO Viesti (Nickname, Sisalto, Aika, KetjuId) VALUES"
                + " ('Antiikki-pappa', 'Miulta sais vintiltä ostaa pois kaikenlaista', "
                + "CURRENT_TIMESTAMP, 3);");
        lista.add("INSERT INTO Ketju (Nimi, AlueId) VALUES ('Rihkamaa', 2);");
        lista.add("INSERT INTO Viesti (Nickname, Sisalto, Aika, KetjuId) VALUES"
                + " ('Antiikki-pappa', 'Myös takapihalla on roinaa joka lähtöön ketä vaan kiinnostaa :D', "
                + "CURRENT_TIMESTAMP, 4);");
        lista.add("INSERT INTO Ketju (Nimi, AlueId) VALUES ('Elektroniikkaa', 2);");
        lista.add("INSERT INTO Viesti (Nickname, Sisalto, Aika, KetjuId) VALUES"
                + " ('köyhäOpiskelija', 'Ei kukaa myis halvalla vanhaa läppäriään?"
                + " Pudotin fuksiläppärini parvekkeelta...', "
                + "CURRENT_TIMESTAMP, 5);");
        
        lista.add("INSERT INTO Ketju (Nimi, AlueId) VALUES ('FyMM2b', 3);");
        lista.add("INSERT INTO Viesti (Nickname, Sisalto, Aika, KetjuId) VALUES"
                + " ('ahdistunut', 'Enkö osaa!!?!?', "
                + "CURRENT_TIMESTAMP, 6);");
        lista.add("INSERT INTO Ketju (Nimi, AlueId) VALUES ('TiKaPe', 3);");
        lista.add("INSERT INTO Viesti (Nickname, Sisalto, Aika, KetjuId) VALUES"
                + " ('eksynytFyysikkoExactumissa', 'Missä täällä nyt on se BKCD122730-laskariluokka? :(', "
                + "CURRENT_TIMESTAMP, 7);");
        lista.add("INSERT INTO Ketju (Nimi, AlueId) VALUES ('Vuorovaikutukset ja Kappaleet', 3);");
        lista.add("INSERT INTO Viesti (Nickname, Sisalto, Aika, KetjuId) VALUES"
                + " ('Fuksi', 'Voiha vehnä, en tajuu nopeutta, niinku paikan aikaderivaatta, wtf...', "
                + "CURRENT_TIMESTAMP, 8);");
        
        lista.add("INSERT INTO Ketju (Nimi, AlueId) VALUES ('Jälkkärit, nam!', 4);");
        lista.add("INSERT INTO Viesti (Nickname, Sisalto, Aika, KetjuId) VALUES"
                + " ('Herkuttelija', 'Tiramisu on hyvää!', "
                + "CURRENT_TIMESTAMP, 9);");
        lista.add("INSERT INTO Ketju (Nimi, AlueId) VALUES ('Liharuokiiii', 4);");
        lista.add("INSERT INTO Viesti (Nickname, Sisalto, Aika, KetjuId) VALUES"
                + " ('Carnivore', 'Uhh, ribs mmmmmmmm', "
                + "CURRENT_TIMESTAMP, 10);");
        lista.add("INSERT INTO Ketju (Nimi, AlueId) VALUES ('Vege heaven', 4);");
        lista.add("INSERT INTO Viesti (Nickname, Sisalto, Aika, KetjuId) VALUES"
                + " ('Carnivore', 'Yäks, joku vege', "
                + "CURRENT_TIMESTAMP, 11);");
        lista.add("INSERT INTO Ketju (Nimi, AlueId) VALUES ('Mexican hot tabasco specials', 4);");
        lista.add("INSERT INTO Viesti (Nickname, Sisalto, Aika, KetjuId) VALUES"
                + " ('Huertas', 'Mitä laitetaan? :D :D :D', "
                + "CURRENT_TIMESTAMP, 12);");
        
        return lista;
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Alue (AlueId integer PRIMARY KEY, Nimi varchar(50) NOT NULL);");
        lista.add("CREATE TABLE Ketju (KetjuId integer PRIMARY KEY, Nimi varchar(1000) NOT NULL,"
                + " AlueId integer NOT NULL, FOREIGN KEY(AlueId) REFERENCES Alue(AlueId));");
        lista.add("CREATE TABLE Viesti (ViestiId integer PRIMARY KEY, Nickname varchar(50) NOT NULL,"
                + " Sisalto varchar(3000) NOT NULL, Aika timestamp NOT NULL, KetjuId integer NOT NULL,"
                + " FOREIGN KEY(KetjuId) REFERENCES Ketju(KetjuId));");
        
        //alueet
        lista.add("INSERT INTO Alue (Nimi) VALUES ('Ohjelmointi');");
        lista.add("INSERT INTO Alue (Nimi) VALUES ('Myydään/Ostetaan -alue');");
        lista.add("INSERT INTO Alue (Nimi) VALUES ('Laskarivinkit');");
        lista.add("INSERT INTO Alue (Nimi) VALUES ('Ruoka/Juoma');");
        
        //ketjut + avausviestit
        lista.add("INSERT INTO Ketju (Nimi, AlueId) VALUES ('Java', 1);");
        lista.add("INSERT INTO Viesti (Nickname, Sisalto, Aika, KetjuId) VALUES"
                + " ('Käpistelijä #1', 'Java on ihan taivaallinen kieli kyllä minä sanon!', "
                + "CURRENT_TIMESTAMP, 1);");
        lista.add("INSERT INTO Ketju (Nimi, AlueId) VALUES ('Python', 1);");
        lista.add("INSERT INTO Viesti (Nickname, Sisalto, Aika, KetjuId) VALUES"
                + " ('Käpistelijä #1', 'Pythonia kukaan käytä...turha ketju :(', "
                + "CURRENT_TIMESTAMP, 2);");
        
        lista.add("INSERT INTO Ketju (Nimi, AlueId) VALUES ('Antiikkia, antiikkia', 2);");
        lista.add("INSERT INTO Viesti (Nickname, Sisalto, Aika, KetjuId) VALUES"
                + " ('Antiikki-pappa', 'Miulta sais vintiltä ostaa pois kaikenlaista', "
                + "CURRENT_TIMESTAMP, 3);");
        lista.add("INSERT INTO Ketju (Nimi, AlueId) VALUES ('Rihkamaa', 2);");
        lista.add("INSERT INTO Viesti (Nickname, Sisalto, Aika, KetjuId) VALUES"
                + " ('Antiikki-pappa', 'Myös takapihalla on roinaa joka lähtöön ketä vaan kiinnostaa :D', "
                + "CURRENT_TIMESTAMP, 4);");
        lista.add("INSERT INTO Ketju (Nimi, AlueId) VALUES ('Elektroniikkaa', 2);");
        lista.add("INSERT INTO Viesti (Nickname, Sisalto, Aika, KetjuId) VALUES"
                + " ('köyhäOpiskelija', 'Ei kukaa myis halvalla vanhaa läppäriään?"
                + " Pudotin fuksiläppärini parvekkeelta...', "
                + "CURRENT_TIMESTAMP, 5);");
        
        lista.add("INSERT INTO Ketju (Nimi, AlueId) VALUES ('FyMM2b', 3);");
        lista.add("INSERT INTO Viesti (Nickname, Sisalto, Aika, KetjuId) VALUES"
                + " ('ahdistunut', 'Enkö osaa!!?!?', "
                + "CURRENT_TIMESTAMP, 6);");
        lista.add("INSERT INTO Ketju (Nimi, AlueId) VALUES ('TiKaPe', 3);");
        lista.add("INSERT INTO Viesti (Nickname, Sisalto, Aika, KetjuId) VALUES"
                + " ('eksynytFyysikkoExactumissa', 'Missä täällä nyt on se BKCD122730-laskariluokka? :(', "
                + "CURRENT_TIMESTAMP, 7);");
        lista.add("INSERT INTO Ketju (Nimi, AlueId) VALUES ('Vuorovaikutukset ja Kappaleet', 3);");
        lista.add("INSERT INTO Viesti (Nickname, Sisalto, Aika, KetjuId) VALUES"
                + " ('Fuksi', 'Voiha vehnä, en tajuu nopeutta, niinku paikan aikaderivaatta, wtf...', "
                + "CURRENT_TIMESTAMP, 8);");
        
        lista.add("INSERT INTO Ketju (Nimi, AlueId) VALUES ('Jälkkärit, nam!', 4);");
        lista.add("INSERT INTO Viesti (Nickname, Sisalto, Aika, KetjuId) VALUES"
                + " ('Herkuttelija', 'Tiramisu on hyvää!', "
                + "CURRENT_TIMESTAMP, 9);");
        lista.add("INSERT INTO Ketju (Nimi, AlueId) VALUES ('Liharuokiiii', 4);");
        lista.add("INSERT INTO Viesti (Nickname, Sisalto, Aika, KetjuId) VALUES"
                + " ('Carnivore', 'Uhh, ribs mmmmmmmm', "
                + "CURRENT_TIMESTAMP, 10);");
        lista.add("INSERT INTO Ketju (Nimi, AlueId) VALUES ('Vege heaven', 4);");
        lista.add("INSERT INTO Viesti (Nickname, Sisalto, Aika, KetjuId) VALUES"
                + " ('Carnivore', 'Yäks, joku vege', "
                + "CURRENT_TIMESTAMP, 11);");
        lista.add("INSERT INTO Ketju (Nimi, AlueId) VALUES ('Mexican hot tabasco specials', 4);");
        lista.add("INSERT INTO Viesti (Nickname, Sisalto, Aika, KetjuId) VALUES"
                + " ('Huertas', 'Mitä laitetaan? :D :D :D', "
                + "CURRENT_TIMESTAMP, 12);");
        
        return lista;
    }
    
    public ArrayList queryAndCollect(String query, Collector col) {
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            ArrayList elements = new ArrayList<>();
            
            while(rs.next()) {
                elements.add(col.collect(rs));
            }
            
            stmt.close();
            return elements;
        
        } catch (Exception e) {
            System.out.println("Tuli virhe DBContacter:queryAndCollect kyselyllä:\n " + query + "\n ja viestillä:\n" + e.getMessage());
            return null;
        }
    }
    public boolean update(String query) {
        try {
            Statement stmt= getConnection().createStatement();
            int touchedRows = stmt.executeUpdate(query);
            if (touchedRows >=1) {
                return true;
            } else { return false;}
        } catch (Exception e) {
            System.out.println("Tuli virhe DBContacter:update viestillä:\n" + e.getMessage());
            return false;
        }
    }
}
