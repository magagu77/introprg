/** Clase Punt per a probar que pasa tots els test de l'exercici 52_07 de tests */
public class Punt {
    private int[] coord;

    // Constructor específic
    public Punt(int n1, int n2) {
        this.coord = new int[2];
        this.coord[0]=n1;
        this.coord[1]=n2;
    }
    // Constructor per defecte
    public Punt(){
        this.coord = new int[] {0,0};
        
    }
    // Getter Y
    public int getY(){
        return coord[1];
    }
    // Getter X
    public int getX(){
        return coord[0];
    }
    // Setter X
    public void setX(int x) {
        this.coord[0]=x;
    }
    // Setter Y
    public void setY(int y) {
        this.coord[1]=y;
    }
    // Suma dos puntos
    public void suma(Punt p) { coord[0]+=p.getX(); coord[1]+=p.getY(); }

    // ToString
    @Override
    public String toString() {
        String punt = String.format("Punt(%s, %S)",getX(),getY());
        return punt;
    }
}
