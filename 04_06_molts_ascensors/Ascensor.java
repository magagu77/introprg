/* Programa del ascensor con entrada de valores con args[] */
public class Ascensor {
    public int pis;
    // AScensor puja un pis
    public static Ascensor[] creaAscensors(int quants) {
        Ascensor[] ascensors = new Ascensor[quants];
        for (int i=0;i<quants;i++) {
            ascensors[i] = new Ascensor();
            ascensors[i].pis = i;
            System.out.println(ascensors[i].pis);
        }       
        return ascensors;
    }
    public static void main(String[] args) {
        if(!UtilString.esEnter(args[0]) || args[0].equals("0")) {
            System.out.println("Cap ascensor");
            return;
        }
        int quants = Integer.parseInt(args[0]);
        Ascensor[] ascensors = creaAscensors(quants);
        for (int i = 0; i < ascensors.length; i++) {
            System.out.printf("Ascensor %s al pis %s%n", i, ascensors[i].pis);
        }
    }
}