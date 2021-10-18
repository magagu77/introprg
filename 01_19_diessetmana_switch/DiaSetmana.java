//Ejercicio 01_18 pero con un switch

public class DiaSetmana {
    public static void main(String[] args){
    
        int diaSetmana = Integer.parseInt(args[0]);
        String diaSetmanaString;
        switch (diaSetmana) {
            case 1: diaSetmanaString = "Dilluns";
                    break;
            case 2: diaSetmanaString = "Dimarts";
                    break;
            case 3: diaSetmanaString = "Dimecres";
                    break;
            case 4: diaSetmanaString = "Dijous";
                    break;
            case 5: diaSetmanaString = "Divendres";
                    break;
            case 6: diaSetmanaString = "Dissabtes";
                    break;
            case 7: diaSetmanaString = "Diumenge";
                    break;
            default: diaSetmanaString = "Error";
                     break;
            }
            System.out.println(diaSetmanaString);
        }
}
