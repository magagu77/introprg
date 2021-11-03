// Suma todos los numeros de un intervalo con un for

public class SumaInterval {
    public static void main (String[] args) {
        
        System.out.println("inici?");
        int inici = Integer.parseInt(Entrada.readLine());
        System.out.println("final?");
        int fi = Integer.parseInt(Entrada.readLine());
        int suma = 0;
        for (int interval = inici;
             interval <= fi;
             interval = interval + 1) {
             suma = suma + interval; 
        }
        for (int interval = inici;
             interval >= fi;
             interval = interval - 1) {
             suma = suma + interval; 
        }
        if (inici == fi) {
        suma = inici;
        }
        System.out.println(suma);
    }
}        
