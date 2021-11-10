//Dibujar triangulos

public class Triangles2 {
	public static void main(String[] args) {
		
		for(int fila = 9; fila >= 0; fila--) {
			for (int columna = 0; columna <= 9; columna++) {
				if (columna >= fila  ) {
					System.out.print(columna);
				} else {
					System.out.print(".");
				}
			}
			for (int columna = 8; columna >= 0; columna-- ) {
				if (columna >= fila) {
					System.out.print(columna);
				} else {
					System.out.print(".");
				}
			} 
			System.out.println("");
		}
	}
}