/*
    Unit testing methods for exercise 03_06_traduccio
*/
import java.io.IOException;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestExercise {
    private static final String MISSATGE = String.format("missatge a mostrar");

    @Test
    @DisplayName("Test error()")
    public void error() throws IOException{
        String expected = "[1] ERROR: " + MISSATGE;
        Log.reset();
        String found = Log.printError(MISSATGE);
        assertEquals(expected, found);
    }

    @Test
    @DisplayName("Test warning()")
    public void warning() throws IOException{
        String expected = "[1] WARNING: " + MISSATGE;
        Log.reset();
        String found = Log.printWarning(MISSATGE);
        assertEquals(expected, found);
    }

    @Test
    @DisplayName("Test info()")
    public void info() throws IOException{
        String expected = "[1] INFO: " + MISSATGE;
        Log.reset();
        String found = Log.printInfo(MISSATGE);
        assertEquals(expected, found);
    }

    @Test
    @DisplayName("Test debug()")
    public void debug() throws IOException{
        String expected = "[1] DEBUG: " + MISSATGE;
        Log.reset();
        String found = Log.printDebug(MISSATGE);
        assertEquals(expected, found);
    }

    @Test
    @DisplayName("Test vàries crides")
    public void varis() throws IOException{
        String expected = "[10] DEBUG: " + MISSATGE;
        String found = "";
        Log.reset();
        for (int i=0; i<10; i++) found = Log.printDebug(MISSATGE);
        assertEquals(expected, found);
    }
}