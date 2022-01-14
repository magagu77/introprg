/*
 * Unit testing methods for an exercise
 */

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestExercise {

    @Test
    @DisplayName("test jugadorGuanya() taulell buit")
    public void jugadorGuanyaTaulellBuit() {
        char[][] taulell = {
            {'·', '·', '·'},
            {'·', '·', '·'},
            {'·', '·', '·'}
        };
        boolean guanya = TresEnRatlla.jugadorGuanya(taulell , 'X');
        assertFalse(guanya, "Revisa jugadorGuanya() quan el taulell està buït");
    }

    @Test
    @DisplayName("test jugadorGuanya() fa fila 0")
    public void jugadorGuanyaTaulellFila0() {
        char[][] taulell = {
            {'X', 'X', 'X'},
            {'·', '·', '·'},
            {'·', '·', '·'}
        };
        boolean guanya = TresEnRatlla.jugadorGuanya(taulell , 'X');
        assertTrue(guanya, "Revisa jugadorGuanya() quan ha marcat la fila 0");
    }

    @Test
    @DisplayName("test jugadorGuanya() fa fila 1")
    public void jugadorGuanyaTaulellFila1() {
        char[][] taulell = {
            {'·', '·', '·'},
            {'X', 'X', 'X'},
            {'·', '·', '·'}
        };
        boolean guanya = TresEnRatlla.jugadorGuanya(taulell , 'X');
        assertTrue(guanya, "Revisa jugadorGuanya() quan ha marcat la fila 1");
    }

    @Test
    @DisplayName("test jugadorGuanya() fa fila 2")
    public void jugadorGuanyaTaulellFila2() {
        char[][] taulell = {
            {'·', '·', '·'},
            {'·', '·', '·'},
            {'X', 'X', 'X'}
        };
        boolean guanya = TresEnRatlla.jugadorGuanya(taulell , 'X');
        assertTrue(guanya, "Revisa jugadorGuanya() quan ha marcat la fila 2");
    }


    @Test
    @DisplayName("test jugadorGuanya() fa columna 0")
    public void jugadorGuanyaTaulellColumna0() {
        char[][] taulell = {
            {'X', '·', '·'},
            {'X', '·', '·'},
            {'X', '·', '·'}
        };
        boolean guanya = TresEnRatlla.jugadorGuanya(taulell , 'X');
        assertTrue(guanya, "Revisa jugadorGuanya() quan ha marcat la columna 0");
    }

    @Test
    @DisplayName("test jugadorGuanya() fa columna 1")
    public void jugadorGuanyaTaulellColumna1() {
        char[][] taulell = {
            {'·', 'X', '·'},
            {'·', 'X', '·'},
            {'·', 'X', '·'}
        };
        boolean guanya = TresEnRatlla.jugadorGuanya(taulell , 'X');
        assertTrue(guanya, "Revisa jugadorGuanya() quan ha marcat la columna 1");
    }

    @Test
    @DisplayName("test jugadorGuanya() fa columna 2")
    public void jugadorGuanyaTaulellColumna2() {
        char[][] taulell = {
            {'·', '·', 'X'},
            {'·', '·', 'X'},
            {'·', '·', 'X'}
        };
        boolean guanya = TresEnRatlla.jugadorGuanya(taulell , 'X');
        assertTrue(guanya, "Revisa jugadorGuanya() quan ha marcat la columna 2");
    }

    @Test
    @DisplayName("test jugadorGuanya() fa diagona 0")
    public void jugadorGuanyaTaulellDiagonal0() {
        char[][] taulell = {
            {'X', '·', '·'},
            {'·', 'X', '·'},
            {'·', '·', 'X'}
        };
        boolean guanya = TresEnRatlla.jugadorGuanya(taulell , 'X');
        assertTrue(guanya, "Revisa jugadorGuanya() quan ha marcat la diagonal \\");
    }

    @Test
    @DisplayName("test jugadorGuanya() fa diagona 1")
    public void jugadorGuanyaTaulellDiagonal1() {
        char[][] taulell = {
            {'·', '·', 'X'},
            {'·', 'X', '·'},
            {'X', '·', '·'}
        };
        boolean guanya = TresEnRatlla.jugadorGuanya(taulell , 'X');
        assertTrue(guanya, "Revisa jugadorGuanya() quan ha marcat la diagonal /");
    }

    @Test
    @DisplayName("test jugadorGuanya() el contrari fa fila 0")
    public void jugadorGuanyaTaulellFila0AltreJugador() {
        char[][] taulell = {
            {'X', 'X', 'X'},
            {'·', '·', '·'},
            {'·', '·', '·'}
        };
        boolean guanya = TresEnRatlla.jugadorGuanya(taulell , 'O');
        assertFalse(guanya, "Revisa jugadorGuanya() quan l'altre ha marcat la fila 0");
    }

    @Test
    @DisplayName("test jugadorGuanya() el contrari fa fila 1")
    public void jugadorGuanyaTaulellFila1AltreJugador() {
        char[][] taulell = {
            {'·', '·', '·'},
            {'X', 'X', 'X'},
            {'·', '·', '·'}
        };
        boolean guanya = TresEnRatlla.jugadorGuanya(taulell , 'O');
        assertFalse(guanya, "Revisa jugadorGuanya() quan l'altre ha marcat la fila 1");
    }

    @Test
    @DisplayName("test jugadorGuanya() el contrari fa fila 2")
    public void jugadorGuanyaTaulellFila2AltreJugador() {
        char[][] taulell = {
            {'·', '·', '·'},
            {'·', '·', '·'},
            {'X', 'X', 'X'}
        };
        boolean guanya = TresEnRatlla.jugadorGuanya(taulell , 'O');
        assertFalse(guanya, "Revisa jugadorGuanya() quan l'altre ha marcat la fila 2");
    }


    @Test
    @DisplayName("test jugadorGuanya() el contrari fa columna 0")
    public void jugadorGuanyaTaulellColumna0AltreJugador() {
        char[][] taulell = {
            {'X', '·', '·'},
            {'X', '·', '·'},
            {'X', '·', '·'}
        };
        boolean guanya = TresEnRatlla.jugadorGuanya(taulell , 'O');
        assertFalse(guanya, "Revisa jugadorGuanya() quan l'altre ha marcat la columna 0");
    }

    @Test
    @DisplayName("test jugadorGuanya() el contrari fa columna 1")
    public void jugadorGuanyaTaulellColumna1AltreJugador() {
        char[][] taulell = {
            {'·', 'X', '·'},
            {'·', 'X', '·'},
            {'·', 'X', '·'}
        };
        boolean guanya = TresEnRatlla.jugadorGuanya(taulell , 'O');
        assertFalse(guanya, "Revisa jugadorGuanya() quan l'altre ha marcat la columna 1");
    }

    @Test
    @DisplayName("test jugadorGuanya() el contrari fa columna 2")
    public void jugadorGuanyaTaulellColumna2AltreJugador() {
        char[][] taulell = {
            {'·', '·', 'X'},
            {'·', '·', 'X'},
            {'·', '·', 'X'}
        };
        boolean guanya = TresEnRatlla.jugadorGuanya(taulell , 'O');
        assertFalse(guanya, "Revisa jugadorGuanya() quan l'altre ha marcat la columna 2");
    }

    @Test
    @DisplayName("test jugadorGuanya() el contrari fa diagona 0")
    public void jugadorGuanyaTaulellDiagonal0AltreJugador() {
        char[][] taulell = {
            {'X', '·', '·'},
            {'·', 'X', '·'},
            {'·', '·', 'X'}
        };
        boolean guanya = TresEnRatlla.jugadorGuanya(taulell , 'O');
        assertFalse(guanya, "Revisa jugadorGuanya() quan l'altre ha marcat la diagonal \\");
    }

    @Test
    @DisplayName("test jugadorGuanya() el contrari fa diagona 1")
    public void jugadorGuanyaTaulellDiagonal1AltreJugador() {
        char[][] taulell = {
            {'·', '·', 'X'},
            {'·', 'X', '·'},
            {'X', '·', '·'}
        };
        boolean guanya = TresEnRatlla.jugadorGuanya(taulell , 'O');
        assertFalse(guanya, "Revisa jugadorGuanya() quan l'altre ha marcat la diagonal /");
    }

    @Test
    @DisplayName("test hiHaEmpat() quan hi ha")
    public void hiHaEmpatQuanHiHa() {
        char[][] taulell = {
            {'O', 'X', 'X'},
            {'O', 'X', 'O'},
            {'X', 'O', 'X'}
        };
        boolean empat = TresEnRatlla.hiHaEmpat(taulell);
        assertTrue(empat , "Revisa hiHaEmpat() quan totes les caselles estan ocupades");
    }

    @Test
    @DisplayName("test hiHaEmpat() quan queda encara un moviment")
    public void hiHaEmpatQuanNoHiHa() {
        char[][] taulell = {
            {'O', 'X', 'X'},
            {'O', 'X', '·'},
            {'X', 'O', 'X'}
        };
        boolean empat = TresEnRatlla.hiHaEmpat(taulell);
        assertFalse(empat , "Revisa hiHaEmpat() quan encara hi ha caselles sense ocupar");
    }

    @Test
    @DisplayName("test casellaOcupada() quan marcada per X")
    public void casellaOcupadaQuanX() {
        char[][] taulell = {
            {'·', '·', 'O'},
            {'X', '·', '·'},
            {'·', '·', '·'}
        };
        boolean ocupada = TresEnRatlla.casellaOcupada(taulell, 1, 0);
        assertTrue(ocupada , "Revisa casellaOcupada() quan està ocupada");
    }

    @Test
    @DisplayName("test casellaOcupada() quan marcada per O")
    public void casellaOcupadaQuanO() {
        char[][] taulell = {
            {'·', '·', 'O'},
            {'X', '·', '·'},
            {'·', '·', '·'}
        };
        boolean ocupada = TresEnRatlla.casellaOcupada(taulell, 0, 2);
        assertTrue(ocupada , "Revisa casellaOcupada() quan està ocupada");
    }

    @Test
    @DisplayName("test casellaOcupada() quan lliure")
    public void casellaOcupadaQuanLliure() {
        char[][] taulell = {
            {'·', '·', 'O'},
            {'X', '·', '·'},
            {'·', '·', '·'}
        };
        boolean ocupada = TresEnRatlla.casellaOcupada(taulell, 2, 2);
        assertFalse(ocupada , "Revisa casellaOcupada() quan està lliure");
    }

    @Test
    @DisplayName("Test hi és mòdul mostraTaulell()")
    public void prgtestX1mostraTaulell() {
        Class classe = TresEnRatlla.class;
        String modulObjectiu = "mostraTaulell";
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
                    "char[][]",
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