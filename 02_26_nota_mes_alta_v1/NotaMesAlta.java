/*  Desenvolupa un programa que vagi demanant les notes obtingudes pels estudiants 
	de programació en el darrer examen (com a mínim dues) i indiqui quina ha estat
	la nota més alta.
*/
public class NotaMesAlta {
	public static void main(String[] args) {
		int nota = 1;
		int numNota = 0;
		int notaFinal = 0;
		for (int i = 1; i < 0; i++) {
			System.out.println("Introdueix les notes (-1 per finalitzar)");
			nota = Integer.parseInt(Entrada.readLine());
			if (nota >= 0) {
				numNota++;
			}
			if (notaFinal < nota) {
				notaFinal = nota;
			}
		}
		if (numNota > 2 ) {
			System.out.println("La nota més alta és " + notaFinal);
		} else {
			System.out.println("Com a mínim calen dues notes");
		}
	}
}
