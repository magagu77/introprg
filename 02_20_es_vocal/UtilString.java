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
}