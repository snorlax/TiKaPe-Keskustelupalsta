package foorumi.ketju;


public class Ketju {
    private int id;
    private String nimi;
    private int alueId;

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
    

    @Override
    public String toString() {
        return nimi + "(" + id + ")";
    }
    
    
}
