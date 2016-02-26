
/**
 *
 * @author henripau
 * SQL-injektioiden yms virheellisten String-muotoisten syötteiden tarkistukseen
 */
public class StringChecker {
    
    public boolean puhdas(String s) {
        
        s = s.toLowerCase(); // kaikki testit voi nyt käsitellä pienellä kirjoitetuille
        
        if (s.contains(";")) {return false;}
        else if (s.contains("drop table")) {return false;}
        else if (s.contains("delete from")) {return false;}
        else if (s.contains("alter table")) {return false;}
        else if (s.contains("=")) {return false;}
        //else if (ehto) {return false;}
        // tähän vaa lisäilee mitä tulee mieleen, pidetään mielellään yksirivisinä
        //else if (!s.matches("[^A-Za-z0-9]")) {return false;}
        // edellinen kieltäisi kaikki erikoismerkit, onko vähän liian tiukka sääntö?
        
        else {return true;}
    }
    
    public boolean notTooLong(String s, int maxPituus) {
        if (s.length() <= maxPituus) {
            return true;
        } else {
            return false;
        }
    }
}
