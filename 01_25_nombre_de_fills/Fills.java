//Numero de fills con un switch

public class Fills {
    public static void main(String[] args) {
    
      System.out.println("Quants fills tens?");
      int numeroFills = Integer.parseInt(Entrada.readLine());
      
      if (numeroFills < 0) {
          System.out.println("No pots tenir menys de 0 fills!");
      } else if (numeroFills == 0) {
            System.out.println("Tot el que t'has estalviat en bolquers!");
      } else if (numeroFills == 1) {
            System.out.println("Compte de no mimar-lo massa!");
      } else if (numeroFills > 1 && numeroFills < 5) {
            System.out.println("No t'avorreixes a casa, eh?");
      } else if (numeroFills > 4) {
            System.out.println("Tu sí que fas país!");
      }
    }
}
    
         
