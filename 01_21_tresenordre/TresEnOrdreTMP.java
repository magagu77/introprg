//Ejercicio hecho permutando variables

public class TresEnOrdreTMP {
    public static void main (String[] args) {
      
      System.out.println("Primer?");
      int primer = Integer.parseInt(Entrada.readLine());
      System.out.println("Segon?");
      int segon = Integer.parseInt(Entrada.readLine());
      System.out.println("Tercer?");
      int tercer = Integer.parseInt(Entrada.readLine());
      int tmp;
      
      if (segon <= primer) {
          tmp = primer;
          primer = segon;
          segon = tmp;
      }
      if (tercer <= segon) {
          tmp = tercer;
          tercer = segon;
          segon = tmp;
      }
      if (segon <= primer) {
          tmp = primer;
          primer = segon;
          segon = tmp;
      }
      System.out.println( primer + ", " + segon + " i " + tercer);
    }
}
