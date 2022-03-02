import java.lang.reflect.Method;
import java.lang.reflect.Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestExercise {

    @Test
    @DisplayName("test hora per defecte")
    public void testHoraPerDefecte000() {
        Hora hora = new Hora();
        assertAll("testHoraPerDefecte000",
                () -> assertEquals(0, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(0, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(0, hora.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test hora constructor específic")
    public void testHoraConstructorEspecific() {
        Hora hora = new Hora(1, 2, 3);
        assertAll("testHoraConstructorEspecific",
                () -> assertEquals(1, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(2, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(3, hora.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test hora quan valors al límit superior")
    public void testHoraConstrueixCorrecteQuanValorsAmbLimitSuperior() {
        Hora hora = new Hora(23, 59, 59);
        assertAll("testHoraConstrueixCorrecteQuanValorsAmbLimitSuperior",
                () -> assertEquals(23, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(59, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(59, hora.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test hora quan hores no vàlides")
    public void testHoraConstrueixPerDefecteQuanValorNoValidPerHores() {
        Hora hora = new Hora(24, 2, 3);
        assertAll("testHoraConstrueixPerDefecteQuanValorNoValidPerHores",
                () -> assertEquals(0, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(0, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(0, hora.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test hora quan hores no vàlides")
    public void testHoraConstrueixPerDefecteQuanValorNegatiuPerHores() {
        Hora hora = new Hora(-24, 2, 3);
        assertAll("testHoraConstrueixPerDefecteQuanValorNegatiuPerHores",
                () -> assertEquals(0, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(0, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(0, hora.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test hora quan minuts no vàlids")
    public void testHoraConstrueixPerDefecteQuanValorNoValidPerMinuts() {
        Hora hora = new Hora(1, 60, 3);
        assertAll("testHoraConstrueixPerDefecteQuanValorNoValidPerMinuts",
                () -> assertEquals(0, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(0, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(0, hora.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test hora quan minuts no vàlids")
    public void testHoraConstrueixPerDefecteQuanValorNegatiuPerMinuts() {
        Hora hora = new Hora(1, -6, 3);
        assertAll("testHoraConstrueixPerDefecteQuanValorNegatiuPerMinuts",
                () -> assertEquals(0, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(0, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(0, hora.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test hora quan segons no vàlids")
    public void testHoraConstrueixPerDefecteQuanValorNoValidPerSegons() {
        Hora hora = new Hora(1, 2, 60);
        assertAll("testHoraConstrueixPerDefecteQuanValorNoValidPerSegons",
                () -> assertEquals(0, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(0, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(0, hora.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test hora quan segons no vàlids")
    public void testHoraConstrueixPerDefecteQuanValorNegatiuPerSegons() {
        Hora hora = new Hora(1, 2, -1);
        assertAll("testHoraConstrueixPerDefecteQuanValorNegatiuPerSegons",
                () -> assertEquals(0, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(0, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(0, hora.getSegons(), "Respecte als segons")
                );
    }


    @Test
    @DisplayName("test hora increment un segon")
    public void testHoraIncrementaCasEstandard() {
        Hora hora = new Hora(1, 2, 3);

        hora.incrementa();

        assertAll("testHoraIncrementaCasEstandard",
                () -> assertEquals(1, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(2, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(4, hora.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test hora increment següent minut")
    public void testHoraIncrementaCasIncrementaMinut() {
        Hora hora = new Hora(1, 2, 59);

        hora.incrementa();

        assertAll("testHoraIncrementaCasIncrementaMinut",
                () -> assertEquals(1, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(3, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(0, hora.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test hora increment següent hora")
    public void testHoraIncrementaCasIncrementaHora() {
        Hora hora = new Hora(1, 59, 59);

        hora.incrementa();

        assertAll("testHoraIncrementaCasIncrementaHora",
                () -> assertEquals(2, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(0, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(0, hora.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test hora increment reinicia")
    public void testHoraIncrementaCasReinicia() {
        Hora hora = new Hora(23, 59, 59);

        hora.incrementa();

        assertEquals(0, hora.getHores(), "Respecte a les hores");
        assertEquals(0, hora.getMinuts(), "Respecte als minuts");
        assertEquals(0, hora.getSegons(), "Respecte als segons");
    }

    @Test
    @DisplayName("test hora increment n cas estàndard")
    public void testHoraIncrementaNCasEstandard() {
        Hora hora = new Hora(1, 2, 3);

        hora.incrementa(2);

        assertAll("testHoraIncrementaNCasEstandard",
                () -> assertEquals(1, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(2, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(5, hora.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test hora increment n cas incrementa minuts")
    public void testHoraIncrementaNCasIncrementaMinut() {
        Hora hora = new Hora(1, 2, 3);

        hora.incrementa(59);

        assertAll("testHoraIncrementaNCasIncrementaMinut",
                () -> assertEquals(1, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(3, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(2, hora.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test hora increment n cas incrementa hores")
    public void testHoraIncrementaNCasIncrementaHora() {
        Hora hora = new Hora(1, 2, 3);

        hora.incrementa(3539);

        assertAll("testHoraIncrementaNCasIncrementaHora",
                () -> assertEquals(2, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(1, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(2, hora.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test hora increment n cas reinicia")
    public void testHoraIncrementaNCasReinicia() {
        Hora hora = new Hora(1, 2, 3);

        hora.incrementa(82739);

        assertAll("testHoraIncrementaNCasReinicia",
                () -> assertEquals(0, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(1, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(2, hora.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test hora increment n cas n és gran")
    public void testHoraIncrementaNCasGranN() {
        Hora hora = new Hora(0, 0, 0);

        hora.incrementa(172737);

        assertAll("testHoraIncrementaNCasGranN",
                () -> assertEquals(23, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(58, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(57, hora.getSegons(), "Respecte als segons")
                );
    }

    // test compareTo
    @Test
    @DisplayName("test hora compareTo() quan menor-major")
    public void testCompareToReconeixMenorMajor() {
        Hora horaMenor = new Hora(1, 2, 3);
        Hora horaMajor = new Hora(1, 2, 4);

        assertTrue(horaMenor.compareTo(horaMajor)<0);
    }

    @Test
    @DisplayName("test hora compareTo() quan major-menor")
    public void testCompareToReconeixMajorMenor() {
        Hora horaMenor = new Hora(1, 2, 3);
        Hora horaMajor = new Hora(1, 2, 4);

        assertTrue(horaMajor.compareTo(horaMenor)>0);
    }

    @Test
    @DisplayName("test hora compareTo() quan iguals")
    public void testCompareToReconeixHoresIguals() {
        Hora hora1 = new Hora(1, 2, 3);
        Hora hora2 = new Hora(1, 2, 3);

        assertEquals(0, hora1.compareTo(hora2));
    }

    // Test decrementa
    @Test
    @DisplayName("test hora incrementa amb segons negatius")
    public void testHoraIncrementaSegonsNegatius() {
        Hora hora = new Hora(1, 2, 4);

        hora.incrementa(-1);

        assertAll("testHoraIncrementaSegonsNegatius",
                () -> assertEquals(1, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(2, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(3, hora.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test hora decrementa estàndard")
    public void testHoraDecrementaCasEstandard() {
        Hora hora = new Hora(1, 2, 4);

        hora.decrementa();

        assertAll("testHoraDecrementaCasEstandard",
                () -> assertEquals(1, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(2, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(3, hora.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test hora decrementa quan canvia minuts")
    public void testHoraDecrementaCasDecrementaMinut() {
        Hora hora = new Hora(0, 2, 3);


        hora.decrementa(60);
        assertAll("testHoraDecrementaCasDecrementaMinut",
                () -> assertEquals(0, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(1, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(3, hora.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test hora decrementa quan canvia hores")
    public void testHoraDecrementaCasDecrementaHora() {
        Hora hora = new Hora(1, 2, 3);

        hora.decrementa(3600);

        assertAll("testHoraDecrementaCasDecrementaHora",
                () -> assertEquals(0, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(2, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(3, hora.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test hora decrementa quan reinicia")
    public void testHoraDecrementaCasReinicia() {
        Hora hora = new Hora(0, 0, 0);

        hora.decrementa(1);

        assertAll("testHoraDecrementaCasReinicia",
                () -> assertEquals(23, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(59, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(59, hora.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test hora decrementa N cas estàndard")
    public void testHoraDecrementaNCasEstandard() {
        Hora hora = new Hora(1, 2, 5);

        hora.decrementa(2);

        assertAll("testHoraDecrementaNCasEstandard",
                () -> assertEquals(1, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(2, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(3, hora.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test hora decrementa N cas decrementa minut")
    public void testHoraDecrementaNCasDecrementaMinut() {
        Hora hora = new Hora(3, 2, 4);

        hora.decrementa(60);

        assertAll("testHoraDecrementaNCasDecrementaMinut",
                () -> assertEquals(3, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(1, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(4, hora.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test hora decrementa N cas decrementa hora")
    public void testHoraDecrementaNCasDecrementaHora() {
        Hora hora = new Hora(1, 2, 3);

        hora.decrementa(3600);

        assertAll("testHoraDecrementaNCasDecrementaHora",
                () -> assertEquals(0, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(2, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(3, hora.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test hora decrementa N cas reinicia")
    public void testHoraDecrementaNCasReinicia() {
        Hora hora = new Hora(0, 0, 0);

        hora.decrementa(86399);

        assertAll("testHoraDecrementaNCasReinicia",
                () -> assertEquals(0, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(0, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(1, hora.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test hora decrementa N cas valor gran")
    public void testHoraDecrementaNCasGranN() {
        Hora hora = new Hora(0, 0, 0);

        hora.decrementa(432001);

        assertAll("testHoraDecrementaNCasGranN",
                () -> assertEquals(23, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(59, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(59, hora.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test hora decrementa N cas valor negatiu")
    public void testHoraDecrementaNegatiu() {
        Hora hora = new Hora(1, 2, 3);

        hora.decrementa(-1);

        assertAll("testHoraDecrementaNegatiu",
                () -> assertEquals(1, hora.getHores(), "Respecte a les hores"),
                () -> assertEquals(2, hora.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(4, hora.getSegons(), "Respecte als segons")
                );
    }

    //  test de toString()
    @Test
    @DisplayName("test hora toString() cas general")
    public void testHoraToStringGeneral() {
        Hora hora = new Hora(11, 21, 31);
        String expected = "11:21:31";
        assertEquals(expected, hora.toString());
    }

    @Test
    @DisplayName("test hora toString() amb zeros")
    public void testHoraToStringLeadingZeros() {
        Hora hora = new Hora(1, 2, 3);
        String expected = "1:02:03";
        assertEquals(expected, hora.toString());
    }

    // duplica d'instància
    @Test
    @DisplayName("test duplica() retorna els mateixos valors")
    public void testDuplicaHoraResultatAmbMateixosValors() {
        Hora hora = new Hora(1, 2, 3);
        Hora duplicada = hora.duplica();
        assertAll("testDuplicaHoraResultatAmbMateixosValors",
                () -> assertEquals(hora.getHores(), duplicada.getHores(), "Respecte a les hores"),
                () -> assertEquals(hora.getMinuts(), duplicada.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(hora.getSegons(), duplicada.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test duplica() genera una nova instància")
    public void testDuplicaHoraResultatEsUnaAltraInstancia() {
        Hora hora = new Hora(1, 2, 3);
        Hora duplicada = hora.duplica();

        assertFalse(hora == duplicada, "L'hora duplicada no ha de ser la mateixa instància");
    }


    // test dels mètodes estàtics

    @Test
    @DisplayName("test valors hora vàlids")
    public void testHoraEsValidaAmbValorsValids() {
        assertTrue(Hora.esValida(1, 2, 3));
    }

    @Test
    @DisplayName("test valors hora no vàlids per massa hores")
    public void testHoraEsValidaAmbMassaHores() {
        assertFalse(Hora.esValida(24, 2, 3), "Les hores no són vàlides");
    }

    @Test
    @DisplayName("test valors hora no vàlids per massa poques hores")
    public void testHoraEsValidaAmbMassaPoquesHores() {
        assertFalse(Hora.esValida(-1, 2, 3), "Les hores no són vàlides");
    }

    @Test
    @DisplayName("test valors hora no vàlids per massa minuts")
    public void testHoraEsValidaAmbMassaMinuts() {
        assertFalse(Hora.esValida(1, 60, 3), "Els minuts no són vàlids");
    }

    @Test
    @DisplayName("test valors hora no vàlids per massa pocs minuts")
    public void testHoraEsValidaAmbMassaPocsMinuts() {
        assertFalse(Hora.esValida(1, -1, 3), "Els minuts no són vàlids");
    }

    @Test
    @DisplayName("test valors hora no vàlids per massa segons")
    public void testHoraEsValidaAmbMassaSegons() {
        assertFalse(Hora.esValida(1, 2, 60), "Els segons no són vàlids");
    }

    @Test
    @DisplayName("test valors hora no vàlids per massa pocs segons")
    public void testHoraEsValidaAmbMassaPocsSegons() {
        assertFalse(Hora.esValida(1, 2, -1), "Els segons no són vàlids");
    }

    @Test
    @DisplayName("test compareTo() reconeix menor-major")
    public void testCompareToStaticReconeixMenorMajor() {
        Hora horaMenor = new Hora(1, 2, 3);
        Hora horaMajor = new Hora(1, 2, 4);

        assertTrue(Hora.compareTo(horaMenor, horaMajor)<0);
    }

    @Test
    @DisplayName("test compareTo() reconeix major-menor")
    public void testCompareToStaticReconeixMajorMenor() {
        Hora horaMenor = new Hora(1, 2, 3);
        Hora horaMajor = new Hora(1, 2, 4);

        assertTrue(Hora.compareTo(horaMajor, horaMenor)>0);
    }

    @Test
    @DisplayName("test compareTo() reconeix iguals")
    public void testCompareToStaticReconeixHoresIguals() {
        Hora hora1 = new Hora(1, 2, 3);
        Hora hora2 = new Hora(1, 2, 3);

        assertEquals(0, Hora.compareTo(hora1, hora2));
    }

    @Test
    @DisplayName("test duplica() retorna els mateixos valors")
    public void testDuplicaStaticHoraResultatAmbMateixosValors() {
        Hora hora = new Hora(1, 2, 3);
        Hora duplicada = Hora.duplica(hora);
        assertAll("testDuplicaHoraResultatAmbMateixosValors",
                () -> assertEquals(hora.getHores(), duplicada.getHores(), "Respecte a les hores"),
                () -> assertEquals(hora.getMinuts(), duplicada.getMinuts(), "Respecte als minuts"),
                () -> assertEquals(hora.getSegons(), duplicada.getSegons(), "Respecte als segons")
                );
    }

    @Test
    @DisplayName("test duplica() genera una nova instància")
    public void testDuplicaStaticHoraResultatEsUnaAltraInstancia() {
        Hora hora = new Hora(1, 2, 3);
        Hora duplicada = Hora.duplica(hora);

        assertFalse(hora == duplicada, "L'hora duplicada no ha de ser la mateixa instància");
    }



}