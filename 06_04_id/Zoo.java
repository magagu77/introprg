/** Clase zoo en l'exercici 06_02 que prova a conectarse a una base de dades SQLite per a guardar 
 *  la taula categories, o eliminarla*/
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.LinkedList;
import java.sql.ResultSet;
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
    public void eliminaTaulaCategories() throws SQLException {
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
        // Clase per a crear una categoria a la BDD
        public void afegeixCategoria(Categoria categoria) throws SQLException {
            String sql = String.format(
                    "INSERT INTO CATEGORIES (nom) VALUES ('%s')",
                    categoria.getId(),categoria.getNom());
            Statement st = null;
            List<Categoria> categories = recuperaCategories();
            int id = categories.size()+1;
            categoria.setId(id);
            try {
                st = conn.createStatement();
                st.executeUpdate(sql);
            } finally {
                if (st != null) {
                    st.close();
                }
            }
        }
}
