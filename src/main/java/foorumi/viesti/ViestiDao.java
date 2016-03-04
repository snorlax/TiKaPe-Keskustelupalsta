package foorumi.viesti;


import foorumi.DBContacter;
import foorumi.StringChecker;
import java.util.ArrayList;

public class ViestiDao {

    private DBContacter c;

    public ViestiDao(DBContacter c) {
        this.c = c;
    }

    //Alla olevistahan saa helposti viestimäärät ArrayList.size()
    //Oletuksena viestit tulevat ORDER BY aika DESC -järjestettyinä
    //Eli palautetun ArrayListin nollas jäsen on viimeisisin viesti!
    public ArrayList<Viesti> kaikkiViestit() {
        return c.queryAndCollect("SELECT * FROM Viesti ORDER BY aika DESC;", new ViestiCollector());
    }

    public ArrayList<Viesti> kaikkiViestit(boolean kaannettyAikajarjestys) {
        if (kaannettyAikajarjestys) {
            return c.queryAndCollect("SELECT * FROM Viesti ORDER BY aika ASC;", new ViestiCollector());
        } else {
            return c.queryAndCollect("SELECT * FROM Viesti ORDER BY aika DESC;", new ViestiCollector());
        }
    }
    public Viesti viimeisinViestiPalstalta() {
        return kaikkiViestit().get(0);
    }

    public ArrayList<Viesti> viestitKetjusta(int ketjuId) {
        return c.queryAndCollect("SELECT * FROM Viesti"
                + " WHERE KetjuId = " + ketjuId + " ORDER BY aika DESC;", new ViestiCollector());
    }

    public ArrayList<Viesti> viestitKetjusta(int ketjuId, boolean kaannettyAikajarjestys) {
        if (kaannettyAikajarjestys) {
            return c.queryAndCollect("SELECT * FROM Viesti"
                    + " WHERE KetjuId = " + ketjuId + " ORDER BY aika ASC;", new ViestiCollector());
        } else {
            return c.queryAndCollect("SELECT * FROM Viesti"
                    + " WHERE KetjuId = " + ketjuId + " ORDER BY aika DESC;", new ViestiCollector());
        }
    }
    public Viesti viimeisinViestiKetjusta(int KetjuId) {
        return viestitKetjusta(KetjuId).get(0);
    }

    public ArrayList<Viesti> viestitAlueelta(int alueId) {
        return c.queryAndCollect("SELECT * FROM Viesti, Ketju"
                + " WHERE Vieti.KetjuId = Ketju.ketjuId AND Ketju.AlueId = "
                + alueId + " ORDER BY aika DESC;", new ViestiCollector());
    }

    public ArrayList<Viesti> viestitAlueelta(int alueId, boolean kaannettyAikajarjestys) {
        if (kaannettyAikajarjestys) {
            return c.queryAndCollect("SELECT * FROM Viesti, Ketju"
                    + " WHERE Vieti.KetjuId = Ketju.ketjuId AND Ketju.AlueId = "
                    + alueId + " ORDER BY aika ASC;", new ViestiCollector());
        } else {
            return c.queryAndCollect("SELECT * FROM Viesti, Ketju"
                    + " WHERE Vieti.KetjuId = Ketju.ketjuId AND Ketju.AlueId = "
                    + alueId + " ORDER BY aika DESC;", new ViestiCollector());
        }
    }
    public Viesti viimeisinViestiAlueelta(int AlueId) {
        return viestitAlueelta(AlueId).get(0);
    }

    public boolean lisaaVastausviesti(int ketjuId, String sisalto, String nickname) {
        StringChecker sc = new StringChecker();
        if (sc.puhdas(sisalto) && sc.puhdas(nickname)
                && sc.notTooLong(sisalto, 2900) && sc.notTooLong(nickname, 45)) {

            return c.update("INSERT INTO Viesti (Nickname, sisalto, aika, ketjuId)"
                    + " VALUES ('" + nickname + "', '" + sisalto + "', "
                    + "CURRENT_TIMESTAMP, " + ketjuId + ");");
        } else {
            return false;
        }
    }
}
