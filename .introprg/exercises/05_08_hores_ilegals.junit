/*
    Unit testing methods for exercise 05_07_hores_excepcionals
*/

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

public class TestExercise {

    @ParameterizedTest
    @CsvSource(value={
        "-1, 0, 0, 'hores fora de rang: -1', 'revisa quan hores és massa baix'",
        "24, 0, 0, 'hores fora de rang: 24', 'revisa quan hores és massa alt'",
        "0, -1, 0, 'minuts fora de rang: -1', 'revisa quan minuts és massa baix'",
        "0, 60, 0, 'minuts fora de rang: 60', 'revisa quan minuts és massa alt'",
        "0, 0, -1, 'segons fora de rang: -1', 'revisa quan segons és massa baix'",
        "0, 0, 60, 'segons fora de rang: 60', 'revisa quan segons és massa alt'",
    })
    @DisplayName("Test del constructor")
    public void constructor(int hores, int minuts, int segons, String esperat, String missatge) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Hora(hores, minuts, segons);
        }, missatge);
        assertEquals(esperat, exception.getMessage(), "revisa el missatge");

    }

    @ParameterizedTest
    @CsvSource(value={
        "-4, 'hores fora de rang: -4', 'revisa quan hores és massa baix'",
        "25, 'hores fora de rang: 25', 'revisa quan hores és massa alt'",
    })
    @DisplayName("Test de setHores")
    public void setHores(int valor, String esperat, String missatge) {
        Hora hora = new Hora();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            hora.setHores(valor);
        }, missatge);
        assertEquals(esperat, exception.getMessage(), "revisa el missatge");
    }

    @ParameterizedTest
    @CsvSource(value={
        "-5, 'minuts fora de rang: -5', 'revisa quan minuts és massa baix'",
        "61, 'minuts fora de rang: 61', 'revisa quan minuts és massa alt'",
    })
    @DisplayName("Test de setMinuts")
    public void setMinuts(int valor, String esperat, String missatge) {
        Hora hora = new Hora();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            hora.setMinuts(valor);
        }, missatge);
        assertEquals(esperat, exception.getMessage(), "revisa el missatge");
    }

    @ParameterizedTest
    @CsvSource(value={
        "-7, 'segons fora de rang: -7', 'revisa quan segons és massa baix'",
        "62, 'segons fora de rang: 62', 'revisa quan segons és massa alt'",
    })
    @DisplayName("Test de setSegons")
    public void setSegons(int valor, String esperat, String missatge) {
        Hora hora = new Hora();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            hora.setSegons(valor);
        }, missatge);
        assertEquals(esperat, exception.getMessage(), "revisa el missatge");
    }

}