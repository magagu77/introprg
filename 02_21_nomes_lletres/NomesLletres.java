/*  Programa que muestra las letras de un texto separadas por comas,
	el resto de caracteres no se mostraran
*/
public class NomesLletres {
	public static void main(String[] args) {
		System.out.println("Text?");
        String text = Entrada.readLine();
        String nomesLletres = UtilString.nomesLletres(text);
        String separades = UtilString.lletresSeparades(nomesLletres);
        System.out.println(separades);
    }
}