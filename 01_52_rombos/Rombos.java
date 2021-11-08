/* En aquest programa s'ha de desenvolupar un codi que permeti dibuixar
 un numero x derombos segons l'entrada ue es doni. Exemple de rombo
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

public class Rombos {
	public static void main(String[] args) {
	
	System.out.println("quants?");
	int numeroLoops = Integer.parseInt(Entrada.readLine());
	for (int repeticions = 1; repeticions <= numeroLoops; repeticions++)
		for (int fila = 0; fila <= 18; fila++){
			for (int columna = 0; columna <= 18; columna++){
				if (fila <= 9){
					if (columna <= 9) {
						if (columna < (9 - fila)) {
							System.out.print(".");
						} else {
							System.out.print(columna);
						}
					} else if (columna > 9){
						if (columna > (9 + fila)) {
							System.out.print(".");
						} else {
							System.out.print((18 - columna));
						}
					}	
				} else {
					if (columna <= 9){
						if (columna  < (fila - 9)) {
							System.out.print(".");
						} else {
							System.out.print(columna);
						}	
					} else {	
						if ((18 - columna)  >= (fila - 9)) {
							System.out.print(18 - columna);
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