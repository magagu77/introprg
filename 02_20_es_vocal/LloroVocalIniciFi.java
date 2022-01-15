/*  Programa del loro (otra vez, Moises pesado) con 2 modulos externos, 
	1 para determinar si la primra letra es vocal o no y otro para confirmar 
	si acaba el programa o no
*/
public class LloroVocalIniciFi {
	public static void main(String[] args) {
		boolean continuar = false;
		while(!continuar){
			System.out.println("El lloro demana paraula amb vocal a l'inici o/i final");
			String text = Entrada.readLine();
			if (text.isBlank()) {
				System.out.println("El lloro demana confirmació per finalitzar");
				continuar = UtilitatsConfirmacio.respostaABoolean(text);
			} else if (UtilString.esVocal(text.charAt(0)) || (text.length() > 1 && UtilString.esVocal(text.charAt(text.length() - 1)))) {
				System.out.println("El lloro diu: " + text);
			}
		}
		System.out.println("Adéu");
	}
	
}

