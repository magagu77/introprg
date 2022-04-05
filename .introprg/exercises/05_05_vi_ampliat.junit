import java.lang.reflect.Type;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

public class TestExercise {

    /* ************************************************************************* *
     *  Test de Vi                                                               *
     * ************************************************************************* */
    @Nested
    @DisplayName("Tests de Vi")
    class SobreVi {

        @ParameterizedTest
        @CsvSource(value={
        "'   nom',nom",
        "'nom   ',nom",
        "'nom   cognom1   cognom2', 'nom cognom1 cognom2'",
        "'nom cognom1 cognom2', 'nom cognom1 cognom2'",
        "'  Nom  Cognom1 Cognom2 ', 'Nom Cognom1 Cognom2'",
        "null,null",
        "'', null",
        "'   ', null"
        }, nullValues={"null"})
        @DisplayName("test Vi.normalitzaString()")
        public void normalitzaString(String nom, String esperat) {
            String obtingut = Vi.normalitzaString(nom);
            String msg = String.format("Vi.normalitzaString(\"%s\") s'esperava \"%s\" però s'ha trobat \"%s\"", nom, esperat, obtingut);
            assertEquals(esperat, obtingut, msg);
        }

        @Test
        @DisplayName("test Vi(String, int) normalitza nom")
        public void constructorNormalitzaValors() {
            String ref = "      ROURABLA20200232           ";
            String nom = "    Supervi     superbe          ";
            int preu = -23;
            int estoc = -54;
            String lloc = "   P02E45N55D    ";
            String origen = "    Pla    del    Bages          ";
            String tipus = "    blanc      ";
            String collita = "    2022          ";
            Vi vi = new Vi(ref, nom, preu, estoc, lloc, origen, tipus, collita);

            String refEsperat = "ROURABLA20200232";
            String nomEsperat = "Supervi superbe";
            int preuEsperat = -1;
            int estocEsperat = -1;
            String llocEsperat = "P02E45N55D";
            String origenEsperat = "Pla del Bages";
            String tipusEsperat = "blanc";
            String collitaEsperat = "2022";
            Vi viEsperat = new Vi(refEsperat, nomEsperat, preuEsperat, estocEsperat, llocEsperat, origenEsperat, tipusEsperat, collitaEsperat);
            assertVinsIguals(viEsperat, vi, "Vi() ha de normalitzar els valors");
        }

        @ParameterizedTest
        @CsvSource(value={
        "1,1",
        "10,10",
        "-1,1012",
        "-10,1012"
        })
        @DisplayName("test Vi.setPreu(int) controla preu")
        public void setPreu(int preu, int esperat) {
            Vi vi = unViValid();
            vi.setPreu(preu);
            String msg = String.format("setPreu(%d) s'esperava preu \"%d\" però s'ha trobat \"%d\"", preu, esperat, vi.getPreu());
            assertEquals(esperat, vi.getPreu(), msg);
        }

        @ParameterizedTest
        @CsvSource(value={
        "1,1",
        "10,10",
        "-1,42",
        "-10,42"
        })
        @DisplayName("test Vi.setEstoc(int) controla estoc")
        public void setEstoc(int estoc, int esperat) {
            Vi vi = unViValid();
            vi.setEstoc(estoc);
            String msg = String.format("setEstoc(%d) s'esperava estoc \"%d\" però s'ha trobat \"%d\"", estoc, esperat, vi.getEstoc());
            assertEquals(esperat, vi.getEstoc(), msg);
        }

        @ParameterizedTest
        @CsvSource(value={
        "unlloc,unlloc",
        "'  un  lloc ','un lloc'",
        "'',P21E45N55E",
        "'  ',P21E45N55E",
        "null,P21E45N55E",
        }, nullValues={"null"})
        @DisplayName("test Vi.setLloc(String) controla lloc")
        public void setLloc(String entrat, String esperat) {
            Vi vi = unViValid();
            vi.setLloc(entrat);
            String msg = String.format("setLloc(%s) s'esperava lloc \"%s\" però s'ha trobat \"%s\"", entrat, esperat, vi.getLloc());
            assertEquals(esperat, vi.getLloc(), msg);
        }

        // esValid()
        @Test
        @DisplayName("test esValid() quan tot vàlid")
        public void esValidQuanValid() {
            assertTrue(unViValid().esValid(), "esValid() ha de retornar true quan tots els valors són vàlids");
        }

