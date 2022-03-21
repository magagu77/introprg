/* Programa UsaGatRenat que crea un gato y pide los nuevos módulos
aixacat(), seu(), y estirat()*/
public class UsaGatRenat {
    public static void main(String[] args) {
        GatRenat renat = new GatRenat();
        System.out.println("El Renat diu: "+ renat.aixecat());
        System.out.println("El Renat diu: "+ renat.seu());
        System.out.println("El Renat diu: "+ renat.estirat());
        System.out.println("El Renat diu: "+ renat.estirat());        
    }
}