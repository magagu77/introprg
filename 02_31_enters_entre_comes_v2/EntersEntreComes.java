/* Una nova versió del programa enters entre comes>

	En aquesta ocasió, el programa tindrà les següents modificacions:

    els valors de l’array en comptes de ser assignats des del programa, els especificaran els usuaris del programa.

    De moment suposarem que els valors d’entrada són sempre enters vàlids.

    en comptes de 3 valors a l’array, n’hi haurà 5
*/
public class EntersEntreComes {
	public static void main(String[] args) {
		int[] valor = new int[5];
		for (int i=0;i<valor.length; i++){
			System.out.println("Valor " + (i+1) + "?");
			valor[i] = Integer.parseInt(Entrada.readLine());
		}
		System.out.print(valor[0]);
		for (int v=1; v<valor.length;v++) {
			System.out.print(", " + valor[v]);
		}
	}
}