/* Ejercicio que consiste en contar las vocales de un texto (vocales catalanas)*/



public class ComptaVocals {
    public static void main(String[] args) {
        char[] vocals = {'a','à','e','è','é','i','í','o','ó','ò','u','ú'};
        char vocal;
        System.out.println("Introdueix un text");
        String text = Entrada.readLine();
        //Empieza un bucle que va vocal por vocal contandolas y 
        for(int i=0;i<vocals.length;i++) {
        	// Cambia la vocal dependiendo del ciclo del bucle
        	vocal = vocals[i];
        	mostraOcurrencies(vocal, quantesOcurrencies(text, vocal));
        }
    }
    // Print que se repite por cada letra
    public static void mostraOcurrencies(char lletra, int quantes) {
        System.out.println("Nombre de '" + lletra + "'s: " + quantes);
    }
    // Contador
    public static int quantesOcurrencies(String text, char lletra) {
        int comptador = 0;
        for (int i=0; i < text.length(); i++) {
            if (text.charAt(i) == lletra) {
                comptador += 1;
            }
        }
        return comptador;
    }
}