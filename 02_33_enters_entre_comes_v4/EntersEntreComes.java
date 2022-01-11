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
public class EntersEntreComes {
	public static void main(String[] args) {
		System.out.println("Quants?");
		String repeticions = Entrada.readLine();
		boolean repetir = true;
		int quants = 0;
		while(repetir) {
			if (Character.isDigit(repeticions.charAt(0))){
				quants = Integer.parseInt(repeticions);
				repetir = false;
			} else {
				System.out.println("Per favor, un valor enter");
			}
		}
		System.out.println("Separador?");
		String separar = Entrada.readLine();
		char separador = separar.charAt(0);
		int[] valor = new int[quants];
		for (int i=0;i<valor.length; i++){
			System.out.println("Valor " + (i+1) + "?");
			valor[i] = Integer.parseInt(Entrada.readLine());
		}
		String text = entreComes(valor, separador);
		System.out.println(text);
	}

	public static String entreComes(int[] valor, char separador) {
		String numeros = "";
		System.out.println(valor.length);
		for (int i=0; i<valor.length; i++) {
			if (i==0) {
				numeros = numeros + valor[0];
			}
			else {
				numeros = separador + " " + valor[i];
			}
		}
		return numeros;
	}
}