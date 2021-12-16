/*  Desenvolupa una nova versió de l’exercici que filtrava les lletres d’un text.

	La nova versió tindrà la mateixa sortida que l’original però el codi del 
	programa serà més modular. En concret, el mòdul main() se n’encarregarà 
	d’obtenir les dades d’entrada, i cridarà un nou mòdul que serà qui realitzi 
	realment la feina.
*/
public class NomesLletres {
	public static void main(String[] args) {
		System.out.println("Text?");
		String text = Entrada.readLine();
		filtraLletres(text);
	}
	public static void filtraLletres(String text) {
		for (int i = 0; i < text.length(); i++) {
			if (Character.isLetter(text.charAt(i)) && i < text.length() - 2) {
				System.out.print(text.charAt(i) + ", ");
			} else if (Character.isLetter(text.charAt(i)) && i < text.length()) {
				System.out.print(text.charAt(i));
			}
		}
		System.out.println();
	}
}