/*  Desenvolupa una nova versió del programa anterior (02_26). Aquest cop, 
	el programa, a banda de mostrar la nota màxima, indicarà també les notes
	introduïdes.
*/
public class NotaMesAlta {
	public static void main(String[] args) {
		int nota = 1;
		int numNota = 1;
		int notaFinal = 0;
		int notaProvisional = 0;
		String text = ("La nota més alta és "+notaFinal+" de les introduïdes: ");
		System.out.println("Introdueix les notes (-1 per finalitzar)");
		while (nota > 0) {
			nota = Integer.parseInt(Entrada.readLine());
			if (nota >= 0 && nota <= 10) {
				numNota++;
				notaProvisional = nota; 
				if (notaFinal < nota) {
				notaFinal = nota;
				}
			} 
			if (numNota == 1) {
				text = text + notaProvisional; 	
			} 
		}
		if (numNota >= 2 ) {
			System.out.println(text);
		} else {
			System.out.println("Com a mínim calen dues notes");
		}
	}
}