        @ParameterizedTest
        @CsvSource(value={
        "'','Matís Negre',1325,12,P20E01N12E,'Pla del Bages',negre,2019,ref,''",
        "'   ','Matís Negre',1325,12,P20E01N12E,'Pla del Bages',negre,2019,ref,'   '",
        "null,'Matís Negre',1325,12,P20E01N12E,'Pla del Bages',negre,2019,ref,null",
        "MATISNEG20190011,'',1325,12,P20E01N12E,'Pla del Bages',negre,2019,nom,''",
        "MATISNEG20190011,'   ',1325,12,P20E01N12E,'Pla del Bages',negre,2019,nom,'    '",
        "MATISNEG20190011,null,1325,12,P20E01N12E,'Pla del Bages',negre,2019,nom,null",
        "MATISNEG20190011,'Matís Negre',-1325,12,P20E01N12E,'Pla del Bages',negre,2019,preu,'-1325'",
        "MATISNEG20190011,'Matís Negre',1325,-12,P20E01N12E,'Pla del Bages',negre,2019,estoc,'-12'",
        "MATISNEG20190011,'Matís Negre',1325,12,'','Pla del Bages',negre,2019,lloc,''",
        "MATISNEG20190011,'Matís Negre',1325,12,'    ','Pla del Bages',negre,2019,lloc,'   '",
        "MATISNEG20190011,'Matís Negre',1325,12,null,'Pla del Bages',negre,2019,lloc,null",
        "MATISNEG20190011,'Matís Negre',1325,12,P20E01N12E,'',negre,2019,origen,''",
        "MATISNEG20190011,'Matís Negre',1325,12,P20E01N12E,'     ',negre,2019,origen,'   '",
        "MATISNEG20190011,'Matís Negre',1325,12,P20E01N12E,null,negre,2019,origen,null",
        "MATISNEG20190011,'Matís Negre',1325,12,P20E01N12E,'Pla del Bages','',2019,tipus,''",
        "MATISNEG20190011,'Matís Negre',1325,12,P20E01N12E,'Pla del Bages','    ',2019,tipus,'   '",
        "MATISNEG20190011,'Matís Negre',1325,12,P20E01N12E,'Pla del Bages',null,2019,tipus,null",
        "MATISNEG20190011,'Matís Negre',1325,12,P20E01N12E,'Pla del Bages',negre,'',collita,'   '",
        "MATISNEG20190011,'Matís Negre',1325,12,P20E01N12E,'Pla del Bages',negre,'   ',collita,'   '",
        "MATISNEG20190011,'Matís Negre',1325,12,P20E01N12E,'Pla del Bages',negre,null,collita,'   '",
        }, nullValues={"null"})
        @DisplayName("test esValid() quan valor no vàlid")
        public void esValidQuanValorNoValid(
                String ref, String nom,
                int preu, int estoc,
                String lloc, String origen,
                String tipus, String collita,
                String atribut, String valor) {
            Vi vi = new Vi(ref, nom, preu, estoc, lloc, origen, tipus, collita);
            String msg = String.format("esValid() ha de retornar false quan l'atribut %s té valor %s",
                    atribut, valor);
            assertFalse(vi.esValid(), msg);
        }
    }

    /* ************************************************************************* *
     *  Test de serialització de Vi                                              *
     * ************************************************************************* */
    @Nested
    @DisplayName("Tests de serialització de Vi")
    class SobreSerialitzacioVi {

        // aArrayString()
        @Test
        @DisplayName("test vi.aArrayString()")
        public void viAArrayString() {
            Vi vi = unViValid();
            String[] esperat = {
                "ROURABLA20200232", "Roura blanc",
                "1012", "42", "P21E45N55E", "Alella",
                "blanc", "2020"};
            String[] obtingut = vi.aArrayString();
            assertArrayEquals(esperat, obtingut, "revisa vi.aArrayString()");
        }

        // deArrayString()
        @Test
        @DisplayName("test vi.deArrayString() quan se li passa una línia vàlida")
        public void viDeArrayStringQuanCorrecte() {
            String[] entrada = {
                "ROURABLA20200232", "Roura blanc",
                "1012", "42", "P21E45N55E", "Alella",
                "blanc", "2020"};
            Vi vi = Vi.deArrayString(entrada);
            Vi viEsperat = unViValid();
            assertVinsIguals(viEsperat, vi, "deArrayString() no converteix adequadament");
        }


