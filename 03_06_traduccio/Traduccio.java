/* Programa que trabaja con varios ficheros del que recibe el nombre de estos desde args[], y escribe en el último una "traducción "*/
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
public class Traduccio {
    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.out.println("ERROR: Cal especificar els fitxers origen, traduccio i destinació");
            return;
        }
        tradueix(args[0], args[1], args[2]);
    }
    // Coge una linea del primer archivo y escribe en el tercero
    public static void tradueix (String fitxerOrigen, String fitxerTraduccio, String fitxerDestinacio) throws IOException {
        BufferedReader origen = new BufferedReader(new FileReader(fitxerOrigen));
        BufferedReader traduccio = new BufferedReader(new FileReader(fitxerTraduccio));
        BufferedWriter destinacio = new BufferedWriter(new FileWriter(fitxerDestinacio));
        String contador = "a";
        while (true) {
            // Coge linea del primer archivo
            String linia = origen.readLine();
            if (null == linia) break;
            
            if(null == linia) break;
            linia = tradueixLinia(linia, fitxerTraduccio);
            // Escribe en el tercer archivo
            destinacio.write(linia);
            destinacio.newLine();
        }
        destinacio.close();
    }
    // Hace la "traducción"
    public static String tradueixLinia (String linia, String fitxerTraduccio) throws IOException {
        BufferedReader traduccio = new BufferedReader(new FileReader(fitxerTraduccio));
        String aTraduir = traduccio.readLine();
        String[] elements = aTraduir.split(",");
        for (int i = 0; i<elements.length/2;i = i + 2) {
            linia = linia.replace(elements[i].strip(),elements[i+1].strip());
        }
        return linia;
    }
}