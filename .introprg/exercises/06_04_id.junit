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
    @DisplayName("test el setter de Categoria no accepta valors negatius")
    public void setterNoAcceptaNegatius() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Categoria("ocell").setId(-1);
        }, "revisa els requeriments del setter");
        assertEquals("L'identificador ha de ser positiu", exception.getMessage(), "revisa el missatge");
    }

    @Test
    @DisplayName("Test hi és mòdul mostraCategories()")
    public void prgtestX1mostraCategories() {
        Class classe = ZooUtils.class;
        String modulObjectiu = "mostraCategories";
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
                    "java.util.List<Categoria>",
                    types[0].getTypeName(),
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