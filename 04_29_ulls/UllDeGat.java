import java.io.ObjectStreamClass;

/*  Clase ull de gat per a la clase GatRenat que permet saber 
    la informacio de com están els ulls del gat
*/
public class UllDeGat {
    private boolean posicio = false;
    //Constructor
    public UllDeGat() {
        posicio = false;
    }

    public UllDeGat(boolean posicio) {
        this.posicio = posicio;
    }

    public void setPosicio(boolean posicio) {
        this.posicio = posicio;
    }

    public boolean getPosicio() {return posicio;}

    public void obret() {
        setPosicio(true);
    }

    public void tancat() {
        setPosicio(false);
    }
    
    public boolean estaObert() {
        if(getPosicio() == true) {
            return true;
        } else {
            return false;
        }
    }
}
