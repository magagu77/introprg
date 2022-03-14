// Ascensor que demana l'exercici 04_15
public class Ascensor {
    private int pis = -1;
    private String moviment = "aturat";

    public int getPis() {
        return pis;
    }
    public String getMoviment() {
        return moviment;
    }
    public void setPis(String newPis) {
        if (UtilString.esEnter(newPis)) {
            int nouPis = Integer.parseInt(newPis);
            if (nouPis <= 10 && nouPis >= -1) {
                pis = nouPis;
            }
        }    
    }
    public void setMoviment(String newMoviment) {
        if (newMoviment.equals("pujant") || newMoviment.equals("aturat") || newMoviment.equals("baixant")){
            moviment = newMoviment;
        }
    }
    public boolean estaAbaix() {
        if (pis == -1) return true;
        return false;
    }
    public boolean estaAmunt() {
        if (pis == 10) return true;
        return false;
    }
    public boolean estaAturat() {
        if (moviment.equals("aturat")) return true;
        return false;
    }
    public boolean estaPujant() {
        if (moviment.equals("pujant")) return true;
        return false;
    }
    public boolean estaBaixant() {
        if (moviment.equals("baixant")) return true;
        return false;
    }
    public String comEsta () {
        String estat = "";
        if (estaBaixant()) {
            estat = estat + "baixant";
        } else if (estaPujant()) {
            estat = estat + "pujant";
        } else if (estaAturat()) {
            estat = estat + "aturat";
        }
        estat = estat + " al pis " + getPis(); 
        return estat;
    }
}
