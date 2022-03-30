/*  Programa que usa GatRenat en la version del ejercicio 04_28,
    que limita a 1 las instancias que se pueden crear, siquiendo 
    el patrón de singleton
*/
public class UsaGatRenat {
    public static void main(String[] args) {
        GatRenat renat = GatRenat.getInstancia();
        System.out.println("Inicialment Renat està " + renat.getPosicio());
        for (String posicio: args) {
            canviaPosicio(posicio);
            System.out.println("Ara està " + renat.getPosicio());
        }
    }
    private static void canviaPosicio(String novaPosicio) {
        // Tradueix novaPosicio: 1 -> estirat, 2 -> assegut, 3 -> dret,
        // altrament es queda com estava
        if (Integer.parseInt(novaPosicio) == 1) {
            novaPosicio = "estirat";
        } else if (Integer.parseInt(novaPosicio) == 2) {
            novaPosicio = "assegut";
        } else if (Integer.parseInt(novaPosicio) == 3) {
            novaPosicio = "dret";
        } else {
            return;
        }
        GatRenat.getInstancia(novaPosicio);
    }
}
