/*Programa para multiplicar un numero x 2 
EL programa no controla valores no adecuados de entrada (LETRAS )*/

class Duplica {
    public static void main(String[] args) {
    
    //Declarar variables a utilizar
    int nombreADuplicar;
    int nombreDuplicat;
    
    //Multplicar número
    
    nombreADuplicar = Integer.parseInt(args[0]);
    
    //Calcula el doble
    nombreDuplicat = nombreADuplicar * 2 ;
    System.out.println ("El doble de " + nombreADuplicar);
    System.out.println ("és " + nombreDuplicat);
    }
}

