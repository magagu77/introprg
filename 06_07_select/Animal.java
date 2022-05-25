/** Clase animal del zoo formada per un id, una Categoria i un nom */
public class Animal {
    private int id =-1;
    private Categoria categoria;
    private String nom;

    // Constructor d'animal
    public Animal(String nom, Categoria categoria) {
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("El nom no pot ser null ni blanc");
        }
        if (categoria == null) {
            throw new IllegalArgumentException("La categoria no pot ser null");
        }
        this.nom = nom;
        this.categoria = categoria;
    }
    // Constructor d'animal amb id
    public Animal(int id, String nom, Categoria categoria) {
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("El nom no pot ser null ni blanc");
        }
        if (categoria == null) {
            throw new IllegalArgumentException("La categoria no pot ser null");
        }
        if (id < 0) {
            throw new IllegalArgumentException("L'identificador ha de ser positiu");
        }
        this.nom = nom;
        this.categoria = categoria;
        this.id = id;
    }
    // Getters
    public int getId() {return id;}

    public Categoria getCategoria() {return categoria;}

    public String getNom() {return nom;}

    // Setters
    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("L'identificador ha de ser positiu");
        }
        this.id = id;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    @Override
    public String toString() {
        return "Animal(id:" +
            (id < 0 ? "indefinit" : id) +
            ", " + nom + ", "+categoria+")";
    }
}