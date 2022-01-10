/* Programa que compara ocurrencies amb uu  modul que no està dins del mateix 
   arxiu de text pla
*/
public class ComptaAiEs {
    public static void main(String[] args) {
        System.out.println("Introdueix un text");
        String entrada = Entrada.readLine();
        comptaLletra(entrada, 'a');
        comptaLletra(entrada, 'e');
        comparaOcurrencies(entrada);
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
    public static void comparaOcurrencies(String text) {
        int comptadorA = 0;
        int comptadorE = 0;
        for (int i=0; i < text.length(); i++) {
            if (text.charAt(i) == 'a') {
                comptadorA += 1;
            } else if (text.charAt(i) == 'e') {
                comptadorE += 1;
            }
        }
        if (comptadorA > comptadorE) {
            System.out.println("Hi ha més 'a's que 'e's");
        } else if (comptadorA < comptadorE) {
            System.out.println("Hi ha menys 'a's que 'e's");
        } else {
            System.out.println("Hi ha tantes 'a's com 'e's");
        }
    }
}

