/*	...................9...................
	..................898..................
	.................78987.................
	................6789876................
	...............567898765...............
	..............45678987654..............
	.............3456789876543.............
	............234567898765432............
	...........12345678987654321...........
	..........0123456789876543210..........
	.........9...................9.........
	........898.................898........
	.......78987...............78987.......
	......6789876.............6789876......
	.....567898765...........567898765.....
	....45678987654.........45678987654....
	...3456789876543.......3456789876543...
	..234567898765432.....234567898765432..
	.12345678987654321...12345678987654321.
	0123456789876543210.0123456789876543210
*/

public class Trilal {
	public static void main(String[] args) {
		
		for (int fila = 0; fila <= 19; fila++) {
			for (int columna = 0; columna <= 38; columna++) {
				if (fila <= 9) {	
					if (columna <= 19) {
						if (columna < (19 - fila)) {
							System.out.print(".");
						} else {
							System.out.print(columna - 10);
						}
					} else if (columna > 19) {
						if (columna < (20 + fila)) {
							System.out.print(28 - columna);
						} else {
							System.out.print(".");
						}
					}
				} else if ( fila > 9) {
					if (columna <= 9) {
						if (columna < 19 - fila ) {
							System.out.print(".");
						} else { 
							System.out.print(columna);
						}
					} else if (columna > 9 && columna < 19) {
						if (columna < fila ) {
							System.out.print(18 - columna);
						} else {
							System.out.print(".");
						}
					} else if (columna == 19) {
						System.out.print(".");
					} else if (columna > 19 && columna <= 29) {
						if (columna - 20 < 9 - (fila - 10) ) {
							System.out.print(".");
						} else {
								System.out.print(columna - 20);
						}
					} else { 
						if (columna - 21 < 9 + (fila - 10)){
							System.out.print(38 - columna);
						} else {
						    System.out.print(".");
						}
					}
					
				}
			}
			System.out.println();
		}
	}
}
