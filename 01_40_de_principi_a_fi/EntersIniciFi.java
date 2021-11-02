//Recorregut pero con valor de inicio y final + saltos

public class EntersIniciFi {
    public static void main(String[] args) {
    
        int inici = 0;
        int valorFinal = 0;
        int salt = 0;
        int valorCalculat = 0 ;
        while (valorCalculat <= valorFinal) {
            if (inici == 0 ) {
            System.out.println("Valor inicial?");
            inici = Integer.parseInt(Entrada.readLine());
            System.out.println("Valor final?");
            valorFinal = Integer.parseInt(Entrada.readLine());
            System.out.println("Salt?");
            salt = Integer.parseInt(Entrada.readLine());
            }
            if ((valorCalculat + salt) != valorFinal) {
                System.out.println(valorCalculat);
                valorCalculat = valorCalculat + salt;
            }    

            
        }
    }
}                   
