package foorumi.ketju;


public class Ketju {
    private int id;
    private String nimi;
    private int alueId;
    private String url;

    public Ketju(int id, String nimi, int alueId) {
        this.id = id;
        this.nimi = nimi;
        this.alueId = alueId;
    }

    public int getId() {
        return id;
    }

    public String getNimi() {
        return nimi;
    }

    public String getUrl() {
        return "http://localhost:4567/viestit?ketjuId=" + id;
    }
    

    @Override
    public String toString() {
        return nimi + "(" + id + ")";
    }
    
    
}
