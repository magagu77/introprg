/*
 * Unit testing methods for an exercise
 */

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestExercise {

    @Test
    @DisplayName("Test no hi és mòdul reemplaçat")
    public void comparaAiEs() {
        Method[] methods = ComptaAiEs.class.getDeclaredMethods();
        boolean foundTarget = false;
        for (Method method: methods) {
            assertNotEquals("comparaAiEs", method.getName(), "cal eliminar el mòdul reemplaçat");
        }
    }

    @Test
    @DisplayName("Test hi és mòdul comptaLletra()")
    public void prgtestX1comptaLletra() {
        Class classe = ComptaAiEs.class;
        String modulObjectiu = "comptaLletra";
        Method[] methods = classe.getDeclaredMethods();
        boolean foundTarget = false;
        for (Method method: methods) {
            if (modulObjectiu.equals(method.getName())) {
                foundTarget=true;
                Type[] types = method.getGenericParameterTypes();
                assertEquals(
                        2, 
                        types.length, 
                        "Revisa els paràmetres requerits pel mòdul " + modulObjectiu + "()"
                );
                assertEquals(
                    "java.lang.String",
                    types[0].getTypeName(),
                    "Revisa els paràmetres requerits pel mòdul " + modulObjectiu + "()"
                );
                assertEquals(
                    "char",
                    types[1].getTypeName(),
                    "Revisa els paràmetres requerits pel mòdul " + modulObjectiu + "()"
                );
                assertEquals(
                        "void",
                        method.getGenericReturnType().getTypeName(),
                        "S'esperava " + modulObjectiu + "() d'un tipus diferent"
                );
            }
        }
        assertTrue(foundTarget, "No es troba el mòdul " + modulObjectiu + "(). Revisa enunciat.");
    }
    @Test
    @DisplayName("Test hi és mòdul comparaOcurrencies()")
    public void prgtestX2comparaOcurrencies() {
        Class classe = ComptaAiEs.class;
        String modulObjectiu = "comparaOcurrencies";
        Method[] methods = classe.getDeclaredMethods();
        boolean foundTarget = false;
        for (Method method: methods) {
            if (modulObjectiu.equals(method.getName())) {
                foundTarget=true;
                Type[] types = method.getGenericParameterTypes();
                assertEquals(
                        3, 
                        types.length, 
                        "Revisa els paràmetres requerits pel mòdul " + modulObjectiu + "()"
                );
                assertEquals(
                    "java.lang.String",
                    types[0].getTypeName(),
                    "Revisa els paràmetres requerits pel mòdul " + modulObjectiu + "()"
                );
                assertEquals(
                    "char",
                    types[1].getTypeName(),
                    "Revisa els paràmetres requerits pel mòdul " + modulObjectiu + "()"
                );
                assertEquals(
                    "char",
                    types[2].getTypeName(),
                    "Revisa els paràmetres requerits pel mòdul " + modulObjectiu + "()"
                );
                assertEquals(
                        "void",
                        method.getGenericReturnType().getTypeName(),
                        "S'esperava " + modulObjectiu + "() d'un tipus diferent"
                );
            }
        }
        assertTrue(foundTarget, "No es troba el mòdul " + modulObjectiu + "(). Revisa enunciat.");
    }
}