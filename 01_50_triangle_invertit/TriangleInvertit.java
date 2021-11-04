/* loop de triangulos con comas y invertido
  1, 2, 3, 4, 5
  1, 2, 3, 4
  1, 2, 3
  1, 2
  1                              */
  
public class TriangleInvertit {
    public static void main(String[] args) {
        
        System.out.println("Nombre?");
        int numeroLoops = Integer.parseInt(Entrada.readLine());
        
        if (numeroLoops > 0) {
            for (int fila = numeroLoops; fila > 0; fila--) {
                for (int columna = 1; columna <= fila; columna++) {
                    if (!(columna == fila)) {
                        System.out.print(columna + ", ");
                    } else {    
                            System.out.print(columna);
                      }          
                }
            System.out.println("");
            }
        }
    }
}                        
