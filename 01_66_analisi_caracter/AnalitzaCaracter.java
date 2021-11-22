/*Desenvolupa un programa que demani un text i una posició.
  Si la posició no existeix dins del text, indicarà que està fora de rang.
  Si la posició és negativa, començarà a comptar a partir de l’últim caràcter 
  del text introduït. Per exemple, -1 correspondrà a l’últim caràcter.
*/
public class AnalitzaCaracter {
	public static void main(String[] args) {
		System.out.println("Text?");
		String text = Entrada.readLine();
		System.out.println("Posició?");
		int posicio = Integer.parseInt(Entrada.readLine());
		if (posicio <= 0 && posicio < text.length()) {
			if (Character.isLetter(text.charAt(posicio))) {
				System.out.println("'" + text.charAt(posicio) + "' és una lletra");
			} else if (Character.isDigit(text.charAt(posicio))) {
				System.out.println("'" + text.charAt(posicio) + "' és un nombre");
			} else {
				System.out.println("'" + text.charAt(posicio) + "' és una altra cosa");
			}	
		} else if (posicio < 0 || posicio + text.length() == 0) {
			if (Character.isLetter(text.charAt(posicio + text.length()))) {
				System.out.println("'" + text.charAt(posicio + text.length()) + "' és una lletra");
			} else if (Character.isDigit(text.charAt(posicio + text.length()))) {
				System.out.println("'" + text.charAt(posicio + text.length()) + "' és un nombre");
			} else {
				System.out.println("'" + text.charAt(posicio + text.length()) + "' és una altra cosa");
			}
		}	
	} 
}  