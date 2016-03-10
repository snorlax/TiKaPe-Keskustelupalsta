package foorumi;

import foorumi.viesti.ViestiDao;
import foorumi.viesti.Viesti;
import foorumi.viesti.ViestiCollector;
import foorumi.ketju.KetjuCollector;
import foorumi.ketju.Ketju;
import foorumi.ketju.KetjuDao;
import foorumi.alue.Alue;
import foorumi.alue.AlueCollector;
import java.sql.*;
import java.util.ArrayList;
import static spark.Spark.*;
import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {
    private static boolean herokuKaytossa;
    public static void main(String[] args) {
        herokuKaytossa = false;
        
        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
            herokuKaytossa = true;
        }
        // käytetään oletuksena paikallista sqlite-tietokantaa
        String jdbcOsoite = "jdbc:sqlite:Keskustelupalsta.db";
        // jos heroku antaa käyttöömme tietokantaosoitteen, otetaan se käyttöön
        if (System.getenv("DATABASE_URL") != null) {
            jdbcOsoite = System.getenv("DATABASE_URL");
            herokuKaytossa = true;
        } 
        
        DBContacter c = new DBContacter(jdbcOsoite);
        //c.testaaYhteys();
        c.foreignKeytPaalle();
        System.out.println("");
        

        //Spark-osuus alkaa
        //-----------------
        //Määritellään staattisten tiedostojen sijainti
        Spark.staticFileLocation("/public");

        get("/", (req, res) -> {
            res.redirect("/alueet");
            
            return "Redirecting...";
        });

        get("/alueet", (req, res) -> {
            HashMap m = new HashMap<>();
            m.put("kuvaus", "Foorumin alueet");
            m.put("alueet", getAlueet(c));

            return new ModelAndView(m, "alueet");
        }, new ThymeleafTemplateEngine());

//        get("/ketjut", (req, res) -> {
//            KetjuDao kD = new KetjuDao(c);
//            HashMap m = new HashMap<>();
//            m.put("kuvaus", "Alueen X ketjut");
//            m.put("ketjut", kD.kaikkiKetjut());
//            
//            return new ModelAndView(m, "ketjut");
//        }, new ThymeleafTemplateEngine());
//        
//        get("/viestit", (req, res) -> {
//            ViestiDao vD = new ViestiDao(c);
//            HashMap m = new HashMap<>();
//            m.put("kuvaus", "Ketjun X viestit");
//            m.put("viestit", vD.kaikkiViestit());
//            
//            return new ModelAndView(m, "viestit");
//        }, new ThymeleafTemplateEngine());
        get("/ketjut", (req, res) -> {
            KetjuDao kD = new KetjuDao(c);
            HashMap m = new HashMap<>();
            int alueId = Integer.parseInt(req.queryParams("alueId"));
            String alueenNimi = "";
            
            for (Object alue : c.queryAndCollect("SELECT * FROM Alue", new AlueCollector())) {
                Alue a = (Alue) alue;
                if (a.getId() == alueId) {
                    alueenNimi = a.getNimi();
                }
            }
            
            m.put("nimi", alueenNimi);
            m.put("ketjut", kD.ketjutAlueelta(alueId));

            return new ModelAndView(m, "ketjut");
        }, new ThymeleafTemplateEngine());

        get("/viestit", (req, res) -> {
            ViestiDao vD = new ViestiDao(c);
            HashMap m = new HashMap<>();
            int ketjuId = Integer.parseInt(req.queryParams("ketjuId"));
            String ketjunNimi = "";
            String lisaysUrl = "";
            
            for (Ketju k : new KetjuDao(c).kaikkiKetjut()) {
                if (k.getId() == ketjuId) {
                    ketjunNimi = k.getNimi();
                    lisaysUrl = k.getLisaysUrl();
                }
            }
            
            m.put("nimi", ketjunNimi);
            m.put("lisaysUrl", lisaysUrl);
            m.put("viestit", vD.viestitKetjusta(ketjuId));

            return new ModelAndView(m, "viestit");
        }, new ThymeleafTemplateEngine());

        get("/add", (req, res) -> {
            int ketjuId = Integer.parseInt(req.queryParams("ketjuId"));
            return "<form method=\"POST\" action=\"/add?ketjuId=" + ketjuId + "\">\n"
                    + "Kirjoita viesti:<br/>\n"
                    + "<input type=\"text\" name=\"sisalto\"/><br/>\n"
                    + "Anna käyttäjänimesi:<br/>\n"
                    + "<input type=\"text\" name=\"kayttis\"/><br/>\n"
                    + "<input type=\"submit\" value=\"Lisää viesti\"/>\n"
                    + "</form>";
        });
        post("/add", (req, res) -> {
            int ketjuId = Integer.parseInt(req.queryParams("ketjuId"));
            String sisalto = req.queryParams("sisalto");
            String kayttis = req.queryParams("kayttis");

            ViestiDao vd = new ViestiDao(c);
            vd.lisaaVastausviesti(ketjuId, sisalto, kayttis);

            String linkinOsoite = "http://localhost:4567";
            if (herokuKaytossa) {
                linkinOsoite = "";
            }
            return "Lisättiin  syöte: " + sisalto + " t: " + kayttis + "<br/>"
                    + "<br/> <a href=\"" + linkinOsoite + "/viestit?ketjuId=" + ketjuId + "\">Palaa viestisivulle</a>";

        });
//        c.suljeYhteys();
//        System.out.println("\n\nYhteys suljettu.");
    }

    public static void tulostaKaikkiViestit(DBContacter c) {
        ArrayList<Viesti> viestit = c.queryAndCollect("SELECT * FROM Viesti;", new ViestiCollector());
        System.out.println("Kaikki viestit:");
        for (Viesti s : viestit) {
            System.out.println(s);
        }
    }

    public static void tulostaKaikkiKetjut(DBContacter c) {
        ArrayList<Ketju> ketjut = c.queryAndCollect("SELECT * FROM Ketju;", new KetjuCollector());
        System.out.println("Kaikki ketjut");
        for (Ketju k : ketjut) {
            System.out.println(k);
        }
    }

    public static void tulostaKaikkiAlueet(DBContacter c) {
        ArrayList<Alue> alueet = c.queryAndCollect("SELECT * FROM Alue;", new AlueCollector());
        System.out.println("Kaikki alueet");
        for (Alue a : alueet) {
            System.out.println(a);
        }
    }

    public static void uusiViesti(DBContacter c) {
        ViestiDao vd = new ViestiDao(c);
        boolean tulos = vd.lisaaVastausviesti(3, "testiketjun kolme avausviesti", "henri P");
        if (tulos) {
            System.out.println("Viestin lisäys onnistui!\n");
        } else {
            System.out.println("Viestin lisäys ei onnistunut :( \n");
        }
    }

    public static ArrayList<Alue> getAlueet(DBContacter c) {
        ArrayList<Alue> alueet = c.queryAndCollect("SELECT * FROM Alue;", new AlueCollector());
        return alueet;
    }

}
