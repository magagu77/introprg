/*
	Desenvolupa una nova versió de l’exercici que codificava un text.

	La nova versió inclou una modificació respecte la versió inicial. En aquesta 
	ocasió permetrà decidir quants caràcters s’incrementen

	Considera les següents simulacions:
*/
public class CodificaBasic {
	public static void main(String[] args) {
		System.out.println("Text?");
		String text = /*Entrada.readLine();*/ "abcd";
		System.out.println("Quants?");
		int numero = /*Integer.parseInt(Entrada.readline());*/ 1;
		codifica(text , numero);
	}
	public static void codifica(String text, int quants) {
		char codifica;
		for (int i = 0; i < text.length(); i++ ) {
			if (quants < 0) {
				System.out.println("No s'accepten números negatius");
				return;
			} else if (text.charAt(i) == 'z') {
				System.out.print('a');
			} else {
			codifica = (char)(text.charAt(i) + quants);
			System.out.print(codifica);
			}
		}
		System.out.println();
	}
}