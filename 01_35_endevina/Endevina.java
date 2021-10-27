//programa que es pensarà un nombre enter entre el 1 i el 100, i reptarà els usuaris a que l’endevini.

public class Endevina {
    public static void main(String[] args) {
        
        int nombre = 0;
        int nombrePredefinit = 42;
        
        while (!(nombre == nombrePredefinit)) {
            System.out.println("Introdueix un nombre entre el 1 i el 100");
            nombre = Integer.parseInt(Entrada.readLine());
                if (nombre < nombrePredefinit) {
                    System.out.println("És més gran que " + nombre);
                } else if (nombre > nombrePredefinit) {
                      System.out.println("És més petit que " + nombre);
                } else if (nombre <= 0 ) {
                      System.out.println("Com a mínim 1");
                } else if (nombre > 100 ) {
                      System.out.println("Com a màxim 100");  
                } else {
                      System.out.println("Has encertat!");
                }
        }
    }
}  
