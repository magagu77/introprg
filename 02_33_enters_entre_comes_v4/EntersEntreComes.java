/* En aquesta ocasió, el programa tindrà les següents modificacions:

    el programa permetrà decidir quin és el caràcter de separació a mostrar 
    entre els valors.

    Per fer-ho, demanarà aquest caràcter i agafarà el primer caràcter que 
    li introdueixin ignorant la resta. En cas que la cadena introduïda sigui 
    buida, considerarà la coma.

    la separació dels valors numèrics, la realitzarà un mòdul amb la següent 
    signatura:
    public static String entreComes(int[], char)

    El primer paràmetre correspon amb l’array d’enters mentre que el segon 
    indica el caràcter de separació entre un valor i el següent.

    Malgrat el nostre programa no ho necessita, entreComes() serà capaç de 
    funcionar correctament quan l’array estigui buit (és a dir, tingui longitud 0)

    Com aquest mòdul pot ser útil en futures ocasions i retorna un String, 
    de moment el posarem a UtilString

    Ja no podem considerar que els valors que ens introdueixin els 
    usuaris seran sempre adequats. Sort que tenim UtilString.esEnter() oi?

Podem seguir considerant que els valors d’entrada seran enters.
*/



/*

POR ACABAR USANDO UTILSTRING, PENDIENTE ACABAR LOS EJERCICIOS DE MODULOS ANTES DE SEGUIR


*/
public class EntersEntreComes {
	public static void main(String[] args) {
		System.out.println("Quants?");
		String repeticions;
		boolean repetir = true;
		int quants = 0;
		char separador;
		while(repetir) {
			repeticions = Entrada.readLine();
			if (Character.isDigit(repeticions.charAt(0)) || Character.isDigit(repeticions.charAt(1))){
				quants = Integer.parseInt(repeticions);
				repetir = false;
				if (quants < 0) {
					System.out.println("Res a fer");
					return;
				} else if (quants == 0) {
					System.out.println("Per favor, un valor enter");
					repetir = true;
				}
			} else {
				System.out.println("Per favor, un valor enter");
			}
		}
		
		// Pide separador
		System.out.println("Separador?");
		String separar = Entrada.readLine();
		if (separar.isBlank()) {
			separador = ',';
		} else {
			separador = separar.charAt(0);
		}
		int[] valor = new int[quants];
		String entrada = "";

		// Pide numeros
		for (int i=0;i<valor.length; i++){
			repetir = true;
			System.out.println("Valor " + (i+1) + "?");
			entrada = Entrada.readLine();
			while(repetir) {
				if (!entrada.isBlank() && Character.isDigit(entrada.charAt(0))) {
					valor[i] = Integer.parseInt(entrada);
					repetir = false;
				} else {
					System.out.println("Per favor, un valor enter");
					entrada = Entrada.readLine();	
				}
			}
		}
		String text = entreComes(valor, separador);
		System.out.println(text);
	}

	public static String entreComes(int[] valor, char separador) {
		String numeros = "";
		for (int i=0; i<valor.length; i++) {
			if (i==0) {
				numeros = numeros + valor[0];
			}
			else {
				numeros = numeros + separador + " " + valor[i];
			}
		}
	return numeros;
	} 
}