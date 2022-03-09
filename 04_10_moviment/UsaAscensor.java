// Programa que usa un ascensor
public class UsaAscensor {
    public static void main(String[] args) {
        Ascensor ascensor = new Ascensor();
        ascensor.pis = -1;
        ascensor.moviment = "aturat";
        System.out.printf("Pis inicial: %s%n",ascensor.pis);
        System.out.printf("Moviment inicial: %s%n",ascensor.moviment);
        ascensor.moviment = "pujant";
        System.out.printf("Moviment final: %s%n",ascensor.moviment);
    } 
}