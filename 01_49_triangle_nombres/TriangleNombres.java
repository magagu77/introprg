/* loops con triangulo invertido
  1
  2 1
  3 2 1
  4 3 2 1
  5 4 3 2 1 */
  
public class TriangleNombres {
    public static void main(String[] args) {
        
        System.out.println("Nombre");
        int numeroLoops = Integer.parseInt(Entrada.readLine());
            for (int fila = 1; fila <= numeroLoops; fila++) {
                for (int columna = fila; columna > 0; columna--) { 
                    System.out.print(" " + columna); 
                }
                System.out.println("");
            
            }
    }
}                          
