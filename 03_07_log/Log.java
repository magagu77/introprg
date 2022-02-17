/* Programa que crea un fichero de log */
public class Log{
    private static int nSeq = 1;
    // Log de erroe
    public static String printError(String mensaje){
        String linea = String.format("[%d] ERROR: %s",nSeq,mensaje);
        nSeq++;
        return linea;
    }
    // Log de warning
    public static String printWarning(String mensaje){
        String linea = String.format("[%d] WARNING: %s",nSeq,mensaje);
        nSeq++;
        return linea;
    }
    // Log de info
    public static String printInfo(String mensaje){
        String linea = String.format("[%d] INFO: %s",nSeq,mensaje);
        nSeq++;
        return linea;
    }
    // Log de debug
    public static String printDebug(String mensaje){
        String linea = String.format("[%d] DEBUG: %s",nSeq,mensaje);
        nSeq++;
        return linea;
    }
    public static void reset(){
        nSeq=1;
    }
}