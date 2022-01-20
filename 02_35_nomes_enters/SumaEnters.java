/* Programa que suma enteros que recibe por args[] y 
   cuando no hay ningun entero el resultado es 0
*/
public class SumaEnters {
	public static void main(String[] args) {
	System.out.println(sumaEnters(filtraEnters(args)));	
	}
	public static int quantsEnters(String[] valors) {
		int valor = 0;
		for (int i=0;i<valors.length;i++){
			if (UtilString.esEnter(valors[i])) {
				valor++;
			}
		}
		return valor;
	}
	public static int[] filtraEnters(String[] valors) {
		int valor[]=new int[quantsEnters(valors)];
		for(int i=0;i<valors.length;i++) {
			if (UtilString.esEnter(valors[i])) {
				int contador=0;
				valor[contador] = Integer.parseInt(valors[i]);
				contador++;
			}
		}
		return valor;
	}
	public static int sumaEnters(int[] valors) {
		int suma=0;
		for(int i=0;i<valors.length;i++) {
			suma =valors[i] + suma;

		}
		return suma;
	}
}