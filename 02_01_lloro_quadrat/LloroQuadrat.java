/* Desenvolupa una versió del lloro que, com és tradicional, vagi demanant 
   texts i els repeteixi fins que arribi un text en blanc o buit.

   En aquesta versió, no obstant, si el text que rep és "dibuixa quadrat" o 
   "dibuixa rectangle", en comptes de repetir el text, dibuixarà el quadrat
   o el rectangle corresponent.
*/

 public class LloroQuadrat {
 	public static void main(String[] args) {
 		lloro();
 	}
 		public static void lloro() {
 			String text = "A";
 			while(!text.isBlank()) {
 				System.out.println("El lloro espera paraula:");
 				text = Entrada.readLine();
 				if (text.equals("dibuixa quadrat")) {
 					dibuixaQuadrat();
 				} else if (!text.isBlank()) {
 					System.out.println("El lloro repeteix: " + text);
 				} else if (text.equals("dibuixa rectangle")) {
 					dibuixaRectangle();
 				}
 			}
 		}
 		public static void dibuixaQuadrat() {
 			for(int l = 1; l <= 5; l++) {
 				for(int c = 1; c <= 5; c++) {
 					System.out.print(" X");
 				}
 				System.out.println();
 			}
 		}
 		public static void dibuixaRectangle() {
 			for (int l = 1; l <= 5; l++) {
 				for (int c = 1; c <= 10; c++) {
 					System.out.println(" X");
 				}
 				System.out.println();
 			}
 		}

 	
 } 