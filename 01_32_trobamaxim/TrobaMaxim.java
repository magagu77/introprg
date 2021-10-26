//Troba maxim entre numeros positius
public class TrobaMaxim {
    public static void main(String[] args) {
    
        int maxim = 0;
        int numero = 0;
        
        while (numero >= 0) {
            System.out.println("Introdueix un valor");
            numero = Integer.parseInt(Entrada.readLine());
            if (numero > maxim) {
               maxim = numero;
            }
        }
        System.out.println("El màxim és " + maxim);
    }
}    
      
