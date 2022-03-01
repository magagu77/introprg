import java.lang.reflect.Method;
import java.lang.reflect.Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

public class TestExercise {

    @Test
    @DisplayName("test Renat inicialitzar a estirat si error")
    public void testRenatNeixEstirat() {
        GatRenat renat = new GatRenat(5, "corrent");
        assertAll("estirat",
                () -> assertEquals(5, renat.getVides()),
                () -> assertEquals("estirat", renat.getPosicio())
                );
    }

    @Test
    @DisplayName("test Renat pot inicialitzar assegut")
    public void testRenatNeixAssegut() {
        GatRenat renat = new GatRenat("assegut");
        assertAll("dret",
                () -> assertEquals(7, renat.getVides()),
                () -> assertEquals("assegut", renat.getPosicio())
                );
    }

    @Test
    @DisplayName("test Renat constructor protegeix posició")
    public void testRenatProtegeixPosicioQuanUnParam() {
        GatRenat renat = new GatRenat("corrent");
        assertAll("corrent",
                () -> assertEquals(7, renat.getVides()),
                () -> assertEquals("estirat", renat.getPosicio())
                );
    }

    @Test
    @DisplayName("test Renat pot inicialitzar dret")
    public void testRenatNeixDret() {
        GatRenat renat = new GatRenat(6, "dret");
        assertAll("dret",
                () -> assertEquals(6, renat.getVides()),
                () -> assertEquals("dret", renat.getPosicio())
                );
    }

    @Test
    @DisplayName("test Renat constructor protegeix vides")
    public void testRenatProtegeixVidesDosParams() {
        GatRenat renat = new GatRenat(-12, "dret");
        assertAll("poques vides",
                () -> assertEquals(7, renat.getVides()),
                () -> assertEquals("dret", renat.getPosicio())
                );
    }

    @Test
    @DisplayName("test Renat constructor protegeix vides")
    public void testRenatProtegeixPosicioDosParams() {
        GatRenat renat = new GatRenat(2, "fent el pi");
        assertAll("fent el pi",
                () -> assertEquals(2, renat.getVides()),
                () -> assertEquals("estirat", renat.getPosicio())
                );
    }


}