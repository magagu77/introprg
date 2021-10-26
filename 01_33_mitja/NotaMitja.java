//Mitjana notes while

public class NotaMitja {
    public static void main(String[] args) {
        
        int nota = 0;
        int numeroNotes = 0;
        int sumaTotal = 0;
        int mitjana = 0;
        
        while (nota <= 100 && nota >= 0) {
            System.out.println("Introdueix una nota");
            nota = Integer.parseInt(Entrada.readLine());
            if (nota <= 100 && nota >= 0) {
                numeroNotes = numeroNotes + 1;
                sumaTotal = sumaTotal + nota;
            } 
        }
        if (numeroNotes > 0) {
            mitjana = sumaTotal / numeroNotes;
            System.out.println("La mitja de les notes vàlides és " + mitjana) ;
        } else {
          System.out.println("Cap nota vàlida introduïda");
    }     }
}           
