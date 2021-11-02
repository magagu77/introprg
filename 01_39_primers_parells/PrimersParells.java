//Aparecen los primeros numeros pares en pantalla

public class PrimersParells {
    public static void main(String[] args) {
    
        int numero = 0;
        int par = 0;
        while (numero <= 10) {
                numero = numero + 1;
                par = numero % 2;
                if (par == 0) {
                    System.out.println(numero);
               }
        }
    }
}           
