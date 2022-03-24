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