        @ParameterizedTest
        @CsvSource(value={
        "'','línia buida'",
        "'Matís Negre;1325;12;P20E01N12E;Pla del Bages;negre;2019','massa pocs elements'",
        "'MATISNEG20190011;Matís Negre;1325;12;P20E01N12E;Pla del Bages;negre;2019;un extra','massa elements'",
        "';Matís Negre;1325;12;P20E01N12E;Pla del Bages;negre;2019','referència buida'",
        "'   ;Matís Negre;1325;12;P20E01N12E;Pla del Bages;negre;2019','referència en blanc'",
        "'MATISNEG20190011;;1325;12;P20E01N12E;Pla del Bages;negre;2019','nom buit'",
        "'MATISNEG20190011;   ;1325;12;P20E01N12E;Pla del Bages;negre;2019','nom en blanc'",
        "'MATISNEG20190011;Matís Negre;;12;P20E01N12E;Pla del Bages;negre;2019','preu en blanc'",
        "'MATISNEG20190011;Matís Negre;car;12;P20E01N12E;Pla del Bages;negre;2019','preu no numèric'",
        "'MATISNEG20190011;Matís Negre;-13;12;P20E01N12E;Pla del Bages;negre;2019','preu negatiu'",
        "'MATISNEG20190011;Matís Negre;1223;;P20E01N12E;Pla del Bages;negre;2019','estoc en blanc'",
        "'MATISNEG20190011;Matís Negre;1223;poc;P20E01N12E;Pla del Bages;negre;2019','estoc no numèric'",
        "'MATISNEG20190011;Matís Negre;1234;-12;P20E01N12E;Pla del Bages;negre;2019','estoc negatiu'",
        "'MATISNEG20190011;Matís Negre;1325;12;;Pla del Bages;negre;2019','lloc buit'",
        "'MATISNEG20190011;Matís Negre;1325;12;    ;Pla del Bages;negre;2019','lloc en blanc'",
        "'MATISNEG20190011;Matís Negre;1325;12;P20E01N12E;;negre;2019','origen buit'",
        "'MATISNEG20190011;Matís Negre;1325;12;P20E01N12E;   ;negre;2019','origen en blanc'",
        "'MATISNEG20190011;Matís Negre;1325;12;P20E01N12E;Pla del Bages;;2019','tipus buit'",
        "'MATISNEG20190011;Matís Negre;1325;12;P20E01N12E;Pla del Bages;   ;2019','tipus en blanc'",
        "'MATISNEG20190011;Matís Negre;1325;12;P20E01N12E;Pla del Bages;negre;','collita buida'",
        "'MATISNEG20190011;Matís Negre;1325;12;P20E01N12E;Pla del Bages;negre;    ','collita en blanc'"
        })
        @DisplayName("test Vi.deArrayString() quan se li passa una línia no vàlida")
        public void viDeArrayStringIncorrecte(String linia, String missatge) {
            String msg = "deArrayString() ha de retornar null quan rep: " + missatge;
            assertNull(Vi.deArrayString(linia.split(";")), msg);
        }

        // toString()
        @Test
        @DisplayName("test Vi.toString()")
        public void ViToString() {
            Vi vi = unViValid();
            String esperat = String.format(
                    "%n    Ref: ROURABLA20200232" +
                    "%n    Nom: Roura blanc" +
                    "%n    Preu: 1012" +
                    "%n    Estoc: 42" +
                    "%n    Lloc: P21E45N55E" +
                    "%n    D.O.: Alella" +
                    "%n    Tipus: blanc" +
                    "%n    Collita: 2020%n");
            assertEquals(esperat, vi.toString(), "Revisa el mètode toString()");
        }

    }

    /* ************************************************************************* *
     *  Test de Botiga                                                           *
     * ************************************************************************* */
    @Nested
    @DisplayName("Tests de Botiga")
    class SobreBotiga {

        // afegeix()
        @Test
        @DisplayName("test botiga afegeix nou vi")
        public void testAfegeix() {
            Vi vi = unViValid();
            Vi trobat = new Botiga().afegeix(vi);
            assertVinsIguals(vi, trobat, "afegeix() ha de retornar el vi afegit");
        }

        @Test
        @DisplayName("test botiga no afegeix vi amb nom repetit")
        public void testNoRepetits() {
            Vi vi = unViValid();
            Vi viRepetit = unViValid();
            Botiga botiga = new Botiga();
            botiga.afegeix(vi);
            Vi trobat = botiga.afegeix(viRepetit);
            assertNull(trobat, "afegeix() no ha d'afegir un vi amb nom repetit");
        }

        @Test
        @DisplayName("test botiga no afegeix vi quan no queda espai")
        public void testNoEspai() {
            Vi[] vins = vinsValids();
            Botiga botiga = new Botiga(1);
            botiga.afegeix(vins[0]);
            Vi trobat = botiga.afegeix(vins[1]);
            assertNull(trobat, "Quan la botiga està plena afegeix() no ha d'afegir un nou vi");
        }

