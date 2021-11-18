//un programa que demana dos texts i un enter positiu i composa un petit informe amb el resultat de les funcions del tipus String

public class InformeString {
	public static void main(String[] args) {
		
		System.out.println("Text principal?");
		String paraula1 = Entrada.readLine();
		System.out.println("Text secundari?");
		String paraula2 = Entrada.readLine() ;
		System.out.println("Número positiu?");
		int numero = Integer.parseInt(Entrada.readLine());
		String inici = String.format("\"" + paraula1 + "\"" );
		String mitad = String.format("\"" + paraula2 + "\"" );


		System.out.println(inici + ".length():" + paraula1.length());
		String missatge = String.format(inici + ".startsWith(%s): " + paraula1.startsWith(paraula2), mitad);
		System.out.println(missatge);
		missatge = String.format(inici + ".endsWith(%s): " + paraula1.endsWith(paraula2), mitad);
		System.out.println(missatge);
		missatge = String.format(inici + ".equals(%s): " + paraula1.equals(paraula2), mitad);
		System.out.println(missatge);
		missatge = String.format(inici + ".equalsIgnoreCase(%s): " + paraula1.equalsIgnoreCase(paraula2), paraula2);
		System.out.println(missatge);
		missatge = String.format(inici + ".isBlank(): " + paraula1.isBlank());
		System.out.println(missatge);
		missatge = String.format(inici + ".isEmpty(): " + paraula1.isEmpty());
		System.out.println(missatge);
		missatge = String.format(inici + ".charAt(%d): " + paraula1.charAt(numero), numero);
		System.out.println(missatge);
		missatge = String.format(inici + ".concat(%s): " + paraula1.concat(paraula2), mitad);
		System.out.println(missatge);
		missatge = String.format(inici + ".repeat(%d): " + paraula1.repeat(numero), numero);
		System.out.println(missatge);
		missatge = String.format(inici + ".toUpperCase(): " + paraula1.toUpperCase());
		System.out.println(missatge);
		missatge = String.format(inici + ".toLowerCase(): " + paraula1.toLowerCase());
		System.out.println(missatge);
	}
}