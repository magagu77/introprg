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
			for (int end = 0; end < matricula.length(); end++) {
				if (end <= 1 || end > 4) {
					if (Character.isLetter(matricula.charAt(end)) && Character.isUpperCase(matricula.charAt(end)) && end >= 'A' && end <= 'Z')
						valida = "És una matrícula italiana vàlida";
				}
				if (end >= 2 && end <= 3) { 
					if (Character.isDigit(matricula.charAt(end))) {
						valida = "És una matrícula italiana vàlida";
					}
				} else {
					valida = "No és una matrícula italiana vàlida";
					break;
				}	
			}
		}

		System.out.println(valida); 
	}	
}   