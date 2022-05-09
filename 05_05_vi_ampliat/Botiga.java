/**
 *  La clase botiga permet guardar un maxim de 10 vins en un array de Vi[], un metode afegeix
 *  permetra afegir un nou vi a l' array, comprova que el vi sigui valid i que no esta repetit, si esta repetit, si
 *  el vi introduit es corecte retorna el vi, en cas contrari retorna null
 * 
 *  El modul cerca() permet buscar un vi pel seu nom, y el retorna en cas que existeixi
 * 
 *  El modul elimina() un vi segons el nom que rebi, si no troba el vi retorna null, en cas que el vi existeixi y tingui stock
 *  retornara null i no eliminara el vi, en cas qeu no hi hagi stock eliminara el vi
 * 
 *  cerca() i elimina() normalitzen el nom
 */
public class Botiga {
    private int DEFAULT_MAX_VINS = 10;
    private Vi[] vins;
    private int posicioArray = -1;

    public Botiga() {
        vins = new Vi[DEFAULT_MAX_VINS];
    }
    // Constructor sense vins per defecte
    public Botiga(int maxVins) {
        vins = new Vi[maxVins];
    }

    // Modul que afegeix vins
    public Vi afegeix(Vi nouVi) {
        if (!viEnLlista(vins, nouVi.getRef(),nouVi.getNom())) {
            for(int i=0;i<vins.length;i++) {
                if(vins[i]==null) {
                    if(nouVi.esValid()) {
                        vins[i] = nouVi;
                        return vins[i];
                    }
                } else {
                    continue;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    // Elimina vi
    public Vi elimina(String ref) {
        ref = Vi.normalitzaString(ref);
        for(int i=0;i<vins.length;i++) {
            if (vins[i]==null){
                continue;
            } else if(vins[i].getRef().equalsIgnoreCase(ref) && vins[i].getEstoc() <= 0) {
                Vi viEliminat = vins[i];
                vins[i] = null;
                return viEliminat;
            }
        }
        return null;
    }
    // Cerca vi segons la referencia
    public Vi cerca(String ref) {
        ref = Vi.normalitzaString(ref);
        if(vins.length == 0) {return null;}
        for(int i=0;i<vins.length;i++) {
            if(vins[i] == null) {
                continue;
            } else if (vins[i].getRef()==null){
                continue;
            } else if (vins[i].getRef().toLowerCase().equals(ref.toLowerCase())) {
                return vins[i];
            }
        }
        return null;
    }
    public Vi cerca() {
        if(vins[0]!=null){return vins[0];}
        return null;
    }
    // Busca un vino comparando con una instancia de vino
    public Vi cerca (Vi viBuscat) {
        for(Vi vi: vins) {
            if(vi == null) {continue;}
            if(!(viBuscat.getRef()==null) && !(viBuscat.getRef().equalsIgnoreCase(vi.getRef()))){continue;}
            if(!(viBuscat.getNom()==null) && !(viBuscat.getNom().equalsIgnoreCase(vi.getNom()))){continue;}
            if((viBuscat.getPreu()!=-1) && (viBuscat.getPreu() < vi.getPreu())){continue;}
            if((viBuscat.getEstoc()!=-1) && (viBuscat.getEstoc() > vi.getEstoc())){continue;}
            if(!(viBuscat.getLloc() ==null) && !(viBuscat.getLloc().equalsIgnoreCase(vi.getLloc()))){continue;}
            if(!(viBuscat.getOrigen()==null) && !(viBuscat.getOrigen().equalsIgnoreCase(vi.getOrigen()))){continue;}
            if(!(viBuscat.getTipus()==null) && !(viBuscat.getTipus().equalsIgnoreCase(vi.getTipus()))){continue;}
            if(!(viBuscat.getCollita()==null) && !(viBuscat.getCollita().equalsIgnoreCase(vi.getCollita()))){continue;}
            return vi;
        }
        return null;
    }
    // Cerca vi comparant amb una instancia de Vip 
    public Vi modificaVi(String nom, int preu, int estoc) {
        nom = Vi.normalitzaString(nom);
        for(int i=0;i<vins.length;i++) {
            if(vins[i] == null) {
                continue;
            } else if (vins[i].getNom().toLowerCase().equals(nom.toLowerCase())) {
                vins[i].setEstoc(estoc);
                vins[i].setPreu(preu);
                return vins[i];
            }
        }
        return null;
    }  

    // Comprova si el vi esta en la llista
    private boolean viEnLlista (Vi[] vins, String ref, String nom) {
        for(int i=0;i<vins.length;i++) {
            if(vins[i] == null) {
                continue;
            } else if (vins[i].getRef().equals(ref)) {
                return true;
            } else if (vins[i].getNom().equals(nom)){
                return true;    
            }
        }
        return false;
    }
    public void iniciaRecorregut() {
        posicioArray = -1;
    }
    public Vi getSeguent() {
        while (true) {
            posicioArray = posicioArray + 1;
            if(posicioArray == vins.length) {return null;}
            if (vins[posicioArray-1]==null) {continue;} 
            else {
            return vins[posicioArray-1];
            }
        }
    }
}