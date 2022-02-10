import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NotaMitja {
    // Main del programa
    public static void main(String[] args) throws IOException {
        String fitxerNotas = "notes.csv";
        BufferedReader input = new BufferedReader(new FileReader(fitxerNotas));
        String notas = input.readLine();
        // Para comprobar si hay notas en el documento o no
        int contador = 0;
        while (true) {
            notas = input.readLine(); 
            if (null == notas) break;
            double mitjaNotas = MitjaNotes(notas);
            String nom = DeterminaNom(notas);
            System.out.printf(nom + " (%.2f)",mitjaNotas);
            System.out.println();
            contador ++;
        }
        if (contador == 0) System.out.println("Cap entrada");
    }

    // Calcula la mitja de notas de cada entrada del document
    public static double MitjaNotes (String notas) {
        double notaMitja = 0;
        String[] elements = notas.split(",");
        for (int i=1;i<elements.length;i++) {
            if (elements[i].equals("NP")) {
                elements[i] = "0";
            }
            double numero = Double.parseDouble(elements[i]);
            notaMitja = notaMitja + numero;
        }
        notaMitja = notaMitja / ((elements.length )- 1);
        return notaMitja;
    }
    // Separa el nom del document i de les notes
    public static String DeterminaNom (String notas) {
        String[] elements = notas.split(",");
        return elements[0];
    }
}