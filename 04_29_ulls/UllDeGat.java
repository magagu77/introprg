import java.io.ObjectStreamClass;

/*  Clase ull de gat per a la clase GatRenat que permet saber 
    la informacio de com están els ulls del gat
*/
public class UllDeGat {
    private boolean posicio = true;
    
    public void obret() {
        posicio = true;
    }
    public void tancat() {
        posicio = false;
    }
    public boolean estaObert() {
        if(posicio == true) {
            return true;
        } else {
            return false;
        }
    }
}
