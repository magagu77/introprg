//El programa demanarà el preu en € i la quantitat de € pagada. A continuació compararà les dues quantitats i escriurà els € que falten per pagar o bé els que se han de tornar.

public class Pagament {
    public static void main(String[] args) {
        System.out.println("Preu?");
        int preu = Integer.parseInt(Entrada.readLine());
        System.out.println("Paga?");
        int paga = Integer.parseInt(Entrada.readLine());
        if (preu < paga) {
            int resultat = paga - preu;
            System.out.println("Sobren " + resultat + "€");
        } else if (paga < preu) {
              int resultat = preu - paga;
              System.out.println("Falten " + resultat + "€");
        } else if (preu == paga) {
              System.out.println("No sobra ni falta res");
        }
    }
}
             
        
