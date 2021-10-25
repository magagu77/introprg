//Programa que cuenta enteros pares hasta que introduces un numero negativo

public class ComptaParells {
    public static void main(String[] args) {
        
        System.out.println("Introdueix un valor");
        int valor = Integer.parseInt(Entrada.readLine());
        int numerosParells = 0;
        /*if (valor % 2 == 0) {
            numerosParells = numerosParells + 1;
        } */
        
        while (valor >= 0 ) {
            System.out.println("Introdueix un valor");
            valor = Integer.parseInt(Entrada.readLine());
            if (valor % 2 == 0) {
            numerosParells = numerosParells + 1;
            }
        }
        System.out.println("Valors parells introduïts: " + numerosParells);
    }
}
