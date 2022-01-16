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
			int numero = Integer.parseInt(quants);
			String text = UtilString.cadenaContinua(entrada, numero);
			System.out.println(text);
		} else {
			System.out.println("error");
		}		
	}
}