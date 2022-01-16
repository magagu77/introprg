/*  Programa que muestra un intervalo de caracteres ordenados en funcion de su
	posicion en el String y los valores que tu das como inicio y final
*/
public class MostraInterval {
	public static void main(String[] args) {
		System.out.println("text?");
		String text = Entrada.readLine();
		System.out.println("inici?");
		int inici = Integer.parseInt(Entrada.readLine());
		System.out.println("final?");
		int end = Integer.parseInt(Entrada.readLine());
		System.out.println(UtilString.intervalString(text, inici, end));
	}
}