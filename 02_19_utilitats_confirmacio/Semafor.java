/*  Otra version del programa semafor pero que utiliza un modulo externo para 
	confirmar si el usuario es mayor de edad
*/
public class Semafor {
	public static void main(String[] args) {
		System.out.println("Ets major d'edat?");
		String text = Entrada.readLine();
		if (UtilitatsConfirmacio.respostaABoolean(text)) {
			System.out.println("Color?");
			String color = Entrada.readLine();
	        if (color.equals("groc")) {
	            System.out.println("corre!");
	        } else if (color.equals("verd")) {
	            System.out.println("passa");
	        } else if (color.equals("vermell")) {
	            System.out.println("espera");
	        } else {
	            System.out.println("ves a l'oculista");
	        }
		} else {
			System.out.println("No pots fer servir aquest programa sense supervisió");
		}	
	}
}