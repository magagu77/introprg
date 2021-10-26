//Programa de sumar notas dentro de los valores correctos


public class SumaNotes {
    public static void main (String[] args) {
            
        int suma = 0;
        System.out.println("Introdueix una nota");
        int valor = Integer.parseInt(Entrada.readLine());
            
        while (valor >= 0 && valor <= 100) {
            suma = suma + valor;
            System.out.println("Introdueix una nota");
            valor = Integer.parseInt(Entrada.readLine());
        }
            
        System.out.println("La suma de les notes vàlides és " + suma);
    }
}
