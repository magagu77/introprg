//Endevina pero aquesta versió es tanca amb intro

public class Endevina {
    public static void main(String[] args) {
          
        int numeroPredefinit = 42;
        int numero = 0;
        String nombre = "a";
        
        do  {
            System.out.println("Nombre?");
            nombre = Entrada.readLine();
            if (!(nombre.isEmpty())) { 
                numero = Integer.parseInt(nombre);
                if (numero <= 0 || numero > 100) {
                    System.out.println("Fora de rang");
                } else if (numero < numeroPredefinit) {
                      System.out.println("Massa petit");
                } else if (numero > numeroPredefinit) {
                      System.out.println("Massa gran");
                }
            }
        } while (!(nombre.isEmpty()) || !(numero == numeroPredefinit));
        if (nombre.isEmpty()) {
            System.out.println("Cancel·lat!");
        } else if (numero == numeroPredefinit) {
              System.out.println("Encertat!");
        }
    }
}            
