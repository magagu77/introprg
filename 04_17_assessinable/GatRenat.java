/*Clase GatRenat en el ejercicio 04_17 que pide añadir 
3 modulos String que quitan una vida al gato, le devuelven 
una vida si está muerto y otro que le devuelve un numero de 
vidas a escoger por el usuario*/
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
    public String aixecat() {
        if(estaDret()){
            return "no faig res";
        } else {
            setPosicio("dret");;
            return "m'aixeco";
        }
    } 
    public String seu() {
        if(estaAssegut()){
            return "no faig res";
        } else {
            setPosicio("assegut");
            return "m'assec";
        } 
    }
    public String estirat() {
        if(estaEstirat()){
            return "no faig res";
        } else {
            setPosicio("estirat");
            return "m'estiro";
        }
    }
    public String mor() {
        if(vides > 0) {
            vides--;
            if (vides > 0) return "auch";
            else return "ximpún";
        } else if (vides==0){
            vides = -1;
            return "...";
        } 
        else return resuscita();
    }
    public String resuscita() {
        if(!estaViu()) {
            setVides(1);
            return "guai!";
        }
        return "...";
        
    }
    public String resuscita(int vides) {
        if(!estaViu()) {
            setVides(vides);
            return "guai!";
        }
        return "...";
    }
}