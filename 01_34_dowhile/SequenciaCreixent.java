public class SequenciaCreixent {
    public static void main (String[] args) {
        
        int numero = 0;
        int longitud = 0;
        int numeroAnterior;
        
        do  {
            numeroAnterior = numero;
            System.out.println("Introdueix un valor");
            numero = Integer.parseInt(Entrada.readLine());
            longitud = longitud + 1;
        } while (numero > numeroAnterior);
        
        System.out.println("Longitud de la següència creixent: " + longitud);
    }
}           
