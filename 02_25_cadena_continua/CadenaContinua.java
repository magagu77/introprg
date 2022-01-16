/*  Programa que hace una secuencia de caracteres con el numero de caracteres 
	que le das
*/
public class CadenaContinua {
	public static void main(String[] args) {
		System.out.println("Text?");
		String entrada = Entrada.readLine();
		if (entrada.isEmpty()) {
			System.out.println("error");
			return;
		}
		System.out.println("Nombre?");
		String quants = Entrada.readLine();
		if (UtilString.esEnter(quants)) {
			System.out.println(cadenaContinua(quants, entrada));
		} else {
			System.out.println("error");
		}		
	}

	//Devuelve un String con la cadena de caracteres que ha  de printar
	public static String cadenaContinua(String quants, String text) {
		int numero = Integer.parseInt(quants);
		String salida = "";
		for (int i = 0; i < numero; i++) {
		 	if (numero <= text.length()) {
		 		salida = salida + text.charAt(i);
		 	} else if (numero > text.length()) {
		 		salida = salida + (text.charAt(i % text.length()));
		 	}
		}
		return salida; 
	}
}