//Prgrama del loro repite palabras

public class Lloro {
    public static void main(String[] args) {
    
        System.out.println("El lloro espera paraula:");
        String paraula = Entrada.readLine();
        
        if (!(paraula.isEmpty() || paraula.isBlank())) { 
            System.out.println("El lloro repeteix: " + paraula);        
        }
        
        while (!(paraula.isEmpty() || paraula.isBlank())) {
          System.out.println("El lloro espera paraula:"); 
          paraula = Entrada.readLine();
          
          if (!(paraula.isEmpty() || paraula.isBlank())) {
              System.out.println("El lloro repeteix: " + paraula);       
          }
        }
        System.out.println("Adéu");
    }  
}        
