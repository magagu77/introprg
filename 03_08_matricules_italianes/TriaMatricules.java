/* Programa que clasifica las matriculas Italianas que lee en si son validas o no */
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
public class TriaMatricules {
    public static void main(String[] args) throws IOException {
        String matriculesLlegides = "matriculesllegides.txt";
        String matricuaCorrecta = "matriculescorrectes.txt";
        String matriculaErronea = "matriculeserronies.txt";
        BufferedReader lectura = new BufferedReader(new FileReader(matriculesLlegides));
        BufferedWriter correcta = new BufferedWriter(new FileWriter(matricuaCorrecta, true));
        BufferedWriter error = new BufferedWriter(new FileWriter(matriculaErronea, true));
        String matricula = "a";
        while (true) {
            matricula = lectura.readLine();
            if (null == matricula) break;
            if (matriculaItalianaValida(matricula)) {
                correcta.write(matricula);
            } else {
                error.write(matricula);
            }
        }
        correcta.close();
        error.close();
    }
    public static boolean matriculaItalianaValida (String matricula) {
        boolean obtingut;
		if (!(matricula.length() == 7)) return false;
		for (int i=0;i<matricula.length();i++) {
			obtingut = esLletraValidaPerMatriculaItaliana(matricula.charAt(i), i);
			if(!obtingut) return false;
		}
        return true;
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