/** Tests per a la clase punt en la versió de l'exercici 52_04 */
import java.beans.Transient;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
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
        assertEquals(0, p.getX());
    }
    @Test
    public void constructorDefecteYZero() {
        //Comprova que Punt() deixa a 0 la Y de punt
        Punt p = new Punt();
        assertEquals(0,p.getY());
    }
    @Test
    public void setX42() {
        // Test per a comprovar que setX permet cambiar el valor de X
        Punt p = new Punt();
        p.setX(42);
        assertEquals(42, p.getX());
    }
    @Test
    public void setY42() {
        // Test per a comprovar que setX permet cambiar el valor de X
        Punt p = new Punt();
        p.setY(42);
        assertEquals(42, p.getY());
    }
    @Test
     public void constructorEspecific() {
         // Test constructor especific
         Punt p = new Punt(1, 2);
         assertAll(
            () -> assertEquals(1, p.getX()),
            () -> assertEquals(2, p.getY())
        );
     }
     @Test
     public void comprovaSuma() {
         // Comprova que el metode suma funciona
        Punt p = new Punt(1,2);
        Punt p2 = new Punt(3,4);
        p.suma(p2);
        assertAll(
            () -> assertEquals(4,p.getX()),
            () -> assertEquals(6,p.getY())
        );
     }
}