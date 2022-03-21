/* Programa que usa un bucle para matar al gato y luego
 revivirlo con una vida para luego volver a matarlo 
 para revivirlo una segunda vez con 2 vidas y
 matarlo de nuevo (se lo merecia el muy puto)*/
public class UsaGatRenat {
    public static void main(String[] args) {
        GatRenat renat = new GatRenat();
        for (int vides = 1; vides <= 3; vides++) {
            while (renat.estaViu()) {
                System.out.println("Renat diu: " + renat.mor());
            }
            System.out.println("Renat diu: " + renat.mor());    // per rematar-ho!
            System.out.println("Renat diu: " + renat.resuscita(vides));
        }
    }
}
