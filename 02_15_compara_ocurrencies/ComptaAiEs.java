/* Programa que compara ocurrencies amb uu  modul que no està dins del mateix 
   arxiu de text pla
*/
public class ComptaAiEs {
    public static void main(String[] args) {
        System.out.println("Introdueix un text");
        String entrada = Entrada.readLine();
        char lletra1 = 'a';
        char lletra2 = 'e';
        comptaLletra(entrada, lletra1);
        comptaLletra(entrada, lletra2);
        comparaOcurrencies(entrada, lletra1, lletra2);
    }
    public static void comptaLletra(String text, char lletra) {
        int comptador = 0;
        for (int i=0; i < text.length(); i++) {
            if (text.charAt(i) == lletra) {
                comptador += 1;
            }
        }
        System.out.println("Nombre de '" + lletra + "'s: " + comptador);  
    }
    public static void comparaOcurrencies(String text, char lletra1, char lletra2) {
        int comptador1 = 0;
        int comptador2 = 0;
        for (int i=0; i < text.length(); i++) {
            if (text.charAt(i) == lletra1) {
                comptador1 += 1;
            } else if (text.charAt(i) == lletra2) {
                comptador2 += 1;
            }
        }
        if (comptador1 > comptador2) {
            System.out.println("Hi ha més 'a's que 'e's");
        } else if (comptador1 < comptador2) {
            System.out.println("Hi ha menys 'a's que 'e's");
        } else {
            System.out.println("Hi ha tantes 'a's com 'e's");
        }
    }
}

