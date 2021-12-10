/*  Desenvolupa una nova versió de l’exercici que mostrava la secció d’un String.
	La nova versió tindrà la mateixa sortida que l’original però el codi del 
	programa serà més modular. En concret, el mòdul main() se n’encarregarà 
	d’obtenir les dades d’entrada, i cridarà un nou mòdul anomenat mostraInterval()
	que serà qui realitzi realment la feina de mostrar l’interval.
*/
public class MostraInterval {
	public static void main(String[] args) {
		System.out.println("text?");
		String text = Entrada.readLine();
		System.out.println("inici?");
		int inici = Integer.parseInt(Entrada.readLine());
		System.out.println("final?");
		int end = Integer. parseInt(Entrada.readLine());
		mostraInterval(text, inici, end);
	} 
	public static void mostraInterval(String text, int inici, int end) {
		if (inici >= 0 || end >= 0) {
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
			if (inici <= end) {
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
						System.out.println(text.charAt(i));
					
				}
			}
		}	
	}
}