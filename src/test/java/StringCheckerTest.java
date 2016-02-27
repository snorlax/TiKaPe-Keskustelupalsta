
import foorumi.StringChecker;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author henripau
 */
public class StringCheckerTest {
    
    public StringCheckerTest() {
    }
    StringChecker sc;
    @Before
    public void setUp() {
        sc = new StringChecker();
    }
    
    // assertEquals(Object expected, Object actual) !!!
    
     @Test
     public void erikoismerkit() {
         assertEquals(false, sc.puhdas("jee;jee"));
         assertEquals(false, sc.puhdas("jee=jee"));
     }
     @Test
     public void stringinPituus() {
         assertEquals(false, sc.notTooLong("jee;jee", 3));
         assertEquals(false, sc.notTooLong("jee=jee", 6));
         assertEquals(true, sc.notTooLong("jee=jee", 7));
         assertEquals(true, sc.notTooLong("jee=jee", 10));
     }
}
