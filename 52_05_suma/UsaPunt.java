	

 public class UsaPunt {
    public static void main(String[] args){
        int x1 = 1; 
        int y1 = 1;
        int x2 = 1;
        int y2 = 1;
        if(args[0]!=null){    
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