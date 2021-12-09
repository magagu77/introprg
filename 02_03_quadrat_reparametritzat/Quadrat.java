/*Farem ara una nova versió del quadrat parametritzat. En aquesta ocasió, 
  el programa serà capaç de mostrar un caràcter diferent de X a partir del
  que main() rebi a args[1]

  Per simplificar, pressuposarem també que sempre ens donen aquest segon 
  argument i que ens quedarem amb el primer caràcter.
*/
public class Quadrat {
  public static void main(String[] args) {
 		int numero = Integer.parseInt(args[0]);
    char caracter = args[1].charAt(0);
    dibuixaQuadrat(numero, caracter);
 	}

 	public static void dibuixaQuadrat(int numero, char caracter) {
 		 for(int l = 1; l <= numero; l++) {
 				dibuixaLinia(l);
 				System.out.println();
 			}
    }
  public static void dibuixaLinia(int numero) {
    for (int c = 1; c <= numero; c++) {
      System.out.print(c);
      } 

    }
}
