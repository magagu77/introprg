/*  Programa que analiza los argumentos de pones al ejecutar un programa y 
	devuelve de que tipo son
*/

public class Arguments {
	public static void main(String[] args) {
		for(int i=0;i<args.length;i++) {
			if(UtilString.esEnter(args[i])) {
				System.out.println("["+i+"]"+" \""+args[i]+"\": és enter");
			} else {
				System.out.println("["+i+"]"+" \""+args[i]+"\": no és enter");
			} 
		}
	}
}