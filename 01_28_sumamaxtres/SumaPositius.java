public class SumaPositius {
    public static void main(String[] args) {

        // creem la variable on guardarem el resultat de sumar
        int suma = 0;   // inicialment no hem sumat res i per tant és 0

        // demanem valors

        // declarem la variable que contindrà els valors llegits
        int valor;

        // processem el primer valor
        System.out.println("Introdueix el primer valor");
        valor = Integer.parseInt(Entrada.readLine());
        if (valor >= 0) {
            suma = suma + valor;
        }

        // processem el segon valor
        System.out.println("Introdueix el segon valor");
        valor = Integer.parseInt(Entrada.readLine());
        if (valor <= 0) {
            suma = suma + valor;
            System.out.println("La suma és " + suma);
        }

            //Es mostren el resultats despres de intentar la segona suma ja que el valor ha de ser major o igual a 0      
           else if (valor >= 0) {
            suma = valor + suma;
            System.out.println("Introdueix el tercer valor");
            valor = Integer.parseInt(Entrada.readLine());
            suma = suma + valor;
            System.out.println("La suma és " + suma);
        }
            
    }
}