        @ParameterizedTest
        @CsvSource(value={
        "'','Matís Negre',1325,12,P20E01N12E,'Pla del Bages',negre,2019,ref",
        "'   ','Matís Negre',1325,12,P20E01N12E,'Pla del Bages',negre,2019,ref",
        "null,'Matís Negre',1325,12,P20E01N12E,'Pla del Bages',negre,2019,ref",
        "MATISNEG20190011,'',1325,12,P20E01N12E,'Pla del Bages',negre,2019,nom",
        "MATISNEG20190011,'   ',1325,12,P20E01N12E,'Pla del Bages',negre,2019,nom",
        "MATISNEG20190011,null,1325,12,P20E01N12E,'Pla del Bages',negre,2019,nom",
        "MATISNEG20190011,'Matís Negre',-1325,12,P20E01N12E,'Pla del Bages',negre,2019,preu",
        "MATISNEG20190011,'Matís Negre',1325,-12,P20E01N12E,'Pla del Bages',negre,2019,estoc",
        "MATISNEG20190011,'Matís Negre',1325,12,'','Pla del Bages',negre,2019,lloc",
        "MATISNEG20190011,'Matís Negre',1325,12,'    ','Pla del Bages',negre,2019,lloc",
        "MATISNEG20190011,'Matís Negre',1325,12,null,'Pla del Bages',negre,2019,lloc",
        "MATISNEG20190011,'Matís Negre',1325,12,P20E01N12E,'',negre,2019,origen",
        "MATISNEG20190011,'Matís Negre',1325,12,P20E01N12E,'     ',negre,2019,origen",
        "MATISNEG20190011,'Matís Negre',1325,12,P20E01N12E,null,negre,2019,origen",
        "MATISNEG20190011,'Matís Negre',1325,12,P20E01N12E,'Pla del Bages','',2019,tipus",
        "MATISNEG20190011,'Matís Negre',1325,12,P20E01N12E,'Pla del Bages','    ',2019,tipus",
        "MATISNEG20190011,'Matís Negre',1325,12,P20E01N12E,'Pla del Bages',null,2019,tipus",
        "MATISNEG20190011,'Matís Negre',1325,12,P20E01N12E,'Pla del Bages',negre,'',collita",
        "MATISNEG20190011,'Matís Negre',1325,12,P20E01N12E,'Pla del Bages',negre,'   ',collita",
        "MATISNEG20190011,'Matís Negre',1325,12,P20E01N12E,'Pla del Bages',negre,null,collita",
        }, nullValues={"null"})
        @DisplayName("test botiga afegeix vi no vàlid")
        public void testAfegeixNoValidPerNom(
                String ref, String nom,
                int preu, int estoc,
                String lloc, String origen,
                String tipus, String collita,
                String atribut) {
            Vi vi = new Vi(ref, nom, preu, estoc, lloc, origen, tipus, collita);
            Vi trobat = new Botiga().afegeix(vi);
            String msg = String.format(
                    "afegeix() ha de retornar null quan el vi no és vàlid per l'atribut %s",
                    atribut);
            assertNull(trobat, msg);
        }

        // cerca(String)
        @Test
        @DisplayName("test botiga.cerca(string) troba vi per ref")
        public void trobavidirecteperref() {
            Vi[] vins = vinsValids();
            Vi cercat = vins[3];
            Botiga botiga = botigaPlena(vins);
            String valorexacte = "SAOROSAT20180001";
            String valoranormalitzar = "    SAOROSAT20180001   ";
            String valorcase = "saorosat20180001";
            String valorinnexistent = "capvicomaquest";
            assertAll(
                    () -> assertVinsIguals(cercat, botiga.cerca(valorexacte), 
                                           "cerca(string) no troba un vi existent amb la referència exacta"),
                    () -> assertVinsIguals(cercat, botiga.cerca(valoranormalitzar), 
                                           "cerca(string) no troba un vi existent amb la referència no normalitzada"),
                    () -> assertVinsIguals(cercat, botiga.cerca(valorcase), 
                                           "cerca(string) no troba un vi existent amb la referència amb canvis majúscules/minúscules"),
                    () -> assertNull(botiga.cerca(valorinnexistent),
                                           "cerca(string) ha de retornar null quan no troba cap vi amb la referència demanada")
                    );
        }

