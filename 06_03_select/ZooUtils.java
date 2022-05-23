import java.util.List;

/** Utilidades para Zoo y UsaZoo que estaran fuera de las otras clases */
import java.util.List;
import java.util.LinkedList;
public class ZooUtils {
    // Muestra las categorias por pantalla
    public static void mostraCategories(List<Categoria> categories) {
        if(categories.isEmpty()){
            System.out.println("Cap categoria");
            return;
        }
        System.out.println("Nombre de categories: "+categories.size());
        for(Categoria categoria: categories){
            System.out.println("    "+categoria);
        }
    }
}
