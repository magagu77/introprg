public class LaForma {
	public static void main(String[] args) {
	
		System.out.println("Quin caracter vols que apareixi com caracter principal?");
		String p = Entrada.readLine();
		char principal = p.charAt(0);
		System.out.println("Quin caracter vols que apareixi com caracter secundari?");
		String  s = Entrada.readLine(); 
		char secundari = s.charAt(0);
		int gran = 18;	
		System.out.println("quants?");
		int numeroLoops = Integer.parseInt(Entrada.readLine());
		int meitat = gran / 2;

		for (int repeticions = 1; repeticions <= numeroLoops; repeticions++) {
			for (int fila = 0; fila <= gran; fila++) {
				for (int columna = 0; columna <= gran; columna++){
					if (fila <= meitat && columna <= meitat) {
						System.out.print(principal);	 
					} else if ( fila <= meitat && columna > meitat) {
						System.out.print(principal);

					} else if (fila > meitat && columna <= meitat) {
						if (columna < (fila - meitat)) {
							System.out.print(secundari);	
						} else {
							System.out.print(principal);
						}
					} else if (fila > meitat && columna >= meitat) {
						if ((gran - columna) >= (fila - meitat)) {
							System.out.print(principal);
						} else {
							System.out.print(secundari);	
						}
					}
				}
				System.out.println();
			}
		}			
	}
}	