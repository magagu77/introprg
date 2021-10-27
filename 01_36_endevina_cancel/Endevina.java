//Endevina pero aquesta versió es tancaa amb intro

public class Endevina {
    public static void main(String[] args) {
        
        int nombre = 0;
        int nombrePredefinit = 42;
        
        while (nombre.isEmpty || !(nombre == numeroPredefinit)) {
            System.out.println("Introdueix un nombre entre el 1 i el 100");
            nombre = Integer.parseInt(Entrada.readLine());
            
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
        if (nombre = nombre predefinit) {
            System.out.println("Has encertat!");
        } else if (nombre.isEmpty()) {
              System.out.println("Cancel·lat!")
    }
} 
