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
import spark.template.thymeleaf.ThymeleafTemplateEngine;


public class Main {

    public static void main(String[] args) {
        DBContacter c = new DBContacter("jdbc:sqlite:Keskustelupalsta.db");
        c.testaaYhteys();
        c.foreignKeytPaalle();
        System.out.println("");
        
        //Spark osuus alkaa
        get("/alueet", (req, res) -> {
            HashMap m = new HashMap<>();
            m.put("kuvaus", "Foorumin alueet");
            m.put("alueet", getAlueet(c));
            
            return new ModelAndView(m, "index");
        }, new ThymeleafTemplateEngine());

        get("/ketjut", (req, res) -> {
            KetjuDao kD = new KetjuDao(c);
            HashMap m = new HashMap<>();
            m.put("kuvaus", "Alueen X ketjut");
            m.put("ketjut", kD.kaikkiKetjut());
            
            return new ModelAndView(m, "ketjut");
        }, new ThymeleafTemplateEngine());
        
        get("/viestit", (req, res) -> {
            ViestiDao vD = new ViestiDao(c);
            HashMap m = new HashMap<>();
            m.put("kuvaus", "Ketjun X viestit");
            m.put("viestit", vD.kaikkiViestit());
            
            return new ModelAndView(m, "viestit");
        }, new ThymeleafTemplateEngine());
        
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
