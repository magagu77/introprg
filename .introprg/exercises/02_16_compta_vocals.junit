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
    @DisplayName("Test hi és mòdul mostraOcurrencies")
    public void comparaOcurrencies() {
        Class classe = ComptaVocals.class;
        String modulObjectiu = "mostraOcurrencies";
        Method[] methods = classe.getDeclaredMethods();
        boolean foundTarget = false;
        for (Method method: methods) {
            if (modulObjectiu.equals(method.getName())) {
                foundTarget=true;
                Type[] types = method.getGenericParameterTypes();
                assertEquals(2, types.length, "Revisa els paràmetres requerits pel mòdul " + modulObjectiu + "()");
                assertEquals("char", types[0].getTypeName(), "Revisa els paràmetres requerits pel mòdul " + modulObjectiu + "()");
                assertEquals("int", types[1].getTypeName(), "Revisa els paràmetres requerits pel mòdul " + modulObjectiu + "()");
                assertEquals("void", method.getGenericReturnType().getTypeName(), "S'esperava que " + modulObjectiu + "() fos un procediment");
            }
        }
        assertTrue(foundTarget, "No es troba el mòdul " + modulObjectiu + "(). Revisa enunciat.");
    }

    @Test
    @DisplayName("Test hi és mòdul quantesOcurrencies")
    public void quantesOcurrencies() {
        Class classe = ComptaVocals.class;
        String modulObjectiu = "quantesOcurrencies";
        Method[] methods = classe.getDeclaredMethods();
        boolean foundTarget = false;
        for (Method method: methods) {
            if (modulObjectiu.equals(method.getName())) {
                foundTarget=true;
                Type[] types = method.getGenericParameterTypes();
                assertEquals(2, types.length, "Revisa els paràmetres requerits pel mòdul " + modulObjectiu + "()");
                assertEquals("java.lang.String", types[0].getTypeName(), "Revisa els paràmetres requerits pel mòdul " + modulObjectiu + "()");
                assertEquals("char", types[1].getTypeName(), "Revisa els paràmetres requerits pel mòdul " + modulObjectiu + "()");
                assertEquals("int", method.getGenericReturnType().getTypeName(), "Revisa el tipus de retorn de " + modulObjectiu + "()");
            }
        }
        assertTrue(foundTarget, "No es troba el mòdul " + modulObjectiu + "(). Revisa enunciat.");
    }

    @Test
    @DisplayName("Test hi és mòdul mostraOcurrencies()")
    public void prgtestX1mostraOcurrencies() {
        Class classe = ComptaVocals.class;
        String modulObjectiu = "mostraOcurrencies";
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
                    "char",
                    types[0].getTypeName(),
                    "Revisa els paràmetres requerits pel mòdul " + modulObjectiu + "()"
                );
                assertEquals(
                    "int",
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
    @DisplayName("Test hi és mòdul quantesOcurrencies()")
    public void prgtestX2quantesOcurrencies() {
        Class classe = ComptaVocals.class;
        String modulObjectiu = "quantesOcurrencies";
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
                        "int",
                        method.getGenericReturnType().getTypeName(),
                        "S'esperava " + modulObjectiu + "() d'un tipus diferent"
                );
            }
        }
        assertTrue(foundTarget, "No es troba el mòdul " + modulObjectiu + "(). Revisa enunciat.");
    }
}