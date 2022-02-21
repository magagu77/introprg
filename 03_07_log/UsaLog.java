/* Programa UsaLog para probar el Log.java, demostración de uso del profe */	
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
public class UsaLog {
    public static void main(String[] args) throws IOException {
        String fitxerLog ="log.txt";
        BufferedWriter log = new BufferedWriter(new FileWriter(fitxerLog, true));
        log.write(Log.printError("Això és un error greu"));
        log.newLine();
        log.write(Log.printWarning("Això és un avís"));
        log.newLine();
        for (int i=0; i < args.length; i++) {
            log.write(Log.printInfo(String.format("Argument %d: %s", i, args[i])));
            log.newLine();
        }
        Log.reset();     // comencem a comptar un altre cop
        log.write(Log.printDebug("Aquí s'acaba el main()"));
        log.newLine();
        log.close();
    }
}