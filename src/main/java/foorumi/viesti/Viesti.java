package foorumi.viesti;


public class Viesti {
    private int id;
    private String nickname;
    private String sisalto;
    private String aika;
    private int ketjuId;
    private String lisaysUrl;
    

    public Viesti(int id, String nickname, String sisalto, String aika, int ketjuId) {
        this.id = id;
        this.nickname = nickname;
        this.sisalto = sisalto;
        this.aika = aika;
        this.ketjuId = ketjuId;
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

    public int getKetjuId() {
        return ketjuId;
    }

    public String getLisaysUrl() {
        return "/add?ketjuId=" + ketjuId;
    }
    
    
    @Override
    public String toString() {
        return sisalto + " t: " + nickname
                + " (lähetetty: " + aika + ")";
    }
}
