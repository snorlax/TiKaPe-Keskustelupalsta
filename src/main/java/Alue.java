
public class Alue {
    private int id;
    private String nimi;

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

    @Override
    public String toString() {
        return nimi + " (" + id + ")";
    }
    
    
}
