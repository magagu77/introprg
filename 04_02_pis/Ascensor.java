/* Programa que muestra en que piso está un ascensor */
public class Ascensor {
    public int planta = -1;
    public static void main(String[] args) {
        Ascensor ascensor;
        ascensor = new Ascensor();
        System.out.println("L'ascensor està a la planta " + ascensor.planta);
    }  
}