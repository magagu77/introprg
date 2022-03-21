/* Ascensor que añade 4 modulos nuevos que pide el ejercicio 04_18, que devuelven un valor booleano segun si esta parado,
 subiendo o bajando, o subir al ascensor al siguiente piso(en caso de llegear al piso 10 bajara al piso 9) */ 
public class Ascensor {
    private int pis = -1;
    private String moviment = "aturat";

    public int getPis() {
        return pis;
    }
    public String getMoviment() {
        return moviment;
    }
    public void setPis(int nouPis) {
            if (nouPis <= 10 && nouPis >= -1) {
                pis = nouPis;
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
    public boolean estaMovent() {
        if (estaAturat()) return false;
        else return true;
    }
    public boolean aturat() {
        if (estaAturat()) return false;
        else {
            setMoviment("aturat");
            return true;
        }
    }
    public boolean arrencaAbaix(){
        if (!getMoviment().equals("baixant")) {
            if(estaAturat())setMoviment("baixant");
            return false;
        }
        else {
            return true;
        }
    }
    public boolean arrencaAmunt() {
        if (getMoviment().equals("pujant")) return false;
        else {  
            if(estaAturat())setMoviment("pujant");
            return true;
        }
    }
    public int seguentPis() {
        if (estaPujant()) {
            if(getPis() == 10) {
                setMoviment("baixant");
                return (getPis()-1);
            } else return (getPis()+1);
        } else if (estaBaixant()) {
            if (getPis() == -1) {
                setMoviment("pujant");
                return (getPis()+1);
            } else return (getPis()-1); 
        } else return getPis();
    }
}
