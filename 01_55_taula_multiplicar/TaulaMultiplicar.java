/* Taula de multiplicar agafant els valors amb args[], els valors han de 
ser incrementals el 1 respecte al 0 y el 3 respecte al 2
*/

public class TaulaMultiplicar {
	public static void main(String[] args) {
			int valor1 = Integer.parseInt(args[0]);
			int valor2 = Integer.parseInt(args[1]);
			int valor3 = Integer.parseInt(args[2]);
			int valor4 = Integer.parseInt(args[3]);
			int resultat = 0;
			int tmp = 0;
			if (valor2 > valor1) {
				tmp = valor1;
				valor1 = valor2;
				valor2 = tmp; 
			}
			if (valor4 > valor3) {
				tmp = valor3;
				valor3 = valor4;
				valor4 = tmp;
			}

			for (int i = valor1; i <= valor2; i++) {
				for (int multiplica = valor3; multiplica <= valor4; multiplica++) {
					resultat = i * multiplica;
					System.out.println(i + " x " + multiplica + " = " + resultat); 
				}
			}
		}	
}