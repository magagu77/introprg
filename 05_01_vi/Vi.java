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
    private int estoc = 0; // Duele Moises, duele

    // Constructor sense tenir en compte stock
    public Vi(String nom, int preu) {
        this.preu = preu;
        this.nom = nom;
        estoc = 0;

    }
    // Constructor con stock
    public Vi(String nom, int preu, int estoc) {
        this.estoc = estoc;
        this.nom = nom;
        this.preu = preu;
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
            return "NOM NO VALID!";
        }
        nom = nom.trim();
        nom = String.format(nom.replaceAll("\\s"," "));
        return nom;
    }

    @Override
    public String toString() {
        String vino = String.format("\n    Vi: %s\n    Preu: %s\n    Estoc: %s\n", getNom(),getPreu(),getEstoc());
        return vino;
    }
}