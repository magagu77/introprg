/*Desenvolupa un programa que esperi un valor enter positiu en args[0] 
  i mostri un quadrat de " X" amb el costat indicat pel valor rebut.
*/
public class Quadrat {
	public static void main(String[] args) {
		int numero = Integer.parseInt(args[0]);
		dibuixaQuadrat(numero);
	}
	public static void dibuixaQuadrat(int numero) {
		for(int l = 1; l <= numero; l++) {
 				for(int c = 1; c <= numero; c++) {
 					System.out.print(" X");
 				}
 				System.out.println();
 			}
	}
}