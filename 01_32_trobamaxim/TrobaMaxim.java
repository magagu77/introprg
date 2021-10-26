//Troba maxim entre numeros positius
public class TrobaMaxim {
    public static void main(String[] args) {
    
        int tmp = 0;
        int numero = 0;
        
        while (numero >= 0) {
            System.out.println("Introdueix un valor");
            numero = Integer.parseInt(Entrada.readLine());
            if (numero > tmp) {
               tmp = numero;
            }
        }
        System.out.println("El màxim és " + tmp);
    }
}    
      
