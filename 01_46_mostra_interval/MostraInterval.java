//Mostrar un intervalo de numeros en pantalla con for

public class MostraInterval {
    public static void main(String[] args) {
        
        System.out.println("inici?");
        int inici = Integer.parseInt(Entrada.readLine());
        System.out.println("final?");
        int fi = Integer.parseInt(Entrada.readLine());
        
        if (inici == fi) {
            System.out.println(inici);
        } else {    
            for (int interval = inici;
                 interval <= fi;
                 interval = interval + 1) {
                 System.out.println(interval);
            }
            for (int interval = inici;
                 interval >= fi;
                 interval = interval - 1) {
                 System.out.println(interval);
            }
        } 
    }
}             
