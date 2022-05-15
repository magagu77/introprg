/** Tests per a la clase segment de l'exercici 52_06 */
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
public class TestSegment {
    @Test
    public void testConstructor() {
        // Comprova que es pot cridar el constructor
        new Segment();
    }
    @Test
    public void testGetP1() {
        //Comprova que el getP1 funciona
        Punt p = new Punt(1,2);
        Segment s =new Segment(p,p);
        assertEquals("Punt(1, 2)",s.getP1().toString());
    }
    @Test
    public void testGetP2() {
        //Comprova que el getP1 funciona
        Punt p = new Punt(1,2);
        Segment s =new Segment(p,p);
        assertEquals("Punt(1, 2)",s.getP2().toString());
    }
    @Test
    public void testConstructorEspecific() {
        // Comprova el constructor per especific
        Punt p1 = new Punt(1,2);
        Punt p2 = new Punt(3,4);
        Segment s = new Segment(p1,p2);
        assertAll(
            () -> assertEquals(p1,s.getP1()),
            () -> assertEquals(p2,s.getP2())
        );
    }
    @Test
    public void testTongitud() {
        Segment s = new Segment();
        double longitud = s.longitud();
        assertEquals(0,longitud);
    }
    @Test
    public void testTongitud2() {
        Punt p1 = new Punt(2,3);
        Punt p2 = new Punt(4,5);
        Segment s = new Segment(p1,p2);
        double longitud = Math.round(s.longitud()*100.0)/100.0;
        assertEquals(2.83,longitud);
    }
}