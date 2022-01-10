/*  Desenvolupa un programa que mostri tres enters separats per comes tenint 
	en comptes que els tres valors es troben emmagatzemats en un array d’enters.
*/
public class EntersEntreComes {
	public static void main(String[] args) {
		int[] numeros;
		numeros = new int[3];
		numeros[1] = 1;
		numeros[2] = 2;
		numeros[3] = 3;
		System.out.print(numeros[1]);
		for (int i=1;i<numeros.length;i++) {
			System.out.print(", " +numeros[i]);
		}
		System.out.println();
	}
}