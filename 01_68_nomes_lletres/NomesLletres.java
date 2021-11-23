/*Desenvolupa un programa que demani un text i mostri només les lletres que conté.
  Cada lletra apareixerà separada per una coma en l’ordre en que apareixia 
  al text original. La resta de caràcters no es mostraran.
*/

public class NomesLletres {
	public static void main(String[] args) {
		System.out.println("Text?");
		String text = Entrada.readLine();
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