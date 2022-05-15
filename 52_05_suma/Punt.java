/** Clase Punt per a probar que pasa tots els test de l'exercici 52_05 de tests */
public class Punt {
    private int y;
    private int x;

    // Constructor específic
    public Punt(int x, int y) {
        this.x = x;
        this.y = y;
    }
    // Constructor per defecte
    public Punt(){
        int y = 0;
        int x = 0;
    }
    // Getter Y
    public int getY(){
        return y;
    }
    // Getter X
    public int getX(){
        return x;
    }
    // Setter X
    public void setX(int x) {
        this.x=x;
    }
    // Setter Y
    public void setY(int y) {
        this.y=y;
    }
    // Suma dos puntos
    public void suma(Punt p) { x+=p.x; y+=p.y; }
}
