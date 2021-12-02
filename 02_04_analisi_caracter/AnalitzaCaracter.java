/* Desenvolupa una nova versió de l’exercici que analitzava un caràcter.
   La nova versió tindrà el mateix comportament que l’original però el codi
   serà diferent:
   el mòdul main() se n’encarregarà d’obtenir les dades d’entrada, comprovarà
   que siguin vàlides, mostrarà els errors corresponents, i n’extraurà el caràcter
   que finalment s’haurà d’analitzar.
   Aquest caràcter a analitzar se li passarà al mòdul analitzaCaracter() que serà
   qui se n’encarregui de realitzar l’anàlisi.
*/
public class AnalitzaCaracter {
	public static void main(String[] args) {
	System.out.println("Text?");
	String text = Entrada.readLine();
	System.out.println("Posició?");
	int posicio = Integer.parseInt(Entrada.readLine());
	char caracter = 'q';
	if (posicio < 0 ) { 
		caracter = text.charAt(posicio + text.length());
	} else {
		caracter = text.charAt(posicio);
	}
	analitzaCaracter(caracter, posicio, text );	
	}
	public static void analitzaCaracter(char caracter, int posicio, String text){
		if (posicio < 0 ) { 
		caracter = text.charAt(posicio + text.length());
		}
		if (posicio >= 0 && posicio < text.length()) {
			if (Character.isLetter(caracter)) {
				System.out.println("'" + caracter + "' és una lletra");
			} else if (Character.isDigit(caracter)) {
				System.out.println("'" + caracter + "' és un nombre");
			} else {
				System.out.println("'" + text.charAt(posicio) + "' és una altra cosa");
			}	
		} else if (posicio < 0) {
			if (Character.isLetter(caracter)) {
				System.out.println("'" + caracter + "' és una lletra");
			} else if (Character.isDigit(caracter)) {
				System.out.println("'" + caracter + "' és un nombre");
			} else {
				System.out.println("'" + caracter + "' és una altra cosa");
			}
		} else {
			System.out.println("Fora de rang");
		}	
	}
}
