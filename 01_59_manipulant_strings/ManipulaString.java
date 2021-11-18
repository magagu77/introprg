/* Completa el següent programa, de manera que demani un text i mostri 
   els aspectes demanats als comentaris.
*/

public class ManipulaString {
	public static void main(String[] args) {
			
		System.out.println("Paraula?");
		String paraula = Entrada.readLine();
		System.out.println("La longitud és "+ paraula.length() );
		System.out.println("La segona lletra és '" + paraula.charAt(1) + "'");
		System.out.println("La darrera lletra és '" + paraula.charAt((paraula.length() - 1)) + "'");
		System.out.println("La penúltima lletra és '" + paraula.charAt((paraula.length() - 2)) + "'");
		if (paraula.length() < 3){
			System.out.println("La paraula en majúscules és \"" + paraula.toUpperCase() + "\"");
			System.out.println("La composició de quatre primeres és \"" + Character.toUpperCase(paraula.charAt(0)) + Character.toLowerCase(paraula.charAt(1)) + Character.toLowerCase(paraula.charAt(2)) + Character.toLowerCase(paraula.charAt(3)) + "\"");
			System.out.println("La composició de quatre és \"" + Character.toLowerCase(paraula.charAt((paraula.length() - 1))) + Character.toUpperCase(paraula.charAt(0)) + Character.toLowerCase(paraula.charAt(1)) + Character.toLowerCase(paraula.charAt(2)) + "\"");
			System.out.println("La composició de quatre corregint majúscules és \"" + Character.toUpperCase(paraula.charAt((paraula.length() - 1))) + Character.toLowerCase(paraula.charAt(0)) + Character.toLowerCase(paraula.charAt(1)) + Character.toLowerCase(paraula.charAt(2)) + "\"");
		}

	}
}   