        // cerca(Vi)
        @Test
        @DisplayName("test Botiga.cerca(Vi) troba vi per ref")
        public void trobaViPerRef() {
            Vi[] vins = vinsValids();
            Vi cercat = vins[6];
            Botiga botiga = botigaPlena(vins);
            String clau = "ref";
            String valorExacte = "CERCIUMX20170002";
            String valorANormalitzar = "    CERCIUMX20170002   ";
            String valorCase = "cerciumx20170002";
            String valorInnexistent = "Capvicomaquest";
            Vi plantillaExacte = new Vi(valorExacte, null, -1, -1, null, null, null, null);
            Vi plantillaNormalitzable = new Vi(valorANormalitzar, null, -1, -1, null, null, null, null);
            Vi plantillaCase = new Vi(valorCase, null, -1, -1, null, null, null, null);
            Vi plantillaInnexistent = new Vi(valorInnexistent, null, -1, -1, null, null, null, null);
            assertAll(
                    () -> assertVinsIguals(cercat, botiga.cerca(plantillaExacte), 
                                           String.format("cerca() no troba el primer vi quan es cerca per %s especificant el valor exacte", clau)),
                    () -> assertVinsIguals(cercat, botiga.cerca(plantillaNormalitzable), 
                                           String.format("cerca() no troba el primer vi quan es cerca per %s especificant el valor no normalitzat", clau)),
                    () -> assertVinsIguals(cercat, botiga.cerca(plantillaCase), 
                                           String.format("cerca() no troba el primer vi quan es cerca per %s especificant el valor canviat majúscules/minúscules", clau)),
                    () -> assertNull(botiga.cerca(plantillaInnexistent),
                                           "cerca() ha de retornar null quan no troba cap vi amb les característiques demanades")
                    );
        }
        @Test
        @DisplayName("test Botiga.cerca(Vi) troba vi per nom")
        public void trobaViPerNom() {
            Vi[] vins = vinsValids();
            Vi cercat = vins[4];
            Botiga botiga = botigaPlena(vins);
            String clau = "nom";
            String valorExacte = "Roura blanc";
            String valorANormalitzar = "    Roura    blanc   ";
            String valorCase = "ROURA BLANC";
            String valorInnexistent = "Capvicomaquest";
            Vi plantillaExacte = new Vi(null, valorExacte, -1, -1, null, null, null, null);
            Vi plantillaNormalitzable = new Vi(null, valorANormalitzar, -1, -1, null, null, null, null);
            Vi plantillaCase = new Vi(null, valorCase, -1, -1, null, null, null, null);
            Vi plantillaInnexistent = new Vi(valorInnexistent, null, -1, -1, null, null, null, null);
            assertAll(
                    () -> assertVinsIguals(cercat, botiga.cerca(plantillaExacte), 
                                           String.format("cerca() no troba el primer vi quan es cerca per %s especificant el valor exacte", clau)),
                    () -> assertVinsIguals(cercat, botiga.cerca(plantillaNormalitzable), 
                                           String.format("cerca() no troba el primer vi quan es cerca per %s especificant el valor no normalitzat", clau)),
                    () -> assertVinsIguals(cercat, botiga.cerca(plantillaCase), 
                                           String.format("cerca() no troba el primer vi quan es cerca per %s especificant el valor canviat majúscules/minúscules", clau)),
                    () -> assertNull(botiga.cerca(plantillaInnexistent),
                                           "cerca() ha de retornar null quan no troba cap vi amb les característiques demanades")
                    );
        }

        @Test
        @DisplayName("test Botiga.cerca(Vi) troba vi per lloc")
        public void trobaViPerLloc() {
            Vi[] vins = vinsValids();
            Vi cercat = vins[7];
            Botiga botiga = botigaPlena(vins);
            String clau = "lloc";
            String valorExacte = "P02E02N55E";
            String valorANormalitzar = "    P02E02N55E   ";
            String valorCase = "P02E02N55E";
            String valorInnexistent = "Capvicomaquest";
            Vi plantillaExacte        = new Vi(null, null, -1, -1, valorExacte, null, null, null);
            Vi plantillaNormalitzable = new Vi(null, null, -1, -1, valorANormalitzar, null, null, null);
            Vi plantillaCase          = new Vi(null, null, -1, -1, valorCase, null, null, null);
            Vi plantillaInnexistent = new Vi(valorInnexistent, null, -1, -1, null, null, null, null);
            assertAll(
                    () -> assertVinsIguals(cercat, botiga.cerca(plantillaExacte), 
                                           String.format("cerca() no troba el primer vi quan es cerca per %s especificant el valor exacte", clau)),
                    () -> assertVinsIguals(cercat, botiga.cerca(plantillaNormalitzable), 
                                           String.format("cerca() no troba el primer vi quan es cerca per %s especificant el valor no normalitzat", clau)),
                    () -> assertVinsIguals(cercat, botiga.cerca(plantillaCase), 
                                           String.format("cerca() no troba el primer vi quan es cerca per %s especificant el valor canviat majúscules/minúscules", clau)),
                    () -> assertNull(botiga.cerca(plantillaInnexistent),
                                           "cerca() ha de retornar null quan no troba cap vi amb les característiques demanades")
                    );
        }

