import java.beans.Transient;

/** Tests de la clase punt en la versio de l'exercici 52_03 */
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
public class TestPunt {
    @Test
    public void constructor() {
        // comprova que Punt() pugui ser cridat
        new Punt();
    }

    @Test
    public void constructorDefecteXZero() {
        // comprova que Punt() deixi a 0 la propietat x de Punt
        Punt p = new Punt();
        Assertions.assertEquals(0, p.getX());
    }
    @Test
    public void constructorDefecteYZero() {
        //Comprova que Punt() deixa a 0 la Y de punt
        Punt p = new Punt();
        Assertions.assertEquals(0,p.getY());
    }
}