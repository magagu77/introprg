/*  Una nova versió del programa enters entre comes.

	En aquesta ocasió, el programa tindrà les següents modificacions:

    inicialment demanarà als usuaris quants valors vol introduir. En cas que 
    demani menys de 1, el programa no donarà cap error però finalitzarà amb el 
    missatge "Res a fer"

    es crea un array amb espai suficient per emmagatzemar el nombre de valors 
    requerit

	Podem seguir considerant que els valors d’entrada seran enters.
*/
public class EntersEntreComes {
	public static void main(String[] args) {
		System.out.println("Quants?");
		int quants = Integer.parseInt(Entrada.readLine());
		int[] valor = new int[quants];
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