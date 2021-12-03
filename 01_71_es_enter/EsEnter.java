/*Desenvolupa un programa que vagi demanant texts fins rebre la cadena buida.
  Per cada text no buit que rebi, indicarà si correspon o no a un nombre enter 
  escrit amb dígits.
*/
public class EsEnter {
	public static void main(String[] args) {

		System.out.println("Introdueix texts (enter sol per finalitzar)");
		String text = "a";
		while (!text.isEmpty()) {
			text = Entrada.readLine();
			if (text.isEmpty()) {
				System.out.println("Adéu");
				return;
			}
			for (int i = 0; i <= text.length(); i++) {
				if (Character.isDigit(text.charAt(i))) {
					System.out.println("És enter");
					break;
				}
			}
		}
	}
}
