/*
    Unit testing methods for exercise 06_04_select
*/

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestExercise {

    @Test
    @DisplayName("test el constructor d'Animal no accepta id negatiu")
    public void noAcceptaIdNegatiu() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Animal(-1, "pardal", new Categoria("ocell"));
        }, "revisa els requeriments del constructor");
        assertEquals("L'identificador ha de ser positiu", exception.getMessage(), "revisa el missatge");
    }

    @Test
    @DisplayName("test els constructors d'Animal no accepten nom ni blanc ni null")
    public void constructorIndefinitNoAcceptaNomNullOEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Animal(null, new Categoria("ocell"));
        }, "revisa els requeriments del constructor");
        assertEquals("El nom no pot ser null ni blanc", exception.getMessage(), "revisa el missatge");

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Animal("", new Categoria("ocell"));
        }, "revisa els requeriments del constructor");
        assertEquals("El nom no pot ser null ni blanc", exception.getMessage(), "revisa el missatge");

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Animal("          ", new Categoria("ocell"));
        }, "revisa els requeriments del constructor");
        assertEquals("El nom no pot ser null ni blanc", exception.getMessage(), "revisa el missatge");

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Animal(1, null, new Categoria("ocell"));
        }, "revisa els requeriments del constructor");
        assertEquals("El nom no pot ser null ni blanc", exception.getMessage(), "revisa el missatge");

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Animal(1, "", new Categoria("ocell"));
        }, "revisa els requeriments del constructor");
        assertEquals("El nom no pot ser null ni blanc", exception.getMessage(), "revisa el missatge");

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Animal(1, "          ", new Categoria("ocell"));
        }, "revisa els requeriments del constructor");
        assertEquals("El nom no pot ser null ni blanc", exception.getMessage(), "revisa el missatge");
    }

    @Test
    @DisplayName("test els constructors d'Animal no accepten categoria null")
    public void constructorIndefinitNoAcceptaCategoriaNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Animal("ocell", null);
        }, "revisa els requeriments del constructor");
        assertEquals("La categoria no pot ser null", exception.getMessage(), "revisa el missatge");

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Animal(1, "ocell", null);
        }, "revisa els requeriments del constructor");
        assertEquals("La categoria no pot ser null", exception.getMessage(), "revisa el missatge");
    }

    @Test
    @DisplayName("Test hi és mòdul getNomTaules()")
    public void prgtestX1getNomTaules() {
        Class classe = Zoo.class;
        String modulObjectiu = "getNomTaules";
        Method[] methods = classe.getDeclaredMethods();
        boolean foundTarget = false;
        for (Method method: methods) {
            if (modulObjectiu.equals(method.getName())) {
                foundTarget=true;
                Type[] types = method.getGenericParameterTypes();
                assertEquals(
                        0, 
                        types.length, 
                        "Revisa els paràmetres requerits pel mòdul " + modulObjectiu + "()"
                );
                assertEquals(
                        "java.lang.String",
                        method.getGenericReturnType().getTypeName(),
                        "S'esperava " + modulObjectiu + "() d'un tipus diferent"
                );
            }
        }
        assertTrue(foundTarget, "No es troba el mòdul " + modulObjectiu + "(). Revisa enunciat.");
    }
}