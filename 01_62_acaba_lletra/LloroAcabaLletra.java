/*Desenvolupa una nova versió del programa del Lloro que, en aquest cas 
  només repeteixi les paraules que finalitzin amb una lletra no vocal. 
  Com la versió original, s’aturarà quan rebi una cadena en blanc o buida.
*/

public class LloroAcabaLletra {
	public static void main(String[] args) {
		
		System.out.println("El lloro pregunta paraula que finalitzi per lletra no vocal");
		String paraula = Entrada.readLine();
		while (!paraula.isBlank()) {
			char end = paraula.charAt(paraula.length() - 1);
			if (!(end == 'a' && end == 'e' && end == 'i' && end == 'o' && end == 'u')) {
				System.out.println("El lloro diu : " + paraula);
			} else {
				System.out.println();
			}
			System.out.println("El lloro pregunta paraula que finalitzi per lletra no vocal");
			paraula = Entrada.readLine();	
		}
	}
}