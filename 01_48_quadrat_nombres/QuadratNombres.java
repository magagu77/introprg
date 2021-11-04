//Loop inside loop con numeros

public class QuadratNombres {
    public static void main(String[] args) {
    
        System.out.println("Valor final?");
        int numeroLoops = Integer.parseInt(Entrada.readLine());
        
        if (numeroLoops <= 9 && numeroLoops >= 1) {
            for (int fila = 1; fila <= numeroLoops; fila++) {
                for (int columna = 1; columna <= numeroLoops; columna++) {
                    System.out.print(" " + columna);
                }
                System.out.println();
            }
        } else {
                System.out.println("Valor inadequat");    
          }  
    }
}