        @Test
        @DisplayName("test Botiga.cerca(Vi) troba vi per origen")
        public void trobaViPerOrigen() {
            Vi[] vins = vinsValids();
            Vi cercat = vins[2];
            Botiga botiga = botigaPlena(vins);
            String clau = "origen";
            String valorExacte = "Pla del Bages";
            String valorANormalitzar = "    Pla   del  Bages   ";
            String valorCase = "pLA DEL bAGES";
            String valorInnexistent = "Capvicomaquest";
            Vi plantillaExacte        = new Vi(null, null, -1, -1, null, valorExacte, null, null);
            Vi plantillaNormalitzable = new Vi(null, null, -1, -1, null, valorANormalitzar, null, null);
            Vi plantillaCase          = new Vi(null, null, -1, -1, null, valorCase, null, null);
            Vi plantillaInnexistent = new Vi(valorInnexistent, null, -1, -1, null, null, null, null);
            assertAll(
                    () -> assertVinsIguals(cercat, botiga.cerca(plantillaExacte), 
                                           String.format("cerca() no troba el primer vi quan es cerca per %s especificant el valor exacte", clau)),
                    () -> assertVinsIguals(cercat, botiga.cerca(plantillaNormalitzable), 
                                           String.format("cerca() no troba el primer vi quan es cerca per %s especificant el valor no normalitzat", clau)),
                    () -> assertVinsIguals(cercat, botiga.cerca(plantillaCase), 
                                           String.format("cerca() no troba el primer vi quan es cerca per %s especificant el valor canviat majúscules/minúscules", clau)),
                    () -> assertNull(botiga.cerca(plantillaInnexistent),
                                           "cerca() ha de retornar null quan no troba cap vi amb les característiques demanades")
                    );
        }

        @Test
        @DisplayName("test Botiga.cerca(Vi) troba vi per preu")
        public void trobaViPerPreu() {
            Vi[] vins = vinsValids();
            Vi cercat = vins[5];
            Botiga botiga = botigaPlena(vins);
            int valorExacte = 906;
            int valorSuperior = 910;
            int valorBaratissim = 1;
            Vi plantillaExacte     = new Vi(null, null, valorExacte, -1, null, null, null, null);
            Vi plantillaSuperior   = new Vi(null, null, valorSuperior, -1, null, null, null, null);
            Vi plantillaBaratissim = new Vi(null, null, valorBaratissim, -1, null, null, null, null);
            assertAll(
                    () -> assertVinsIguals(cercat, botiga.cerca(plantillaExacte), 
                                           "cerca() no troba el primer vi quan es cerca per preu màxim i s'especifica el valor exacte"),
                    () -> assertVinsIguals(cercat, botiga.cerca(plantillaSuperior), 
                                           "cerca() no troba el primer vi quan es cerca per preu màxim i s'especifica una mica per sobre"),
                    () -> assertNull(botiga.cerca(plantillaBaratissim),
                                           "cerca() ha de retornar null quan no troba cap vi amb el preu demanat")
                    );
        }

        @Test
        @DisplayName("test Botiga.cerca(Vi) troba vi per estoc")
        public void trobaViPerEstoc() {
            Vi[] vins = vinsValids();
            Vi cercat = vins[4];
            Botiga botiga = botigaPlena(vins);
            int valorExacte = 42;
            int valorInferior = 40;
            int valorExcessiu = 1000;
            Vi plantillaExacte   = new Vi(null, null, -1, valorExacte, null, null, null, null);
            Vi plantillaInferior = new Vi(null, null, -1, valorInferior, null, null, null, null);
            Vi plantillaExcessiu = new Vi(null, null, -1, valorExcessiu, null, null, null, null);
            assertAll(
                    () -> assertVinsIguals(cercat, botiga.cerca(plantillaExacte), 
                                           "cerca() no troba el primer vi quan es cerca per estoc mínim i s'especifica el valor exacte"),
                    () -> assertVinsIguals(cercat, botiga.cerca(plantillaInferior), 
                                           "cerca() no troba el primer vi quan es cerca per estoc mínim i s'especifica una mica per sota"),
                    () -> assertNull(botiga.cerca(plantillaExcessiu),
                                           "cerca() ha de retornar null quan no troba cap vi amb l'estoc el demanat")
                    );
        }


        @Test
        @DisplayName("test Botiga.cerca(Vi) troba vi per múltiples claus")
        public void trobaViPerMultiplesClaus() {
            Vi[] vins = vinsValids();
            Vi cercat = vins[3];
            Botiga botiga = botigaPlena(vins);
            Vi plantilla = new Vi(null, null, -1, 20, null, null, "negre", "2018");
            assertVinsIguals(cercat, botiga.cerca(plantilla), "cerca() no troba el vi quan es cerca per múltiples claus");
        }



        // elimina(String)
        @Test
        @DisplayName("test botiga no es pot eliminar un vi amb estoc")
        public void testNoEliminaAmbEstoc() {
            Vi[] vins = vinsValids();
            Vi escollit = vins[3];
            Botiga botiga = botigaPlena(vins);
            String ref = "SAOROSAT20180001";
            Vi trobat = botiga.elimina(ref);
            assertNull(trobat, "elimina() no ha d'eliminar vins amb estoc");
        }

