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
		if (nombre.isBlank()) {
			return;
		}
		int numero = Integer.parseInt(nombre);
		 	for (int i = 0; i < numero; i++) {
		 		System.out.print(text.charAt(i));
		 	}
		
	}
}