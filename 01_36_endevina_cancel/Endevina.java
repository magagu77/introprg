//Endevina pero aquesta versió es tancaa amb intro

public class Endevina {
    public static void main(String[] args) {
          
        int nombrePredefinit = 42;
        String numero = "0";
        int nombre = Integer.parseInt(numero);
        
        while (numero.isEmpty() || !(nombre == nombrePredefinit)) {
            System.out.println("Nombre?");
            numero = Entrada.readLine();
            nombre = Integer.parseInt(numero);
            
                  if  (nombre <= 0 || nombre > 100)  {
                    System.out.println("Fora de rang");
                } else if (nombre > nombrePredefinit ) {
                      System.out.println("Massa gran");
                } else if (nombre < nombrePredefinit) {
                      System.out.println("Massa petit") ;  
                } 
        }  
        if (nombre == nombrePredefinit) {
            System.out.println("Encertat!");
        } else if (numero.isEmpty()) {
              System.out.println("Cancel·lat!");
        }
    }
} 
