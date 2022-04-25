/**
 * Clase vi que requereix un nom String, que no estigui en blanc i que al guardar el nom no es guardi amb cap espai 
 * per davant o per darrere fet per un modul normalitzaNom()
 * retornant NOM NO VALID! des d' un modul esValid() replaceAll()
 * 
 * preu, en centims i sense iva, no pot ser negatiu get preu&set preu, es determina si es valid pel petode es valid
 * 
 * Stock com a minim 0, mai menys, En cas que el constructor rebi un estoc negatiu, deixarà com a valor -1, el que 
 * farà que la instància sigui considerada com no vàlida pel mètode esValid().
 */
public class Vi {

    private String nom = "";
    private int preu = -1;
    private int estoc = -1; // Duele Moises, duele

    // Constructor sense tenir en compte stock
    public Vi(String nom, int preu) {
        setPreu(preu);
        this.nom = normalitzaNom(nom);
        estoc = 0;

    }
    // Constructor con stock
    public Vi(String nom, int preu, int estoc) {
        this.nom = normalitzaNom(nom);
        setPreu(preu);
        setEstoc(estoc);
    }

    // Getters
    public String getNom() {return nom;}

    public int getPreu() {return preu;}

    public int getEstoc() {return estoc;}


    // Setters
    public void setNom(String nom) {
        if(!nom.isBlank()){
            this.nom = nom;
        }
    }
    public void setPreu(int preu) {
        if (preu >= 0) {
            this.preu = preu;
        }
    }
    public void setEstoc(int stock) {
        if(stock >= 0) {
            this.estoc = stock;
        }
    }
    // Normaliza String del nombre
    public static String normalitzaNom(String nom) {
        if (nom.isBlank()) {
            return "NOM NO VÀLID!";
        }
        nom = nom.trim();
        nom = String.format(nom.replaceAll(" +"," "));
        return nom;
    }
    // Determina si el preu d'un vi es valid o no
    public boolean esValid() {
        if (preu < 0) {
            return false;
        } else if (nom.isBlank()){
            return false;
        } else if (nom.equals("NOM NO VÀLID!")) {
            return false;
        } else if (estoc < 0){
            return false;
        } else {
            return true;
        }
    }
    // Funcion que recibe un array de Strings y los convierte en un vino

    /** PENDIENTE ARREGLAR */
    public static Vi deArrayString(String[] vins) {
        if(vins[0].isBlank()|| vins[0]==null){return null;}
        if(!UtilString.esEnter(vins[1])|| vins[1]==null){return null;}
        if(!UtilString.esEnter(vins[2])||vins[2]==null){return null;}
        Vi nouVi = new Vi(normalitzaNom(vins[0]),Integer.parseInt(vins[1]),Integer.parseInt(vins[2]));
        if (nouVi.esValid()) {return nouVi;}
        else {return null;}
    }
    // Convierte las instancias de un vino en un array de Strings 
    public String[] aArrayString() {
        String[] viString ={ getNom(),Integer.toString(getPreu()),Integer.toString(getEstoc())};
        return viString;
        
    }

    @Override
    public String toString() {
        String vino = String.format("\n    Vi: %s\n    Preu: %s\n    Estoc: %s\n", getNom(),getPreu(),getEstoc());
        return vino;
    }
}