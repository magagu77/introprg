import java.lang.reflect.Method;
import java.lang.reflect.Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

public class TestExercise {

    @Test
    @DisplayName("test Renat neix estirat")
    public void testRenatNeixEstirat() {
        assertEquals("estirat", new GatRenat().getPosicio());
    }

    @Test
    @DisplayName("test Renat té la propietat privada posicio")
    public void testRenatTePosicio() {
        assertTrue(UtilTests.classHasPrivateField(GatRenat.class, "posicio"));
    }
}