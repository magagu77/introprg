// Programa que usa un ascensor y muestra El piso en el que está, y el movimiento que hace 
public class UsaAscensor {
    public static void main(String[] args) {
        Ascensor ascensor = new Ascensor();
        System.out.printf("Pis inicial: %s%n",ascensor.pis);
        System.out.printf("Moviment inicial: %s%n",ascensor.moviment);
        ascensor.moviment = "pujant";
        System.out.printf("Moviment final: %s%n",ascensor.moviment);
    } 
}