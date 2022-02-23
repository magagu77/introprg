/* Programa del ascensor con entrada de valores con args[] */
public class Ascensor {
    public static int pis = 0;
    // AScensor puja un pis
    public static void pujaPis(Ascensor ascensor) {
        ascensor.pis++;
    }
    public static void main(String[] args) {
        Ascensor ascensor = new Ascensor();
        if(!UtilString.esEnter(args[0])) {
            System.out.println("Cap ascensor");
            return;
        }
        int numero = Integer.parseInt(args[0]);
        for (int i=0;i<numero;i++) {
            System.out.printf("Ascensor %s al pis %s\n",i,ascensor.pis);
            pujaPis(ascensor);

        }
    }
}