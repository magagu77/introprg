//Endevina pero aquesta versió es tancaa amb intro

public class Endevina {
    public static void main(String[] args) {
        
        int nombre = 0;
        int nombrePredefinit = 42;
        String numero = "a";
        
        while (numero.isEmpty() || !(nombre == nombrePredefinit)) {
            System.out.println("Introdueix un nombre entre el 1 i el 100");
            numero = Entrada.readLine();
            if (!(numero.isEmpty())) {
                nombre = Integer.parseInt(numero);
            }
            
                if  (nombre <= 0) {
                    System.out.println("Com a mínim 1");
                    
                } else if (nombre > 100) {
                      System.out.println("Com a màxim 100");
                      
                } else if (nombre < nombrePredefinit ) {
                      System.out.println("Massa gran");
                } else if (nombre > nombrePredefinit) {
                      System.out.println("Massa petit") ;  
                } 
        }  
        if (nombre == nombrePredefinit) {
            System.out.println("Has encertat!");
        } else if (numero.isEmpty()) {
              System.out.println("Cancel·lat!");
        }
    }
} 
