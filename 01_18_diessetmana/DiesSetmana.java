    //Desenvolupa un programa que demani per entrada estàndard un número enter entre el 1 i el 7 i respongui segons la taula://
    
public class DiesSetmana {

    public static void main(String[] args) {
        int diaSetmana = Integer.parseInt(args[0]);
        
        if (diaSetmana == 1){
            System.out.println("Dilluns");
         }
            else if (diaSetmana == 2){
                System.out.println("Dimarts");
            }
            else if (diaSetmana == 3){
               System.out.println("Dimecres"); 
            }
            else if (diaSetmana == 4){
               System.out.println("Dijous"); 
            }
            else if (diaSetmana == 5){
               System.out.println("Divendres"); 
            }
            else if (diaSetmana == 6){
                System.out.println("Disabte");
            }
            else if (diaSetmana == 7){
                System.out.println("Diumenge"); 
            }
              else {
                System.out.println("Error"); 
            }
            
    }
}     
