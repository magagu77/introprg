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
            System.out.println("\t"+categoria);
        }
    }
    public static void mostraAnimals(List<Animal> animals) {
        if (animals.isEmpty()) {
            System.out.println("Cap animal");
            return;
        }
        System.out.println("Nombre de animals: "+animals.size());
        for(Animal animal: animals) {
            System.out.println("\t"+animal);
        }
    }
}
