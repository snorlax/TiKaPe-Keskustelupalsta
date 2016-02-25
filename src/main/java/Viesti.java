
import java.sql.Timestamp;



public class Viesti {
    private int id;
    private String nickname;
    private String otsikko; //poistetaanko otsikko?
    private String sisalto;
    private String aika;
    private boolean onkoAvausviesti; //poistetaanko?

    public Viesti(int id, String nickname, String otsikko, String sisalto, String aika, boolean onkoAvausviesti) {
        this.id = id;
        this.nickname = nickname;
        this.otsikko = otsikko; //poistetaanko?
        this.sisalto = sisalto;
        this.aika = aika;
        this.onkoAvausviesti = onkoAvausviesti; // poistetaanko?
    }

    public String getAika() {
        return aika;
    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getOtsikko() {
        return otsikko;
    }

    public String getSisalto() {
        return sisalto;
    }

    public boolean OnkoAvausviesti() {
        return onkoAvausviesti;
    }
    
    @Override
    public String toString() {
        return otsikko + "\t" + sisalto + "\t" + nickname
                + "\t" + aika;
    }
}
