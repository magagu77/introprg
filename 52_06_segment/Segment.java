/** Clase segment en la versió de l'exercici 52_06 que ha de pasar els seus tests + prgtest */
public class Segment {
    private Punt p1;
    private Punt p2;

    // Constructor de la classe
    public Segment(Punt p1, Punt p2) {
        this.p1 = p1;
        this.p2 = p2;
    }
    public Segment() {
        p1 = new Punt();
        p2 = new Punt();
    }
    // Getters
    public Punt getP1() {
        return p1;
    }
    public Punt getP2() {
        return p2;
    }
    // Setters
    public void setP1(Punt punt) {
        this.p1 = punt;
    }
    public void setP2(Punt punt) {
        this.p2 = punt;
    }
    // Calcula la longitud de un segmento 
    public double longitud() {
        int x1 = p1.getX();
        int x2 = p2.getX();
        int y1 = p1.getY();
        int y2 = p2.getY();
        double longitud = Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
        return longitud;
    } 
}
