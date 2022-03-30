import java.io.ObjectStreamClass;

/*  Clase ull de gat per a la clase GatRenat que permet saber 
    la informacio de com están els ulls del gat
*/
public class UllDeGat {
    private String posicio = "obert";
    
    public void obret() {
        posicio = "obert";
    }
    public void tancat() {
        posicio = "obert";
    }
    public boolean estaObert() {
        if(posicio.equals("obert")) {
            return true;
        } else {
            return false;
        }
    }
}
