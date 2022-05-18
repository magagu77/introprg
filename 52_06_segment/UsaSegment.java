/** Programa UsaPunt per a provar la clase Punt de l'exercici 52_05 (codigo dado por Moises) */
public class UsaSegment {
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
        Punt punt1 = new Punt(x1, y1);
        Punt punt2 = new Punt(x2, y2);
        Segment segment1 = new Segment(punt1, punt2);
        Segment segment2 = new Segment();
        segment2.setP1(segment1.getP2());
        segment2.setP2(segment1.getP1());
        System.out.printf("p1: %s%n", punt1);
        System.out.printf("p2: %s%n", punt2);
        System.out.printf("s1: %s s1.longitud: %.02f%n", segment1, segment1.longitud());
        System.out.printf("s2: %s s2.longitud: %.02f%n", segment2, segment2.longitud());
    }
}