//Loop inside loop con numeros

public class QuadratNombres {
    public static void main(String[] args) {
    
        System.out.println("Valor final?");
        int numeroLoops = Integer.parseInt(Entrada.readLine());
        
        for (int fila = 1; fila <= numeroLoops; fila++) {
            for (int columna = 1; columna <= numeroLoops; columna++) {
                System.out.println(" " + columna);
            }
            System.out.println();
        }
    }
}
