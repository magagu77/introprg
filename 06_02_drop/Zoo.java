/** Clase zoo en l'exercici 06_02 que prova a conectarse a una base de dades SQLite per a guardar 
 *  la taula categories, o eliminarla*/
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
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
        zoo.eliminaTaulaCategories();
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
}
