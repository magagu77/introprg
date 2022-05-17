/** Programa UsaPunt per a provar la clase Punt de l'exercici 52_05 (codigo dado por Moises) */
 public class UsaPunt {
    public static void main(String[] args){
        int x1 = 0; 
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;
        if(args!=null){    
            for(int i=0;i<args.length;i++){
                if(i==0&&UtilString.esEnter(args[i])){
                    x1+=Integer.parseInt(args[i]);
                } else if(i==1&&UtilString.esEnter(args[i])){
                    y1+=Integer.parseInt(args[i]);
                } else if(i==2&&UtilString.esEnter(args[i])){
                    x2+=Integer.parseInt(args[i]);
                }else if(i==3&&UtilString.esEnter(args[i])){
                    y2+=Integer.parseInt(args[i]);
                }
            }
        }
        Punt p1 = new Punt(x1, y1);
        Punt p2 = new Punt(x2, y2);
        System.out.printf("p1: (%d, %d)%n", p1.getX(), p1.getY());
        System.out.printf("p2: (%d, %d)%n", p2.getX(), p2.getY());
        p1.suma(p2);
        System.out.printf("p1+p2: (%d, %d)%n", p1.getX(), p1.getY());
    }
}