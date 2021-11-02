//Programa que escribe los 10 primeos numeros naturales

public class DeuPrimers {
    public static void main(String[] args) {
    
        int numero = 1;
        boolean numeroDeu = false;
        
        while (numeroDeu == false) {
            System.out.println(numero);
            numero = numero + 1;       
        
            if (numero == 10) {
                numeroDeu = true;
            }
        }
    }
}
