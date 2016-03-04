package foorumi.alue;


public class Alue {
    private int id;
    private String nimi;
    private String url;

    public Alue(int id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }

    public int getId() {
        return id;
    }

    public String getNimi() {
        return nimi;
    }

    public String getUrl() {
        return "http://localhost:4567/ketjut?alueId=" + id;
    }
    

    @Override
    public String toString() {
        return nimi + " (" + id + ")";
    }
    
    
}
