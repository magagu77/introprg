/* Programa que comprueba los ficheros o carpetas que se le dan en args[] y muestra el contenido que hay dentro */
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class Inspecciona {
    public static void main(String[] args) throws IOException {
        for(int i=0;i<args.length;i++) {
            File carpeta = new File(args[i]);
            String procesant = "Processant argument: " + args[i];
            String nomFitxer = args[i];
            String[] extensio = nomFitxer.split(".");
            System.out.println(procesant);
            for(int c=0;c<procesant.length();c++) {
                System.out.print("=");
            }
            System.out.println();
            // Comprueba si existe una carpeta o archivo con ese nombre
            if(carpeta.exists()) {
                System.out.print(permisos(args[i]));
                if (carpeta.isDirectory()) {
                    String[] subcarpeta = carpeta.list();
                    if (subcarpeta.length ==0){
                        System.out.println(" directori buit");
                    } else {
                        String fitxersCarpeta = generaNoms(subcarpeta);
                        System.out.printf(" directori que conté: %s\n", fitxersCarpeta);
                    }
                } else if (carpeta.isFile()) {
                    if (carpeta.length() == 0){
                        System.out.println(" fitxer buit");
                    } else {
                        BufferedReader input =new BufferedReader(new FileReader(args[i]));
                        System.out.printf(" fitxer de mida en bytes: %s\n", carpeta.length());
                        System.out.println(extensio.length);
            /*            if(extensio[1].equals("txt")|| extensio[1].equals("java")){
                            System.out.println("Amb els continguts:");
                            while (true) {
                                String contingut = input.readLine();
                                if (contingut == null) break;
                                System.out.printf("|%s|\n",contingut);
                            }
                            input.close();
                        } */
                    }
                }
            }
            
            else {
                System.out.println("No trobat");
                System.out.println();
                break;
            }
        }
    }
    // Modulo para comprobar los permisos de un archivo o carpeta
    public static String permisos (String nom) throws IOException {
        File archivo = new File(nom);
        String permisos = "";
        if (archivo.canRead()) {
            permisos = permisos + "r";
        } else {
            permisos = permisos + "-";
        }
        if (archivo.canWrite()) {
            permisos = permisos + "w";
        } else {
            permisos = permisos + "-";
        }
        if (archivo.canExecute()) {
            permisos = permisos + "x";
        } else {
            permisos = permisos + "-";
        }
        return permisos;
    }
    public static String generaNoms(String[] fitxersCarpeta) {
        String fitxers ="";
        for (int i=0;i<fitxersCarpeta.length;i++) {
            if (i==0) {
                fitxers = fitxersCarpeta[i];
            } else {
                fitxers = fitxers +", " + fitxersCarpeta[i];
            }
        }
        return fitxers;
    }
}