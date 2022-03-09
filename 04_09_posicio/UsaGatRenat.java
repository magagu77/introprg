 // Programa que usa GatRenat en la version del ejercicio 04_09
 public class UsaGatRenat {
    public static void main(String[] args) {
        GatRenat renat = new GatRenat();
        System.out.println("Vides inicials: " + renat.vides);
        System.out.println("Posició inicial: " + renat.posicio);
        if(estirat(renat.posicio)){
            renat.posicio = "assegut";
        }
        System.out.println("Posició final: " + renat.posicio);
    }
    public static boolean estirat(String posicio) {
        if (posicio.equals("estirat")) return true;
        else return false;
    }
}