import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
public class Recordat {
    public static void main(String[] args) throws IOException {
        String nouFitxer = "records.txt";
        BufferedWriter output = new BufferedWriter(new FileWriter(nouFitxer));
        BufferedReader input = new BufferedReader(new FileReader(nouFitxer));
        String paraula = "a";
        while (true) {
            System.out.println("El lloro pregunta paraula:");
            paraula = Entrada.readLine();
            if (paraula.isBlank()) {
                System.out.println("D'acord");
                break;
            }
            output.write(paraula);
            output.newLine();
            System.out.printf("El lloro registra: %s\n", paraula);
        }
        output.close();
        int contador = 0;
        while (true) {
            paraula = input.readLine();
            if (paraula == null) {
                if(contador == 0){
                    System.out.println("El lloro no recorda res");
                }
                System.out.println("Adéu");
                break;
            }
            System.out.printf("El lloro recorda: %s\n",paraula);
            contador++;
        }
    }
}