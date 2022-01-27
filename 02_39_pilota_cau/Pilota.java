/* Ejercicio en el que cae una pelota y se borra la pantalla a cada paso */

public class Pilota {
    public static final int N_FILES =9;  // mínim 5 per passar els tests
    public static final int N_COLS = 13;  // mínim 5 per passar els tests

    public static void netejaPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static void mostraCamp(char[][] camp) {
        for (int i=0; i<N_FILES; i++) {
            for (int j=0; j<N_COLS; j++) {
                System.out.print(camp[i][j]);
            }
            System.out.println();
        }
    }
    public static void netejaCamp(char[][] camp) {
        for (int fila=0;fila<camp.length;fila++) {
        	for (int col=0;col<camp.length;col++) {
        		camp[fila][col] = '·';
         	}
        }
    }
    public static void netejaPosicio(char[][] camp, int fila, int col) {
        camp[fila][col] = '·';
    }
    public static void posicionaPilota(char[][] camp, int fila, int col) {
        camp[fila][col] = 'O';
    }
    public static int seguentFila(int actual) {
        if (actual == N_FILES-1) {
      	 	actual = 0;
         	return actual;
        } else {
     	  	actual++;
         	return actual;
        } 	
    }
    public static int seguentCol(int actual) {
		if (actual == N_COLS-1) {
         	actual = 0;
         	return actual;
        } else {
         	actual++;
         	return actual;
        } 
    }
    public static void main(String[] args)  {
        char[][] camp = new char[N_FILES][N_COLS];
        netejaCamp(camp);
        int fila = 0;
        int col = 0;
        while (true) {
            // posiciona la pilota i mostra el camp
            posicionaPilota(camp, fila, col);
            netejaPantalla();
            mostraCamp(camp);
            // neteja la posició actual
            netejaPosicio(camp, fila, col);
            // actualitza següent fila i columna
            fila = seguentFila(fila);
            col = seguentCol(col);
            // espera resposta
            System.out.printf("%nEnter per continuar");
            Entrada.readLine();
        }
    }
}