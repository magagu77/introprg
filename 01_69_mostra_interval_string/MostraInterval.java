/* Desenvolupa un programa que demani un text i dos valors enters, i que mostri
   tots els caràcters que hi ha entre el primer i el segon, en l’ordre marcat
   per l’entrada.
   En cas que els valors enters quedin fora de les posicions del text introduït,
   el programa mostrarà tot el que es pugui sense queixar-se.
*/

public class MostraInterval {
	public static void main(String[] args) {
		System.out.println("text?");
		String text = Entrada.readLine();
		System.out.println("inici?");
		int inici = Integer.parseInt(Entrada.readLine());
		System.out.println("final?");
		int end = Integer. parseInt(Entrada.readLine());
		if (inici < 0) {
			inici = 0;
		}
		if (end < 0) {
			end = 0;
		}	
		if (inici > text.length() - 1) {
			inici = text.length() - 1;
		}	
		if (end > text.length() - 1) {
			end = text.length() - 1;
		}
		if (inici < end) {
			for (int i = inici; i <= end; i++) {
				if (i >= text.length()) {
					break;
				} else {
					System.out.println(text.charAt(i));
				}
			}
		}
		if (end < inici) {
			for (int i = inici; i >= end; i--) {
				if (i <= -1) {
					break;
				} else {
					System.out.println(text.charAt(i));
				}
			}
		}
	}	
}
   