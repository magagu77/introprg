/* Desenvolupa un programa que demani un text i, respecte el primer caràcter,
   composi un petit informe amb el resultat de les funcions dels caracters
*/

public class InformeCaracter {
	public static void main(String[] args) {
		

		System.out.println("Caràcter?");
		String paraula = Entrada.readLine();
		if (paraula.isEmpty()) {
			System.out.println("Caràcter buit");
		} else {
			char lletra = paraula.charAt(0);
			System.out.println("Character.getName('" + lletra + "'): " + Character.getName(lletra));
			System.out.println("Character.isDigit('" + lletra + "'): " + Character.isDigit(lletra));
			System.out.println("Character.isJavaIdentifierStart('" + lletra + "'): " + Character.isJavaIdentifierStart(lletra));
			System.out.println("Character.isJavaIdentifierPart('" + lletra + "'): " + Character.isJavaIdentifierPart(lletra));
			System.out.println("Character.isLetter('" + lletra + "'): " + Character.isLetter(lletra));
			System.out.println("Character.isLowerCase('" + lletra + "'): " + Character.isLowerCase(lletra));
			System.out.println("Character.isUpperCase('" + lletra + "'): " + Character.isUpperCase(lletra));
			System.out.println("Character.isWhitespace('" + lletra + "'): " + Character.isWhitespace(lletra));
			System.out.println("Character.toLowerCase('" + lletra + "'): " + Character.toLowerCase(lletra));
			System.out.println("Character.toUpperCase('" + lletra + "'): " + Character.toUpperCase(lletra));
		} 
		
			
	}
}   