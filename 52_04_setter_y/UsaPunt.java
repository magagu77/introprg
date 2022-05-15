/** Programa UsaPunt per a provar la clase Punt de l'exercici 52_04 */
public class UsaPunt {
    public static void main(String[] args){
        Punt punt = new Punt();
        int x =0;
        int y=0;
        if (args[0]!=null&& UtilString.esEnter(args[0])){
            x = Integer.parseInt(args[0]);
        }
        if (args[1]!=null&&UtilString.esEnter(args[1])){
            y = Integer.parseInt(args[1]);
        }
        punt.setX(x);
        punt.setY(y);
        System.out.printf("punt.getX() -> %d%n", punt.getX());
        System.out.printf("punt.getY() -> %d%n", punt.getY());
    }
}