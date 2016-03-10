package foorumi.ketju;


public class Ketju {
    private int id;
    private String nimi;
    private int alueId;
    private String url;
    private String lisaysUrl;

    public Ketju(int id, String nimi, int alueId) {
        this.id = id;
        this.nimi = nimi;
        this.alueId = alueId;
        this.lisaysUrl = "/add?ketjuId=" + id;
    }

    public int getId() {
        return id;
    }

    public int getAlueId() {
        return alueId;
    }
    

    public String getNimi() {
        return nimi;
    }

    public String getUrl() {
        return "/viestit?ketjuId=" + id;
    }
    
    public String getLisaysUrl() {
        return lisaysUrl;
    }

    @Override
    public String toString() {
        return nimi + "(" + id + ")";
    }
    
    
}
