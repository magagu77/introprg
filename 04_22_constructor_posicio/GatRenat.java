/* Ejercicio que comprueba que valor asigna en la posicio del gat renat y en caso de que no sea correcta le dice que esta estirado*/
public class GatRenat {
    private int vides;
    private String posicio;
    public GatRenat(int novesVides, String novaPosicio) {
        vides = novesVides;
        setPosicio(novaPosicio);
    }
    @Override
    public String toString() {
        return String.format("Vides: %d. Posició: %s", vides, posicio);
    }
    public int getVides() { return vides; }

    public String getPosicio() {return posicio;}

    public void setPosicio (String novaPosicio) {
        if (posicioCorrecta(novaPosicio)) {
            posicio = novaPosicio; 
        }
        else {
            posicio = "estirat";
        }
    }
    //Comprova posicio correcta
    public static boolean posicioCorrecta (String posicio) {
        if(posicio.equals("dret") || posicio.equals("assegut") 
        || posicio.equals("estirat")) return true; 
        else return false;
    }

    public static void main(String[] args) {
        System.out.println(new GatRenat(7, "dret"));
    }
}