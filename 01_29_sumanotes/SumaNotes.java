//Programa de sumar notas
public class SumaNotes {
    public static void main (String[] args) {
            
        int suma = 0;
        System.out.println("Introdueix valor");
        int valor = Integer.parseInt(Entrada.readLine());
            
        while (valor >= 0 && valor <= 100) {
            suma = suma + valor;
            System.out.println("Introdueix valor");
            valor = Integer.parseInt(Entrada.readLine());
        }
            
        System.out.println("La suma de les notes vàlides és " + suma);
    }
}
