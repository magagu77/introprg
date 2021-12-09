/* Desenvolupa una nova versió de l’exercici que dibuixava un triangle de lletres.

   La nova versió tindrà la mateixa sortida que l’original però el codi del 
   programa serà més modular. En concret, el mòdul main() se se n’encarregarà 
   d’obtenir les dades d’entrada, i cridarà un nou mòdul anomenat dibuixaTriangle() 
   que serà qui realitzi realment la feina de mostrar el triangle. De fet, aquest 
   es recolzarà en un altre anomenat dibuixaLinia() que serà qui realment faci la 
   feina de dibuixar cada línia del triangle.
*/
public class TriangleLletres {
	public static void main(String[] args) {
		System.out.println("Text?");
		String text = Entrada.readLine();
		dibuixaTriangle(text);
	}
	public static void dibuixaTriangle(String text) {
		for (int f = 0; f <= text.length(); f++) {
			for (int c = 0; c < f; c++) {
				if (c + 1 == f) {
					System.out.print(text.charAt(c));
				} else
					System.out.print(text.charAt(c) + ", ");
			}
			System.out.println();
		}
	}
}