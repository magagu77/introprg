/* Programa del ascensor con entrada de valores con args[] */
public class Ascensor {
    public int pis;
    // AScensor puja un pis
    public Ascensor(int pis){
        this.pis = pis; 
    }
    public static Ascensor[] creaAscensors(int quants) {
        Ascensor[] ascensors = new Ascensor[quants];
        for (int i=0;i<quants;i++) {
            ascensors[i] = new Ascensor(i);
        }
        return ascensors;
    }

    public static void main(String[] args){
        int quants = Integer.parseInt(args[0]); 
        Ascensor[] ascensors = creaAscensors(quants);
        for (int i = 0; i < ascensors.length; i++) {
            System.out.printf("Ascensor %d al pis %d%n", i, ascensors[i].pis);
        }
    }
}