/*  Modulo externo a programas que contiene utilidades para strings o char que 
	no vienen por defecto en java
*/
public class UtilString {
	
	//Devuelve un valor boolean = true si una letra es vocal
	public static boolean esVocal(char lletra) {
		char[] vocals = {'a','à','e','è','é','i','í','ï','o','ó','ò','u','ú','ü'};
		for (int i=0;i<vocals.length; i++) {
			if(vocals[i] == Character.toLowerCase(lletra)) {
				return true;
			}
		}
		return false;
	}

	//Comprueba si un caracter en una posicion concreta de un texto es una letra
	public static String nomesLletres(String text) {
		String lletres="";
		for (int i=0;i<text.length();i++) {
			if (Character.isLetter(text.charAt(i))) {
				lletres = lletres + text.charAt(i);
			}
		}
		return lletres;
	}

	//Separa llos caracteres de un texto con un caracter (en este caso comas)
	public static String lletresSeparades(String text) {
		String lletres = "";
		for (int i=0;i<text.length(); i++) {
			if (i < text.length() - 1) {
				lletres = lletres + text.charAt(i) + ", ";
			} else if (i < text.length()) {
				lletres = lletres + text.charAt(i);
			}
		}
		return lletres;
	} 

	//Devuelve un String ordenado según un intérvalo (si no se entiende ver 02_23)
	public static String intervalString(String text, int inicio,int fin) {
		String interval = "";
		if (inicio < 0 && fin < 0) {
			return interval;
		}
		if (inicio < 0) {
			inicio = 0;
		} else if (inicio > text.length()) {
			inicio = text.length() - 1;
		}
		if (fin > text.length()) {
			fin = text.length() - 1;
		} else if (fin < 0) {
			fin = 0;
		}
		if (inicio < fin) {
			for (int i=(inicio); i<=(fin);i++) {
				interval = interval + text.charAt(i);
			}
		} else {
			for (int i=inicio;i>=fin;i--) {
				interval = interval + text.charAt(i);
			}
		}		
	return interval;
	}

	//Determina si un determinado String es un numero entero 
	public static boolean esEnter(String valor) {
		if (valor.isBlank()) {
			return false;
		}
		for (int i=0; i<valor.length();i++) {
			if (Character.isLetter(valor.charAt(i))) {
				return false;
			} else if (valor.charAt(i) == ',' || valor.charAt(i) == ' ') {
				return false;
			}
		}
		return true;
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