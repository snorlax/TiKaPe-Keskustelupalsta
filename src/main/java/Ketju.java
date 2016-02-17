
public class Ketju {
    private int id;
    private String nimi;

    public Ketju(int id, String nimi) {
        this.id = id;
        this.nimi = nimi;
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
