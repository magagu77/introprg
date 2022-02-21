import java.lang.reflect.Method;
import java.lang.reflect.Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestExercise {

    @Test
    @DisplayName("test mor una")
    public void testMorUna() {
        GatRenat renat = new GatRenat();
        String missatge = renat.mor();
        assertAll("mor1",
                () -> assertEquals("auch", missatge, "revisa el missatge de retorn quan perd una vida"),
                () -> assertEquals(6, renat.getVides(), "hauria de quedar-li 6 vides quan perd la primera")
                );
    }

    @Test
    @DisplayName("test Renat mor del tot")
    public void testMorDelTot() {
        GatRenat renat = new GatRenat();
        renat.setVides(1);
        String missatge = renat.mor();
        assertAll("mortot",
                () -> assertEquals("ximpún", missatge, "revisa el missatge de retorn quan perd totes les vides"),
                () -> assertEquals(0, renat.getVides(), "haurien de quedar-li 0 vides quan les perd totes")
                );
    }

    @Test
    @DisplayName("test Renat silenci quan mor si ja està mort")
    public void testSilenciQuanMortIEraMort() {
        GatRenat renat = new GatRenat();
        renat.setVides(0);
        String missatge = renat.mor();
        assertAll("mortsilenci",
                () -> assertEquals("...", missatge, "revisa el missatge de retorn quan ja estava mort"),
                () -> assertEquals(0, renat.getVides(), "hauria de quedar-li 0 vides")
                );
    }

    @Test
    @DisplayName("test Renat resuscita amb 1 per defecte")
    public void testResuscita1() {
        GatRenat renat = new GatRenat();
        renat.setVides(0);
        String missatge = renat.resuscita();
        assertAll("resuscita1",
                () -> assertEquals("guai!", missatge, "revisa el missatge de retorn quan resuscita"),
                () -> assertEquals(1, renat.getVides(), "hauria de recuperar 1 vida")
                );
    }

    @Test
    @DisplayName("test Renat resuscita amb 1 per defecte")
    public void testResuscitaMoltes() {
        GatRenat renat = new GatRenat();
        renat.setVides(0);
        String missatge = renat.resuscita(5);
        assertAll("resuscitamoltes",
                () -> assertEquals("guai!", missatge, "revisa el missatge de retorn quan resuscita"),
                () -> assertEquals(5, renat.getVides(), "hauria de recuperar 5 vides")
                );
    }

    @Test
    @DisplayName("test Renat resuscita quan no era mort")
    public void testResuscitaNoMort() {
        GatRenat renat = new GatRenat();
        String missatge = renat.resuscita(5);
        assertAll("resuscitaviu",
                () -> assertEquals("...", missatge, "revisa el missatge de retorn quan resuscita estant viu"),
                () -> assertEquals(7, renat.getVides(), "hauria tenir totes les vides")
                );
    }

    @Test
    @DisplayName("test Renat resuscita quan no prou vides")
    public void testResuscitaNegatiu() {
        GatRenat renat = new GatRenat();
        renat.setVides(0);
        String missatge = renat.resuscita(-5);
        assertAll("resuscitanegatiu",
                () -> assertEquals("...", missatge, "revisa el missatge de retorn quan resuscita amb vides negatives"),
                () -> assertEquals(0, renat.getVides(), "hauria de quedar-li 0 vides")
                );
    }


}