        @Test
        @DisplayName("test botiga elimina vi conegut")
        public void testEliminaConegut() {
            Vi[] vins = vinsValids();
            Vi escollit = vins[3];
            escollit.setEstoc(0);
            Botiga botiga = botigaPlena(vins);
            String ref = "SAOROSAT20180001";
            Vi trobat = botiga.elimina(ref);
            Vi cercat = botiga.cerca(ref);
            assertAll(
                    () -> assertVinsIguals(escollit, trobat, "elimina() ha de retornar el vi eliminat"),
                    () -> assertNull(cercat, "elimina() ha d'eliminar el vi a partir de la referència")
                    );
        }
        @Test
        @DisplayName("test botiga elimina vi amb nom normalitzat")
        public void testEliminaNormalitzant() {
            Vi[] vins = vinsValids();
            Vi escollit = vins[5];
            escollit.setEstoc(0);
            Botiga botiga = botigaPlena(vins);
            String ref = "   ROURABLA20200201    ";
            Vi trobat = botiga.elimina(ref);
            Vi cercat = botiga.cerca(ref);
            assertAll(
                    () -> assertVinsIguals(escollit, trobat, "elimina() ha de retornar el vi eliminat"),
                    () -> assertNull(cercat, "elimina() ha d'eliminar el vi a partir de la referència no normalitzada")
                    );
        }
        @Test
        @DisplayName("test botiga elimina vi ignorant majúscules/minúscules")
        public void testEliminaCaseInsensitive() {
            Vi[] vins = vinsValids();
            Vi escollit = vins[0];
            escollit.setEstoc(0);
            Botiga botiga = botigaPlena(vins);
            String ref = "llumalba20200001";
            Vi trobat = botiga.elimina(ref);
            Vi cercat = botiga.cerca(ref);
            assertAll(
                    () -> assertVinsIguals(escollit, trobat, "elimina() ha de retornar el vi eliminat"),
                    () -> assertNull(cercat, "elimina() ha d'eliminar el vi a partir de la referència amb canvis majúscules/minúscules")
                    );
        }

        @Test
        @DisplayName("test botiga no elimina vi desconegut")
        public void testNoEliminaDesconegut() {
            Vi[] vins = vinsValids();
            Botiga botiga = botigaPlena(vins);
            String ref = "vi desconegut";
            Vi trobat = botiga.elimina(ref);
            assertNull(trobat, "elimina() ha de tornar null quan ha d'eliminar un vi que no hi és");
        }

        @Test
        @DisplayName("test botiga en eliminar segon vi queda espai per un de nou")
        public void testEliminaDeixaEspai() {
            Vi[] vins = vinsValids();
            Botiga botiga = new Botiga(vins.length);
            Arrays.stream(vins).forEach(botiga::afegeix);
            Vi escollit = vins[3];
            escollit.setEstoc(0);
            Vi nou = new Vi("novaref", "nou nom", 1, 2, "lloc", "origen", "blanc", "1000");
            String ref = "SAOROSAT20180001";
            Vi noAfegit = botiga.afegeix(nou);
            Vi eliminat = botiga.elimina(ref);
            Vi afegit = botiga.afegeix(nou);
            Vi trobat = botiga.cerca("novaref");
            assertAll(
                    () -> assertNull(noAfegit, "afegeix() no ha d'afegir quan botiga plena"),
                    () -> assertVinsIguals(escollit, eliminat, "elimina() ha de retornar el vi eliminat"),
                    () -> assertNotNull(afegit, "elimina() ha de deixar espai per poder afegir un nou vi"),
                    () -> assertVinsIguals(nou, trobat, "cerca(String) ha de trobar el vi un cop afegit")
                    );
        }

    }

    /* ************************************************************************* *
     *  Test de recorregut de Botiga                                             *
     * ************************************************************************* */
    @Nested
    @DisplayName("Tests recorregut de Botiga")
    class SobreBotigaRecorregut {

        // recorregut
        @Test
        @DisplayName("test botiga pot ser recorreguda quan és buida")
        public void recorregutBuit() {
            Botiga botiga = new Botiga();
            botiga.iniciaRecorregut();
            Vi primer = botiga.getSeguent();
            assertNull(primer, "una botiga buida ha de retornar null al primer vi del recorregut");
        }

        @Test
        @DisplayName("test botiga pot ser recorreguda quan hi ha elements")
        public void recorregutComplet() {
            Vi[] vins = vinsValids();
            Botiga botiga = botigaPlena(vins);

            botiga.iniciaRecorregut();
            Vi[] trobats = new Vi[vins.length];
            int i = 0;
            while (true) {
                assertTrue(i <= vins.length, "la botiga no ha de permetre recórrer més vins dels que té");
                Vi trobat = botiga.getSeguent();
                if (trobat == null) break;
                trobats[i] = trobat;
                i++;
            }
            final int quants = i;
            assertAll(
                    () -> assertEquals(vins.length, quants, "la botiga ha de permetre recórrer tots els vins afegits"),
                    () -> assertVinsIguals(vins[0], trobats[0], "la botiga ha de retornar primer el primer vi"),
                    () -> assertVinsIguals(vins[1], trobats[1], "la botiga ha de retornar el segon vi després del primer"),
                    () -> assertEquals(vins[vins.length-1], trobats[vins.length-1], "la botiga ha de retornar el darrer vi al final"),
                    () -> assertNull(botiga.getSeguent(), "un cop finalitzat el recorregut getSeguent() ha de retornar null")
                    );
        }

