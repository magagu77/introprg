/*
	.........9.........
	........898........
	.......78987.......
	......6789876......
	.....567898765.....
	....45678987654....
	...3456789876543...
	..234567898765432..
	.12345678987654321.
	0123456789876543210
	.12345678987654321.
	..234567898765432..
	...3456789876543...
	....45678987654....
	.....567898765.....
	......6789876......
	.......78987.......
	........898........
	.........9.........

*/


public class Rombos2 {
	public static void main(String[] args) {
		
		for (int fila = 9; fila >= 0; fila-- ) {
			for (int columna = 0; columna <= 9; columna++) {
				if (columna >= fila) {
					System.out.print(columna);
				} else {
					System.out.print(".");
				}
			}
			for (int columna = 8; columna >= 0; columna--){
				if (columna >= fila) {
					System.out.print(columna);
				} else {
					System.out.print(".");
				}
			}
			System.out.println();
		}
		for (int fila = 1; fila <= 9; fila++ ) {
			for (int columna = 0; columna <= 9; columna++) {
				if (columna >= fila) {
					System.out.print(columna);
				} else {
					System.out.print(".");
				}
			}
			for (int columna = 8; columna >= 0; columna--){
				if (columna >= fila) {
					System.out.print(columna);
				} else {
					System.out.print(".");
				}
			}
			System.out.println();
		}
	}
}
