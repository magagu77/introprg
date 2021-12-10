/*
 * Unit testing methods for an exercise
 */

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.DisplayName;

public class TestExercise {

    @ParameterizedTest
    @CsvSource(value={
    "a, true", "à,true","e,true","è,true","é,true","i,true","í,true","ï,true","o,true","ó,true","ò,true","u,true","ú,true","ü,true",
    "A, true", "À,true","E,true","È,true","É,true","I,true","Í,true","Ï,true","O,true","Ó,true","Ò,true","U,true","Ú,true","Ü,true",
    "c,false","d,false","f,false","j,false"})
    @DisplayName("test trobaOcurrencies()")
    public void esVocal(char lletra, boolean esperat) {
        boolean obtingut = UtilString.esVocal(lletra);
        assertEquals(esperat, obtingut, "Revisa la crida esVocal('" + lletra + "')");
    }

}
