public class Ascensor {
  public static void main (String[] args) {
      
      System.out.println("pis?");
      String pis = Entrada.readLine();
      System.out.println("botó?");
      String boto = Entrada.readLine();
      int planta = 0;
      int resultat = 0;
      
      if (pis.equals("planta baixa")) { 
          planta = 0;
      } else if(pis.equals("primer pis")) { 
          planta = 1;
      } else if(pis.equals("segon pis")) { 
          planta = 2;    
      }
          else {
            planta = 50;
      }
      
      if (boto.equals("pujar un")) {
          resultat = planta + 1;
      } else if (boto.equals("pujar dos")) {
          resultat = planta + 2;
      } else if (boto.equals("baixar dos")) {
          resultat = planta - 2;
      } else if (boto.equals("baixar un")) {
          resultat = planta - 1;
          
      }   else {
            resultat = 50;
      }
      
      if (resultat == 0) {
          System.out.println("planta baixa");
      } else if (resultat == 1) {
          System.out.println("primer pis");
      } else if (resultat == 2) {
          System.out.println("segon pis");
      }   else  {
            System.out.println("error");
      }
    }
}
