/* Programa del ascensor con entrada de valores con args[] */
public class Ascensor {
    public static int pis = 0;
    // AScensor puja un pis
    public static void pujaPis(Ascensor ascensor, int numero) {
        ascensor.pis = numero;
    }
    public static Ascensor[] creaAscensors(int quants) {
        Ascensor[] ascensors = new Ascensor[quants];
        return ascensors;
    }
    public static void main(String[] args) {
        Ascensor ascensor = new Ascensor();
        if(!UtilString.esEnter(args[0]) || args[0].equals("0")) {
            System.out.println("Cap ascensor");
            return;
        }
        int quants = Integer.parseInt(args[0]);
        Ascensor[] ascensors = creaAscensors(quants);
        for (int i = 0; i < ascensors.length; i++) {
            System.out.printf("Ascensor %d al pis %d%n", i, ascensors[i].pis);
            pujaPis(ascensors[i], i);
        }
    }
}