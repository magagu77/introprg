/** Tests per a la clase punt en la versió de l'exercici 52_04 */
import java.beans.Transient;

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
    @Test
    public void setX42() {
        // Test per a comprovar que setX permet cambiar el valor de X
        Punt p = new Punt();
        p.setX(42);
        Assertions.assertEquals(42, p.getX());
    }
    @Test
    public void setY42() {
        // Test per a comprovar que setX permet cambiar el valor de X
        Punt p = new Punt();
        p.setY(42);
        Assertions.assertEquals(42, p.getY());
    }
}