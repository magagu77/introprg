/*Desenvolupa un programa que demani un text i un nombre enter, i mostri tants
  caràcters del text com indiqui el nombre enters, començant pel primer. En cas
  que en faltin, el programa tornarà a mostrar el text a partir del primer caràcter
  fins que hagi aconseguit tots els caràcters demanats.
*/

public class CadenaContinua {
	public static void main(String[] args) {
		System.out.println("Text?");
		String text = Entrada.readLine();
		if (text.isBlank()) {
			System.out.println("error");
			return;
		}
		System.out.println("Nombre?");
		String nombre = Entrada.readLine();
		if (nombre.isBlank() || nombre.equals("0")) {
			return;
		}
		int numero = Integer.parseInt(nombre);
		int divisio = numero / (text.length());
		if (numero <= text.length()){
			for(int i = 0; i < numero; i++) {
				System.out.print(text.charAt(i));
			}
		}	
		if (divisio > 1) {
			for (int d = 1; d < divisio; d++) {
				for(int p = 0; p < (text.length() - 1); p++) {
					System.out.print(text.charAt(p));
				}
			}
		}
	}
}