/*
    Unit testing methods for exercise 02_17_utilitats_confirmacio
*/

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestExercise {

    @Test
    @DisplayName("Test hi és mòdul respostaABoolean()")
    public void prgtestX1respostaABoolean() {
        Class classe = UtilitatsConfirmacio.class;
        String modulObjectiu = "respostaABoolean";
        Method[] methods = classe.getDeclaredMethods();
        boolean foundTarget = false;
        for (Method method: methods) {
            if (modulObjectiu.equals(method.getName())) {
                foundTarget=true;
                Type[] types = method.getGenericParameterTypes();
                assertEquals(
                        1, 
                        types.length, 
                        "Revisa els paràmetres requerits pel mòdul " + modulObjectiu + "()"
                );
                assertEquals(
                    "java.lang.String",
                    types[0].getTypeName(),
                    "Revisa els paràmetres requerits pel mòdul " + modulObjectiu + "()"
                );
                assertEquals(
                        "boolean",
                        method.getGenericReturnType().getTypeName(),
                        "S'esperava " + modulObjectiu + "() d'un tipus diferent"
                );
            }
        }
        assertTrue(foundTarget, "No es troba el mòdul " + modulObjectiu + "(). Revisa enunciat.");
    }
}