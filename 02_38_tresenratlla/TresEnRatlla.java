/* Juego del 3 en ralla con modulos */
public class TresEnRatlla {
	public static void main(String[] args) {
		// Variables que se necesitan 
		String jugador="X?";
		String jugador2 = "O?";
		String tmp = "";
		String coordenada="";
		int fila=0;
		int columna=0;
		char player='a';
		//Tauler per defecte
		char[][] taulell = new char [3][3];
		taulell[0][0] = '·';
        taulell[0][1] = '·';
        taulell[0][2] = '·';
        taulell[1][0] = '·';
        taulell[1][1] = '·';
        taulell[1][2] = '·';
        taulell[2][0] = '·';
        taulell[2][1] = '·';
        taulell[2][2] = '·';
        System.out.println("Comença el joc");
        //Bucle del juego
        while(true) {
			mostraTaulell(taulell);
			System.out.println(jugador);
			player = jugador.charAt(0);
			coordenada = Entrada.readLine();
			if (coordenada.equals("a")) {
				System.out.println(player + " abandona");
				break;
			}
			fila = coordenada.charAt(0)-48;
			columna =coordenada.charAt(1)-48;
			//Comprueba casilla ocupada
			if (casellaOcupada(taulell, fila, columna)) {
				System.out.println("Ocupada");
				continue;
			}
			taulell[fila][columna] = player;
			//Comprueba si alguien gana
			if(jugadorGuanya(taulell, player)) {
				System.out.println(player + " Guanya");
				return;
			}
			//Comprueba empate
			if(hihaEmpat(taulell)) {
				System.out.println("Empat");
				return;
			}
			//Cambio de jugadores
			tmp=jugador;
			jugador=jugador2;
			jugador2=tmp;

        }
	}
	public static void mostraTaulell(char[][] tauler) {
		for (int fila=0;fila<tauler.length;fila++) {
			for(int col=0;col<tauler.length;col++){
				System.out.print(tauler[fila][col]);
			}
			System.out.println();
		}
	}
	public static boolean casellaOcupada(char[][] taulell, int fila, int columna){
		if (taulell[fila][columna]=='·') {
			return false;
		} else {
			return true;
		}
	}
	public static boolean hihaEmpat(char[][] tauler) {
		for (int i=0;i<3;i++){
			for(int c=0;c<3;c++){
				if(tauler[i][c]=='·') {
					return false;
				}
			}
		}
		return true;
	}
	public static boolean jugadorGuanya(char[][] taulell, char jugador) {
		for (int fila=0;fila<taulell.length;fila++) {
			if(taulell[fila][0] != jugador){
				return false;
			} else {
				return true;
			}
		}
		for (int fila=0;fila<taulell.length;fila++) {
			if(taulell[fila][1] != jugador) {
				return false;
			} else {
				return true;
			}
		}
		for (int fila=0;fila<taulell.length;fila++) {
			if(taulell[fila][2] != jugador) {
				return false;
			}
			else {
				return true;
			}
		}
		for (int col=0;col<taulell.length;col++) {
			if(taulell[0][col] != jugador) {
				return false;
			} else {
				return true;
			}
		}
		for (int col=0;col<taulell.length;col++) {
			if(taulell[1][col] != jugador) {
				return false;
			} else {
				return true;
			}
		}
		for (int col=0;col<taulell.length;col++) {
			if(taulell[2][col] != jugador) {
				return false;
			} else {
				return true;
			}
		}
		if (taulell[0][0]==jugador && taulell[1][1]==jugador && taulell[2][2]==jugador) {
			return true;
		}
		if (taulell[0][2]==jugador && taulell[1][1]==jugador && taulell[2][0]==jugador) {
			return true;
		}
		return false;
	}
}