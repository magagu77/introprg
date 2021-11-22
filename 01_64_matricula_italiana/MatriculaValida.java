/* Desenvolupa un programa que demani a l’usuari una matrícula i li digui si 
   el que li ha introduït correspon a una matrícula italiana vàlida en el 
   format vigent.
   Les lletres poden ser qualsevol lletra majúscula sense caràcters especials 
   com Ç, À, Ñ o ß, i exceptuant les lletres que porten a confusió I, O, Q i U.
*/

public class MatriculaValida {
	public static void main(String[] args) {
		
		System.out.println("Introduïu una matrícula");
		String matricula = Entrada.readLine();
		String valida = "No és una matrícula italiana vàlida";
		if (matricula.length() == 7) {	
			char lletra1 = matricula.charAt(0);
			char lletra2 = matricula.charAt(1);
			if (Character.isLetter(lletra1) && 
				Character.isUpperCase(lletra1) &&
				Character.isLetter(lletra2) && 
				Character.isUpperCase(lletra2)) {
				if (!(lletra1 == 'O' || lletra1 == 'I' || lletra1 == 'Q' || lletra1 == 'U')) {
					if (!(lletra2 == 'O' || lletra2 == 'I' || lletra2 == 'Q' || lletra2 == 'U'))
						if (Character.isDigit(matricula.charAt(2)) && 
							Character.isDigit(matricula.charAt(3)) &&
							Character.isDigit(matricula.charAt(4))) {
							if (Character.isLetter(matricula.charAt(5)) && 
								Character.isUpperCase(matricula.charAt(5)) &&
								Character.isLetter(matricula.charAt(6)) && 
								Character.isUpperCase(matricula.charAt(6))) {
								valida ="És una matrícula italiana vàlida";
							}
						}
				}
			}	
		}
		System.out.println(valida); 
	}	
}   