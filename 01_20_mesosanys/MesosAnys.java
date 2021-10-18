//Desenvolupa un programa que demani un mes i un any, i escrigui el mes anterior i el mes següent.

public class MesosAnys {
    public static void main(String[]args) {
        
        System.out.println("Mes?");
        int mes = Integer.parseInt(Entrada.readLine());
        System.out.println("Any?");
        int any = Integer.parseInt(Entrada.readLine());
        
        if (mes < 12  && mes > 1) {
            System.out.println("Anterior " + (mes - 1) + "/" + any + " i posterior " + (mes + 1) + "/" + any);
        }
          else if (mes == 12) {
              System.out.println("Anterior " + (mes - 1) + "/" + any + " i posterior 1/" + (any + 1));
          }
          else if (mes == 1) {
              System.out.println("Anterior 12/" + (any - 1) + " i posterior " + (mes + 1) + "/" + any);
          }   
    }
}
     
