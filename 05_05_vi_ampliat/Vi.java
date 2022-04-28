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

    private String ref = "";
    private String nom = "";
    private int preu = -1;
    private int estoc = -1; // Duele Moises, duele
    private String lloc ="";
    private String origen = "";
    private String tipus = "";
    private String collita ="";


    // Constructor que tiene en cuenta todo
    public Vi(String ref,String nom, int preu,int estoc, String lloc, String origen, String tipus, String collita) {
        this.ref = normalitzaString(ref); 
        this.nom = normalitzaString(nom);
        setPreu(preu);
        setEstoc(estoc);
        this.lloc = normalitzaString(lloc);
        this.origen = normalitzaString(origen);
        this.tipus = normalitzaString(tipus);
        this.collita = normalitzaString(collita);
    }

    // Getters
    public String getRef() {return ref;}

    public String getNom() {return nom;}

    public int getPreu() {return preu;}

    public int getEstoc() {return estoc;}

    public String getLloc() {return lloc;}

    public String getOrigen() {return origen;}

    public String getTipus() {return tipus;}

    public  String getCollita() {return collita;}

    // Setters
    public void setNom(String nom) {
        this.nom = normalitzaString(nom);
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
    public void setLloc(String nouLloc) {
        this.lloc = normalitzaString(nouLloc);
    }
    // Normaliza String del nombre
    public static String normalitzaString(String nom) {
        if (nom.isBlank()) {
            return null;
        }
        nom = nom.trim();
        nom = String.format(nom.replaceAll(" +"," "));
        return nom;
    }
    // Determina si el preu d'un vi es valid o no
    public boolean esValid() {
        if (preu < 0) {
            return false;
        } else if (nom.isBlank()||collita.isBlank()||lloc.isBlank()||ref.isBlank()||tipus.isBlank()||origen.isBlank()){
            return false;
        } else if (nom == null) {
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
        if(vins.length != 8){return null;}
        if (vins[0].isBlank()|| vins[0]==null ||
            vins[3].isBlank()|| vins[3]==null ||
            vins[4].isBlank()|| vins[4]==null ||
            vins[5].isBlank()|| vins[5]==null ||
            vins[6].isBlank()|| vins[6]==null ||
            vins[7].isBlank()|| vins[7]==null ){return null;}
        if(!UtilString.esEnter(vins[1])|| vins[1]==null){return null;}
        if(!UtilString.esEnter(vins[2])||vins[2]==null){return null;}
        Vi nouVi = new Vi(normalitzaString(vins[0]),normalitzaString(vins[1]),Integer.parseInt(vins[2]),Integer.parseInt(vins[3]),normalitzaString(vins[4]),normalitzaString(vins[5]),normalitzaString(vins[6]),normalitzaString(vins[7]));
        if (nouVi.esValid()) {return nouVi;}
        else {return null;}
    }
    // Convierte las instancias de un vino en un array de Strings 
    public String[] aArrayString() {
        String[] viString ={getRef(),getNom(),Integer.toString(getPreu()),Integer.toString(getEstoc()),getLloc(),getOrigen(),getTipus(),getCollita()};
        return viString;
        
    }

    @Override
    public String toString() {
        String vino = String.format("\n    Ref: %s\n    Vi: %s\n    Preu: %s\n    Estoc: %s\n    Lloc: %s\n    Origen: %s\n    Tipus: %s\n",getRef(), getNom(),getPreu(),getEstoc(),getLloc(),getOrigen(),getTipus(),getCollita());
        return vino;
    }
}