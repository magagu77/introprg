//Recorregut pero con valor de inicio y final + saltos

public class EntersIniciFi {
    public static void main(String[] args) {
    
        int inici = 0;
        int valorFinal = 0;
        int salt = 0;
        int valorCalculat = 0;
        boolean resultat = false;
        while (resultat == false) {
            System.out.println("Valor inicial?");
            inici = Integer.parseInt(Entrada.readLine());
            System.out.println("Valor final?");
            valorFinal = Integer.parseInt(Entrada.readLine());
            System.out.println("Salt?");
            salt = Integer.parseInt(Entrada.readLine());
            if (valorCalculat == 0) {
                valorCalculat = inici;
                System.out.println(valorCalculat);
            }
            valorCalculat = valorCalculat + salt;
            System.out.println(valorCalculat);
            if (valorCalculat >= valorFinal) {
                resultat = true;
            }
        }
    }
}                   
