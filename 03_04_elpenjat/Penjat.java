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
		String entrada ="a";
		boolean continua = true;
		int paraulesJugades = 0;
		int paraulesEncertades = 0;
		int paraulesFallades = 0;
		int paraulesCancelades = 0;
		
		// Bucle del juego
		while(continua) {
			// Coge una palabra del fichero
			String paraula = input.readLine();
			if (null == paraula) break;
			char[] adivina = new char[paraula.length()];
			for (int i=0;i<adivina.length;i++) {
				adivina[i] = '*';
			}
			System.out.println();
			String lletres = "cap";
			System.out.println("Paraula: " + new String(adivina));
			char lletra ='a';
			int intents = 10;
			paraulesJugades++;
			// Bucle de juego por cada palabra
			while(true) {
				System.out.println("Utilitzades: " + lletres);
				System.out.println("Intents disponibles: " + intents);
				System.out.println("Introdueix una lletra");
				entrada = Entrada.readLine();
				if (comprovaSiPara(entrada)) {
					continua = false;
					paraulesCancelades++;
					break;	
				}
				lletra = entrada.charAt(0);
				if (lletres.equals("cap")) {
					lletres = Character.toString(Character.toUpperCase(lletra));
				} else {
					lletres = lletres + ", " + Character.toString(Character.toUpperCase(lletra));
				}
				//Comprueba si la letra esta en la palabra y la sustituye
				if (comprovaLletra(paraula,lletra)) {
					adivina = sustitueixLletra(paraula,lletra,adivina);
				} else {
					mostraFigura(intents);
					intents = intents - 1;
					if(comprovaDerrota(intents)) {
						paraulesFallades++;
						break;
					}
				}
				if(comprovaVictoria(adivina)) {
					paraulesEncertades++;
					break;
				}
				System.out.println(new String(adivina));
			}	
		}
	}
	// Pone asteriscos en el un array con la misma longitud que la palabra
	public static char[] asteriscos(char[] asteriscos) {
		for(int i=0;i<asteriscos.length;i++) {
			asteriscos[i] = '*';
		}
		return asteriscos;
	}
	// Comprueba si la letra está en la palabra
	public static boolean comprovaLletra (String paraula, char lletra) {
		paraula = paraula.toLowerCase();
		for (int i=0;i<paraula.length();i++) {
			if(paraula.charAt(i)==Character.toLowerCase(lletra)) return true;
		}
		return false;
	}
	// Sustituye los asteriscos del array por la letra en la misma posicion de la palbra, si hay mas de una las sustituye todas
	public static char[] sustitueixLletra(String paraula, char lletra, char[] adivina) {
		for (int i=0;i<paraula.length();i++) {
			if (paraula.charAt(i)==Character.toLowerCase(lletra)) {
				adivina[i] = Character.toLowerCase(lletra);
			}
		}
		return adivina;
	}
	// Comprueba si se han acertado todas las letras de la palabra
	public static boolean comprovaVictoria (char[] asteriscos){
		for(int i=0;i<asteriscos.length;i++) {
			if (asteriscos[i]=='*') {
				return false;
			}
		}
		return true;
	}
	// Muestra la figura que toque en cada momento
	public static void mostraFigura (int intents) throws IOException {
		String cami = "./recursos/figura" + (10 - intents) + ".txt";
		BufferedReader figura = new BufferedReader(new FileReader(cami));
		String fila = "a";
		while(true) {
			fila = figura.readLine();
			if(null == fila) break;
			System.out.println(fila);
		}
		figura.close();
	}
	// Comprueba si pierdes la partida
	public static boolean comprovaDerrota(int intents) {
		if (intents == 0) return true;
		else return false;
	}
	// Comprueba si tiene que parar la palabra y la partida
	public static boolean comprovaSiPara (String para) {
		if (para.equals("prou")) {
			System.out.println("Vols finalitzar?");
			if (UtilitatsConfirmacio.respostaABoolean(Entrada.readLine())) return true;
			else return false;
		} else return false;
	}
	// Hace un print de la "estadistica" de esta partida
	public static void estadistica(int paraulesJugades,	int paraulesEncertades, int paraulesFallades, int paraulesCancelades) {
		System.out.printf("Paraules jugades: %i\n",paraulesJugades);
		System.out.printf("Paraules encertades: %i\n",paraulesEncertades);
		System.out.printf("Paraules fallades: %i\n",paraulesFallades);
		System.out.printf("Paraules cancel·lades: %i\n",paraulesCancelades);
		System.out.println("Espero que t'hagis divertit");
	}
}