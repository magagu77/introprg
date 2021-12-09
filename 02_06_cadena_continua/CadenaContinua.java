/* Desenvolupa una nova versió de l’exercici que mostrava una cadena contínua.
   La nova versió tindrà la mateixa sortida que l’original però el codi del
   programa serà més modular. En concret, el mòdul main() se n’encarregarà 
   d’obtenir les dades d’entrada, i cridarà un nou mòdul anomenat mostra
   CadenaContinua() que serà qui realitzi realment la feina de mostrar el resultat.
*/
public class CadenaContinua {
	public static void main(String[] args) {
		System.out.println("Text?");
		String text = Entrada.readLine();
		if (text.isBlank()) {
			System.out.println("error");
			return;
		}
		System.out.println("Nombre?");
		String nombre = Entrada.readLine();
		if (nombre.isBlank()) {
			return;
		}
		int longitud = Integer.parseInt(nombre);
		cadenaContinua(text, longitud);
	}
	public static void cadenaContinua(String text, int longitud) {
		for (int i = 0; i < longitud; i++) {
		 	if (longitud <= text.length()) {
		 		System.out.print(text.charAt(i));
		 	} else if (longitud > text.length()) {
		 		System.out.print(text.charAt(i % text.length()));
		 	}
		}
		System.out.println();
	}
}   