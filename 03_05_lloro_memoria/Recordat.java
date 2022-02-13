/* Programa del loro con alzheimer que necesita un fixero para guardar 3 frases*/

// PENDIENTA ACABAR Y SACAR COSAS EN MODULOS

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
public class Recordat {
    public static void main(String[] args) throws IOException {
        String nouFitxer = "records.txt";
        BufferedWriter output = new BufferedWriter(new FileWriter(nouFitxer));
        String paraula = "a";
        while (true) {
            System.out.println("El lloro pregunta paraula:");
            paraula = Entrada.readLine();
            if (procesaEntrada(paraula)) {
                System.out.println("D'acord");
                break;
            }
            paraula = paraula.strip();
            output.write(paraula);
            output.newLine();
            System.out.printf("El lloro registra: %s\n", paraula);
        }
        output.close();
        mostraRecords(nouFitxer);
    }
    public static boolean procesaEntrada(String entrada) {
        if (entrada.isBlank()) return true;
        else return false;
    }
    public static void mostraRecords (String fitxer) throws IOException {
        int contador = 0;
        String paraula = "a";
        BufferedReader input = new BufferedReader(new FileReader(fitxer));
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