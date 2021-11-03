//Primers parells del 1 al 10 pero amb for

public class PrimersParells {
    public static void main(String[] args) {
    
        for (int i = 1;
             i <= 10;
             i = i + 1) {
             if (i % 2 == 0) {
             System.out.println(i);
             }
        }
    }
}                            
