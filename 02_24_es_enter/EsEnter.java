/*  Versión modular de EsEnter, que determina si un determinado valor de entrada
	 es entero o no
*/
public class EsEnter {
	public static void main(String[] args) {
		System.out.println("Introdueix texts (enter sol per finalitzar)");
		String text = Entrada.readLine();
		while(!text.isBlank()) {
			text = text.strip();
			if (UtilString.esEnter(text)) {
				System.out.println("És enter");
			} else {
				System.out.println("No és enter");
			}
			text = Entrada.readLine();
		}
		System.out.println("Adéu");
	}
}