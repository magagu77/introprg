// char a = 97 //char z = 122 
/* Ejercicio del juego del ahorcado */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Penjat {
	public static void main(String[] args) throws IOException{
		// Seleccionar fichero del que leer las palabras
		String fitxerParaula = "paraules.txt";
		BufferedReader input = new BufferedReader(new FileReader(fitxerParaula));
		System.out.println("Comencem a jugar");
		
		// Bucle del juego
		while(true) {
			// Coge una palabra del fichero
			String paraula = input.readLine();
			char[] adivina = new char[paraula.length()];
			for (int i=0;i<adivina.length;i++) {
				adivina[i] = '*';
			}
			System.out.println(adivina);
			String lletres = "cap";
			System.out.println("Paraula: " + adivina);
			char lletra ='1';
			int intents = 10;
			while(true) {
				System.out.printf("Utilitzades: %s",lletres);
				System.out.printf("Intents disponibles: %i", intents);
				System.out.println("Introdueix una lletra");
				lletra = (Entrada.readLine()).charAt(0);
				//Comprueba si la letra esta en la palabra y la sustituye
				if (comprobaLletra(paraula,lletra)) {
					adivina = sustitueixLletra(paraula,lletra,adivina);
				}
				if(comprobaVictoria(adivina)) {
					System.out.println("Funciona");
					return;
				}
			}	
		}
	}
	public static char[] asteriscos(char[] asteriscos) {
		for(int i=0;i<asteriscos.length;i++) {
			asteriscos[i] = '*';
		}
		return asteriscos;
	}
	public static boolean comprobaLletra (String paraula, char lletra) {
		for (int i=0;i<paraula.length();i++) {
			if(paraula.charAt(i)==lletra) return true;
		}
		return false;
	}
	public static char[] sustitueixLletra(String paraula, char lletra, char[] adivina) {
		for (int i=0;i<paraula.length();i++) {
			if (paraula.charAt(i)==lletra) {
				adivina[i] = lletra;
			}
		}
		return adivina;
	}
	public static boolean comprobaVictoria (char[] asteriscos){
		for(int i=0;i<asteriscos.length;i++) {
			if (asteriscos[i]=='*') {
				return false;
			}
		}
		return true;
	}
}