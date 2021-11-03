//Te pide valor inicial, final y salto pero con for

public class EntersIniciFi {
	public static void main(String[] args) {
		
		System.out.println("Valor inicial?");
		int inici = Integer.parseInt(Entrada.readLine());
		System.out.println("Valor final?");
		int fi = Integer.parseInt(Entrada.readLine());
		System.out.println("Salt?");
		int salt = Integer.parseInt(Entrada.readLine());

		for (int valorCalculat = inici;
			 valorCalculat <= fi;
			 System.out.println(valorCalculat)) {
			 valorCalculat =+ salt;
		}	
	}
}