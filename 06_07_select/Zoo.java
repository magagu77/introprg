/** Clase zoo en l'exercici 06_02 que prova a conectarse a una base de dades SQLite per a guardar 
 *  la taula categories, o eliminarla*/
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.LinkedList;
import java.sql.ResultSet;
import java.util.ArrayList;
public class Zoo {
    private static final String NOM_BASE_DE_DADES = "animals.bd";
    private static final String CADENA_DE_CONNEXIO = "jdbc:sqlite:" +
                                                     NOM_BASE_DE_DADES;
    private Connection conn = null;

    public void connecta() throws SQLException {
        if (conn != null) return;   // ja connectat
        conn = DriverManager.getConnection(CADENA_DE_CONNEXIO);
    }

    public void desconnecta() throws SQLException {
        if (conn == null) return; // ja desconnectat
        conn.close();
        conn = null;
    }
    public void creaTaulaCategories() throws SQLException {
        eliminaTaulaCategories();
        String sql = "CREATE TABLE  CATEGORIES (" +
                     "       id        INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "       nom       VARCHAR(40))";
        Statement st = null;
        try {
            st = conn.createStatement();
            st.executeUpdate(sql);
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }
    // Crea la taula animals
    public void creaTaulaAnimals() throws SQLException {
        eliminaTaulaAnimals();
        eliminaTaulaCategories();
        creaTaulaCategories();
        String sql = "CREATE TABLE ANIMALS ("+
            "id        INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "nom       TEXT,"+
            "categoria INTEGER,"+
            "FOREIGN KEY(categoria) REFERENCES CATEGORIES(id))";
            Statement st = null;
            try {
                st = conn.createStatement();
                st.executeUpdate(sql);
            } finally {
                if (st != null) {
                    st.close();
                }
            }
    }
    // Elimina la taula categoria
    public void eliminaTaulaCategories() throws SQLException {
        eliminaTaulaAnimals();
        String sql = "DROP TABLE IF EXISTS CATEGORIES";
        Statement st = null;
        try {
            st = conn.createStatement();
            st.executeUpdate(sql);
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }
    // ELimina la taula animals
    public void eliminaTaulaAnimals() throws SQLException {
        String sql = "DROP TABLE IF EXISTS ANIMALS";
        Statement st = null;
        try {
            st = conn.createStatement();
            st.executeUpdate(sql);
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }
    // Crea una lista amb les categories
    public List<Categoria> recuperaCategories() throws SQLException {
        String sql = "SELECT * FROM CATEGORIES ORDER BY nom";
        Statement st = null;
        try {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            List<Categoria> categories = new LinkedList<>();
            while (rs.next()) {
                int bdId = rs.getInt("id");
                String nom = rs.getString("nom");
                Categoria categoria = new Categoria(bdId, nom);
                categories.add(categoria);
            }
            rs.close();
            return categories;
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }
    // Crea una lista amb els animals guardats
    public List<Animal> recuperaAnimals() throws SQLException {
        String sql = "SELECT ANIMALS.id as id_animal, "+
        "ANIMALS.nom as nom_animal, "+
        "CATEGORIES.id as id_categoria, "+
        "CATEGORIES.nom as nom_categoria "+
        "FROM ANIMALS, CATEGORIES "+
        "WHERE ANIMALS.categoria = CATEGORIES.id "+
        "ORDER BY ANIMALS.nom";
        Statement st = null;
        try {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            List<Animal> animals = new LinkedList<>();
            while (rs.next()) {
                int categoriaId = rs.getInt("id_categoria");
                String categoriaNom = rs.getString("nom_categoria");
                String animalNom = rs.getString("nom_animal");
                int animalId = rs.getInt("id_animal");
                Categoria categoria = new Categoria(categoriaId,categoriaNom);
                Animal animal = new Animal(animalId, animalNom,categoria);
                animals.add(animal);
            }
            rs.close();
            return animals;
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }
    // Busca una categoria per nom
    public Categoria obteCategoriaPerNom(String nom) throws SQLException {
        List<Categoria> categories = recuperaCategories();
        for(Categoria categoria: categories){
            if(categoria.getNom().equals(nom)){
                return categoria;
            }
        }
        return null;
    }
    // Busca un animal per nom
    public Animal obteAnimalPerNom(String nom) throws SQLException {
        List<Animal> animals = recuperaAnimals();
        for(Animal animal: animals) {
            if(animal.getNom().equals(nom)) {
                return animal;
            }
        }
        return null;
    }
    // Clase per a crear una categoria a la BDD
    public void afegeixCategoria(Categoria categoria) throws SQLException {
        String sql = String.format(
                "INSERT INTO CATEGORIES (nom) VALUES ('%s')",
                categoria.getNom());
        Statement st = null;
        try {
            st = conn.createStatement();
            st.executeUpdate(sql);
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            categoria.setId(id);
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }
    // Clase per a crear una animal a la BDD
    public void afegeixAnimal(Animal animal) throws SQLException {
        Categoria categoria = obteCategoriaPerNom(animal.getCategoria().getNom());
        if (categoria==null) {
            afegeixCategoria(animal.getCategoria());
            categoria =animal.getCategoria();
        }
        animal.setCategoria(categoria);
        String sql = String.format(
        "INSERT INTO ANIMALS (nom, categoria) VALUES ('%s', '%d')",
        animal.getNom(),
        animal.getCategoria().getId());
        Statement st = null;
        try {
            st = conn.createStatement();
            st.executeUpdate(sql);
            ResultSet rs = st.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            animal.setId(id);
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }
    /* retorna el nom de les taules definides a la bd */
    public String getNomTaules() throws SQLException {
        String sql = "SELECT name FROM sqlite_schema " +
                    "WHERE name NOT LIKE 'sqlite%' " +
                    "ORDER BY name";
        List<String> taules = new ArrayList<>();
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) { taules.add(rs.getString("name")); }
            rs.close();
        }
        return taules.size() > 0 ? String.join(", ", taules) : "cap";
    }
}