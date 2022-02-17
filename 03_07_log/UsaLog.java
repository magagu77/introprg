/* Programa UsaLog para probar el Log.java */	
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
public class UsaLog {
    public static void main(String[] args) throws IOException {
        String fitxerLog ="log.txt";
        BufferedWriter log = new BufferedWriter(new FileWriter(fitxerLog, true));
        log.write(Log.printError("Això és un error greu"));
        log.write(Log.printWarning("Això és un avís"));
        for (int i=0; i < args.length; i++) {
           Log.printInfo(String.format("Argument %d: %s", i, args[i]));
        }
        Log.reset();     // comencem a comptar un altre cop
        log.write(Log.printDebug("Aquí s'acaba el main()"));
    }
}