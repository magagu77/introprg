// Programa del ascensor pero puja un pis
public class Ascensor {
    public static int pis = -1;
    public static void pujaPis(Ascensor ascensor) {
        ascensor.pis++;
    }
    public static void main(String[] args){
        Ascensor ascensor;
        ascensor = new Ascensor();
        System.out.println("L'ascensor inicialment està a la planta " + ascensor.pis);
        pujaPis(ascensor);
        System.out.println("L'ascensor finalment està a la planta " + ascensor.pis);
    }
}