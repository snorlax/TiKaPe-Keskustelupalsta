
public class Viesti {
    private int id;
    private String nickname;
    private String sisalto;
    private String aika;

    public Viesti(int id, String nickname, String sisalto, String aika) {
        this.id = id;
        this.nickname = nickname;
        this.sisalto = sisalto;
        this.aika = aika;
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


    public String getSisalto() {
        return sisalto;
    }
    
    @Override
    public String toString() {
        return sisalto + " t: " + nickname
                + " (lähetetty: " + aika + ")";
    }
}
