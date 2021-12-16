/* Desenvolupa un programa que demani un text i mostri el mateix text amb 
   les inicials de cada paraula en majúscules i la resta en minúscules.

   Una paraula és cada segment del text que només conté lletres que davant 
   i darrere tenen quelcom que no sigui una lletra, o bé són la primera o darrera 
   paraula del text.
*/
 public class MajusculitzaInicials {
 	public static void main(String[] args) {
 		System.out.println("Text?");
 		String text = Entrada.readLine();
 		majusculitzaInicials(text);
 	}
 	public static void majusculitzaInicials(String text) {
 		for (int c = 0; c < text.length(); c++) {
 			if (c == 0) {
 				System.out.print(Character.toUpperCase(text.charAt(c)));
 			}
 			else if (Character.isLetter(text.charAt(c)) && 
 				text.charAt(c - 1) == ' ') {
 				System.out.print(Character.toUpperCase(text.charAt(c)));
 			} else {
 				System.out.print(text.charAt(c));
 			}
 		}
 		System.out.println();
 	}
 }