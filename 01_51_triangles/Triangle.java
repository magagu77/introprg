/* Dibulo que hay que hacer:
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
Se tiene que repetir tantas veces como quiera el usuario    */

public class Triangle {
	public static void main(String[] args) {
	
	System.out.println("quants?");
	int numeroLoops = Integer.parseInt(Entrada.readLine());
	for (int repeticions = 1; repeticions <= numeroLoops; repeticions++)
		for (int fila = 0; fila <= 9; fila++){
			for (int columna = 0; columna <= 18; columna++){
				if (columna <= 9) {
					if (columna < (9 - fila)) {
						System.out.print(".");
					} else {
						System.out.print(columna);
					}
				} 
				else {
					if (columna > (9 + fila)) {
						System.out.print(".");
					} else {
						System.out.print((18 - columna));
					}
					
				}	
			}

			System.out.println();
		}
	}			
}
	       	