        @Test
        @DisplayName("test el recorregut de botiga salta espais eliminats")
        public void recorregutAmbViEliminat() {
            Vi[] vins = vinsValids();
            vins[0].setEstoc(0);
            vins[2].setEstoc(0);
            vins[4].setEstoc(0);
            vins[6].setEstoc(0);
            Botiga botiga = botigaPlena(vins);
            botiga.elimina(vins[0].getRef());
            botiga.elimina(vins[2].getRef());
            botiga.elimina(vins[4].getRef());
            botiga.elimina(vins[6].getRef());
            botiga.iniciaRecorregut();
            Vi[] trobats = new Vi[vins.length];
            int i = 0;
            while (true) {
                assertTrue(i <= vins.length, "la botiga no ha de permetre recórrer més vins dels que té");
                Vi trobat = botiga.getSeguent();
                if (trobat == null) break;
                trobats[i] = trobat;
                i++;
            }
            final int quants = i;
            assertAll(
                    () -> assertEquals(4, quants, "la botiga no ha de retornar vins eliminats"),
                    () -> assertVinsIguals(vins[1], trobats[0], "la botiga ha de retornar primer el primer vi no eliminat"),
                    () -> assertVinsIguals(vins[3], trobats[1], "la botiga ha de saltar-se els vins eliminats"),
                    () -> assertVinsIguals(vins[5], trobats[2], "la botiga ha de saltar-se els vins eliminats"),
                    () -> assertEquals(vins[7], trobats[3], "la botiga ha de retornar al final el darrer vi no eliminat")
                    );
        }
    }

    /* ************************************************************************* *
     *  Utilitats                                                                *
     * ************************************************************************* */
    private static void assertVinsIguals(Vi un, Vi dos, String msg) {
        assertAll(
            () -> assertEquals(un.getRef(), dos.getRef(), msg + ". Revisa atribut ref"),
            () -> assertEquals(un.getNom(), dos.getNom(), msg + ". Revisa atribut nom"),
            () -> assertEquals(un.getPreu(), dos.getPreu(), msg + ". Revisa atribut preu"),
            () -> assertEquals(un.getEstoc(), dos.getEstoc(), msg + ". Revisa atribut estoc"),
            () -> assertEquals(un.getLloc(), dos.getLloc(), msg + ". Revisa atribut lloc"),
            () -> assertEquals(un.getOrigen(), dos.getOrigen(), msg + ". Revisa atribut origen"),
            () -> assertEquals(un.getTipus(), dos.getTipus(), msg + ". Revisa atribut tipus"),
            () -> assertEquals(un.getCollita(), dos.getCollita(), msg + ". Revisa atribut collita")
        );
    }

    private static Vi unViValid() {
        return new Vi("ROURABLA20200232", "Roura blanc", 1012, 42, "P21E45N55E", "Alella", "blanc", "2020");
    }

    private static Vi[] vinsValids() {
        return new Vi[] {
            new Vi("LLUMALBA20200001", "Llum d'Alba Blanc", 1750, 12, "P02E03N55D", "Priorat"          , "blanc", "2020"),
            new Vi("CARPATHI20180021", "Carpathia Negre"  , 3450,  6, "P23E01N43D", "Montsant"         , "negre", "2018"),
            new Vi("MATISNEG20190011", "Matís Negre"      , 1325, 12, "P20E01N12E", "Pla del Bages"    , "negre", "2019"),
            new Vi("SAOROSAT20180001", "Saó Rosat"        , 1040, 24, "P02E45N55D", "Costers del Segre", "negre", "2018"),
            new Vi("ROURABLA20200232", "Roura blanc"      , 1012, 42, "P21E45N55E", "Alella"           , "blanc", "2020"),
            new Vi("ROURABLA20200201", "Roura blanc"      ,  906,  3, "P21E45N55E", "Alella"           , "blanc", "2018"),
            new Vi("CERCIUMX20170002", "Cercium"          ,  565, 30, "P23E01N55D", "Empordà"          , "negre", "2017"),
            new Vi("MASIASER20200001", "Masia Serra"      , 1350, 12, "P02E02N55E", "Empordà"          , "negre", "2020")
        };
    }

    private static Botiga botigaPlena(Vi[] vins) {
        Botiga botiga = new Botiga();
        Arrays.stream(vins).forEach(botiga::afegeix);
        return botiga;
    }

}