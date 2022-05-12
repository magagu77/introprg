import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestFBB {
    @Test 
    public void quanEntro1Espero1() {
        int input = 1;
        String expected = "1";
        String found = FBBEvaluator.eval(input);
        assertEquals(expected, found);
    }
    @Test 
    public void quanEntro2Espero2() {
        int input = 2;
        String expected = "2";
        String found = FBBEvaluator.eval(input);
        assertEquals(expected, found);
    }
    @Test
    public void quanEntro4Espero4() {
        int input = 4;
        String expected = "4";
        String found = FBBEvaluator.eval(input);
        assertEquals(expected, found);
    }
    @Test
    public void quanEntro8Espero8() {
        int input = 13;
        String expected = "13";
        String found = FBBEvaluator.eval(input);
        assertEquals(expected, found);
    }
    @Test
    public void quanEntro13Espero13() {
        int input = 13;
        String expected = "13";
        String found = FBBEvaluator.eval(input);
        assertEquals(expected, found);
    }
    @ParameterizedTest
    @CsvSource ({
        "1","2","4","8","11","13","16","17","19"
    })
    public void quanEntroNEsperoN(int input) {
        String expected = Integer.toString(input);
        String found = FBBEvaluator.eval(input);
        assertEquals(expected, found);
    }
}