//El problema te pide el año en que estas, tu nombre y cuantos años tienes, y de ahí te dice cuantos años tenias en un año en concreto

public class HistoricEdats {
    public static void main(String[] args) {
        
        System.out.println("nom?");
        String nom = Entrada.readLine();
        System.out.println("edat?");
        int edat = Integer.parseInt(Entrada.readLine());
        System.out.println("any actual?");
        int anyActual = Integer.parseInt(Entrada.readLine());
        int anyEdat = 0;
        if (nom.isBlank()) {
            System.out.println("Entrada errònia");
        } else if (edat <= 0) {
            System.out.println("Adéu " + nom);
        } else {        
            for (int contadorEdat = 0;
                 contadorEdat <= edat;
                 contadorEdat = contadorEdat + 1) {
                 anyEdat = anyActual - edat + contadorEdat;
                 if (contadorEdat == 0) {   
                    System.out.println("El " + anyEdat + " va néixer");
                 } else if (contadorEdat == edat) {
                      System.out.println("Adéu " + nom);     
                 } else if (contadorEdat == 1) {
                      System.out.println("El " + anyEdat + " tenia " + contadorEdat + " any");
                 } else if (contadorEdat > 0) {
                      System.out.println("El " + anyEdat + " tenia " + contadorEdat + " anys");
                 } 
            }
        }           
    }             
}             
