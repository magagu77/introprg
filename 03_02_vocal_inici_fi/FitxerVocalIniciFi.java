/* Programa del loro pero coge el texto de un archivo externo */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class FitxerVocalIniciFi {
    public static void main(String[] args) throws IOException {
        String lloro = "frases.txt";
        BufferedReader input = new BufferedReader(new FileReader(lloro));
        while (true) {
            String linia = input.readLine();
            if (null == linia) {
                break;
            } else if (UtilString.esVocal(linia.charAt(linia.length()-1))) {
                System.out.println(linia);
            } else if(UtilString.esVocal(linia.charAt(0))) {
                System.out.println(linia);
            }
        }
        input.close();
    }
}