// Programa que usa ascensor en e ejercicio 04_13
public class UsaAscensor {
    public static void main(String[] args) {
        String moviments;
        String nouPis;
        // Dona informació del pis en el que está i le moviment
        Ascensor ascensor = new Ascensor();
        System.out.println("Pis inicial: " + ascensor.getPis());
        System.out.println("Moviment inicial: " + ascensor.getMoviment());
        System.out.println("Introdueix nou pis:");
        // Nou pis
        nouPis = Entrada.readLine();
        if (UtilString.esEnter(nouPis)) {
            ascensor.setPis(Integer.parseInt(nouPis));
        }
        System.out.println("Introdueix nou moviment:");
        // Nou moviment
        ascensor.setMoviment(Entrada.readLine());
        System.out.println("Pis final: " + ascensor.getPis());
        System.out.println("Moviment final: " + ascensor.getMoviment());
    }
}
