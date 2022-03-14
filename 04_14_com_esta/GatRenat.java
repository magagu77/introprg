// Clase GatRenat
public class GatRenat {
    private int vides = 7;
    private String posicio = "estirat";
    // Da el valor de las vidas 
    public int getVides() {
        return vides;
    }
    public void setVides(int valor) {
        vides = valor;
    }
    public String getPosicio() {
        return posicio;
    }
    public void setPosicio (String novaPosicio) {
        if (posicioCorrecta(novaPosicio)) {
            posicio = novaPosicio; 
        }
    }
    public static boolean posicioCorrecta (String posicio) {
        if(posicio.equals("dret") || posicio.equals("assegut") 
        || posicio.equals("estirat")) return true;
        return false;
    }
    public boolean estaViu () {
        if (vides <= 0) return false;
        else return true;
    }
    public boolean estaAssegut() {
        if (posicio.equals("assegut")) return true;
        else return false;
    }
    public boolean estaDret() {
        if (posicio.equals("dret")) return true;
        else return false;
    }
    public boolean estaEstirat() {
        if (posicio.equals("estirat")) return true;
        else return false;
    }
}