/* Desenvolupa una nova versió del programa del Lloro que, en aquest cas només
   repeteixi les paraules que comencen per una vocal en minúscules i s’aturi 
   quan rebi una cadena en blanc.
*/

 public class LloroVocalMinuscules {
 	public static void main(String[] args) {
 		
 		String paraula = "a";
 		while (!(paraula.isBlank())) {
 			System.out.println("El lloro pregunta paraula que comenci amb vocal en minúscules");
 			paraula  = Entrada.readLine();
         char inici = paraula.charAt(0);
 			if (Character.isLowerCase(paraula.charAt(0)) && inici == 'a' || inici == 'e' || 
             inici == 'i' || inici == 'o' || inici == 'u') {
 				System.out.println("El lloro diu: " + paraula);
 			} else {
 				System.out.println();
 			}
 		System.out.println("Adéu");
 	   }
 	}
}  