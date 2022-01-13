/*  Programa que determina si una matricula italiana es valida o no 
	con un modulo que debuelve un valot boolean
*/
public class MatriculaValida {
	public static void main(String[] args) {
		System.out.println("Introduïu una matrícula");
		String matricula = Entrada.readLine();
		boolean matriculaOk;
		if (matricula.length() < 7) {
			System.out.println("No es una matrícula italiana vàlida");
			return;
		}
		for (int i=0;i<matricula.length();i++) {
			matriculaOk = esLletraValidaPerMatriculaItaliana(matricula.charAt(i), i);
			if(!matriculaOk) {
				System.out.println("No és una matrícula italiana vàlida");
				return;
			}
		}
		System.out.println("És una matrícula italiana vàlida");
	}
	public static boolean esLletraValidaPerMatriculaItaliana(char lletra, int posicio) {
		if (posicio<=1 || posicio>=5) {
			if (Character.isLetter(lletra) && Character.isUpperCase(lletra) && lletra >= 'A' && lletra <= 'Z' && 
				!(lletra == 'I' || lletra == 'O' ||lletra == 'Q' || lletra == 'U')) {
						return true;
			} else {
				return false;
			}
		} else if (posicio >=2 && posicio <=4) {
			if (Character.isDigit(lletra)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
}