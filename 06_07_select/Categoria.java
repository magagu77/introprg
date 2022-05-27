/** Clase categoria que s'utilitza per a asignara una categoria a un animal del zoo */
public class Categoria {
    private int id = -1;  // -1 indica no assignat/indefinit
    private String nom;

    public Categoria(String nom) {
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("El nom no pot ser null ni blanc");
        }
        this.nom = nom;
    }

    public Categoria(int id, String nom) {
        if (id < 0) {
            throw new IllegalArgumentException("L'identificador ha de ser positiu");
        }
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("El nom no pot ser null ni blanc");
        }
        this.id = id;
        this.nom = nom;
    }

    public boolean idIndefinit() { return id < 0; }
    public int getId() {
        if (idIndefinit()) {
            throw new UnsupportedOperationException("L'identificador no està disponible");
        }
        return id;
    }
    public String getNom() { return nom; }

    @Override
    public String toString() {
        return "Categoria(id:" +
            (id < 0 ? "indefinit" : id) +
            ", " + nom + ")";
    }
    public void setId(int id) {
        if(id < 0) {
            throw new IllegalArgumentException("L'identificador ha de ser positiu");
        }
        this.id = id; 
    }   
}