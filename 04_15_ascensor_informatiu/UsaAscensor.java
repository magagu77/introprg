// Clase que usa Ascensor tal com demana l'excercici 04_15
public class UsaAscensor {
    // XXX considera si et cal algun mòdul d'ajut
    public static void main(String[] args) {
        String nouPis;
        Ascensor ascensor = new Ascensor();
        System.out.println("Pis inicial: " + ascensor.getPis());
        System.out.println("Moviment inicial: " + ascensor.getMoviment());
        System.out.println("Introdueix nou pis:");
        nouPis = Entrada.readLine();
        if (UtilString.esEnter(nouPis)) {
            ascensor.setPis(Integer.parseInt(nouPis));
        }

        System.out.println("Introdueix nou moviment:");
        ascensor.setMoviment(Entrada.readLine());

        System.out.println("Pis final: " + ascensor.getPis());
        System.out.println("Moviment final: " + ascensor.getMoviment());
        System.out.println("Estat de l'ascensor: " + ascensor.comEsta());
    }
}