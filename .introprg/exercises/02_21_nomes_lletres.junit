/*
 * Unit testing methods for an exercise
 */

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.DisplayName;

public class TestExercise {

    @ParameterizedTest
    @CsvSource(value={ "'',''", "a,a", "ab,'a, b'","abc,'a, b, c'", "abcdef,'a, b, c, d, e, f'" })
    @DisplayName("test lletresSeparades()")
    public void lletresSeparades(String text, String esperat) {
        String obtingut = UtilString.lletresSeparades(text);
        assertEquals(esperat, obtingut,
                "Revisa la crida lletresSeparades(\"" + text + "\")");
    }

    @ParameterizedTest
    @CsvSource(value={ 
        "'',''", "a,a", "ab,ab","aBc,aBc", "aBcDeF,aBcDeF",
        "1,''", "123,''", "1a1,a", "a1b2c3,abc","*.;:,''"
    })
    @DisplayName("test nomesLletres()")
    public void nomesLletres(String text, String esperat) {
        String obtingut = UtilString.nomesLletres(text);
        assertEquals(esperat, obtingut,
                "Revisa la crida nomesLletres(\"" + text + "\")");
    }

}
