import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
public class Recordat {
    public static void main(String[] args) throws IOException {
        String nouFitxer = "records.txt";
        BufferedWriter output = new BufferedWriter(new FileWriter(nouFitxer));
        while (true) {
            System.out.println("El lloro pregunta paraula:");
            String paraula = Entrada.readLine();
            if (paraula.isBlank()) break;
            output.write(paraula);
            output.newLine();
            System.out.printf("El lloro registra: %s \n", paraula);
        }
        output.close();
    }
}