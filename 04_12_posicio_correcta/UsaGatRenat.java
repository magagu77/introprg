//Programa que usa GatRenat pero con getters y setters
public class UsaGatRenat {
    public static void main(String[] args) {
        GatRenat renat = new GatRenat();
        System.out.println("Vides inicials: " + renat.getVides());
        System.out.println("Posició inicial: " + renat.getPosicio());
        System.out.println("Introdueix nova posició:");
        renat.setPosicio(Entrada.readLine());
        System.out.println("Posició final: " + renat.getPosicio());
    }
}