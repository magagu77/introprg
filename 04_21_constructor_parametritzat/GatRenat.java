/*  Gato que necesita que le añadas tu el valor de vidas y la posicion, y en el main te dice que vaor les has asignado*/ 
public class GatRenat {
    private int vides;
    private String posicio;
    public GatRenat(int novesVides, String novaPosicio) {
        vides = novesVides;
        posicio = novaPosicio;
    }
    @Override
    public String toString() {
        return String.format("Vides: %d. Posició: %s", vides, posicio);
    }
    public int getVides() { return vides; }

    public String getPosicio() {return posicio;}
    public static void main(String[] args) {
        System.out.println(new GatRenat(7, "estirat"));
    }
}