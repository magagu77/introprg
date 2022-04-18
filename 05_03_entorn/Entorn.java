/**
 * La class entorn es el programa amb el cual s'interactuara, el 
 * prompt (el nombre de la izquierda de la consola), unicamente aceptara las siguientes ordenes
 *      
 *      surt: acaba el programa con el siguiente texto "adéu"
 * 
 *      ajuda: ofrece los informacion de los comandos disponibles
 * 
 *      afegeix: permite añadir un nuevo vino, el programa pide el nombre, precio(en centimos)
 *               y stock. Si el valor introducido esta en blanco, el valor es igual a 0.
 *               El valor del precio y del stock tiene que ser positivo, de lo contrario se mostrara
 *               el siguiente mensaje "ERROR: el valor ha de ser un enter positiu" y se cancelara la operacion
 *               Si el vino no de añade se mostrara el siguiente mensaje: "ERROR: no s'ha pogut afegir"
 *      
 *      cerca: permite buscar un vino por su nombre y se muestran sus datos
 * 
 *      modifica: permite modificar un vino, previamente buscado por su nombre,pedirá por orden
 *                si se quiere modificar el nombre, precio o stock (no se aceptan valores 
 *                negativos, en tal caso cancela la operacion)
 * 
 *      elimina: elimina un vino previamente buscado por su nombre, muestra el vino y pide confirmación,
 *               en caso de que el vino no se puedea eliminar muestra el siguiente mensaje "ERROR: no s'ha pogut eliminar"
 *               en caso de que el vino se elimine muestar el mensaje "Eliminat"  
 * 
 * Si no se introduce ninguna de los comandos validos muestra el siguiente mensaje "ERROR: comanda no reconeguda. Escriviu help per ajuda"
 * Al iniciar el programa muestra el siguiente mensaje "Celler La Bona Estrella. Escriviu ajuda per veure opcions."      
 */
public class Entorn {
    private final Botiga botiga = new Botiga();
    public static void main(String[] args) {
        Entorn entorn = new Entorn();
        System.out.println("Celler La Bona Estrella. Escriviu ajuda per veure opcions.");
        while(true) {
            System.out.print("botiga> ");
            String entrada = Entrada.readLine();
            if(entrada.isBlank()){continue;}
            if(entrada.equals("surt")){break;}
            switch(entrada) {
                case "ajuda" : mostraAjuda();
                            break;
                case "afegeix" : entorn.procesaAfegeix();
                            break;
                case "cerca" : entorn.procesaCerca();
                            break;
                case "modifica" : entorn.procesaModifica();
                            break;
                case "elimina" : entorn.procesaElimina();
                            break;
                default: mostraErrorComandaDesconeguda();
            }
        }
        System.out.println("adéu");
    }

    // Mostra ajuda
    public static void mostraAjuda() {
        System.out.println("Comandes disponibles:\najuda\ncerca\nafegeix\nmodifica\nelimina\nsurt");
    }
    // Afegeix vi
    public void procesaAfegeix() {
        String entrada = "";
        System.out.print("nom (enter cancel·la)> ");
        entrada = Entrada.readLine();
        System.out.println();
        System.out.print("preu (en cèntims)> ");
        String preu = Entrada.readLine();
        System.out.println();
        if (!UtilString.esEnter(preu)){
            System.out.println("ERROR: el valor ha de ser un enter positiu");
            return;
        } else if (Integer.parseInt(preu)<0) {
            System.out.println("ERROR: el valor ha de ser un enter positiu");
            return;
        } else if (preu.isBlank()) {
            preu = "0";
        }
        System.out.print("estoc (enter sense estoc)> ");
        String stock = Entrada.readLine();
        System.out.println();
        if (!UtilString.esEnter(stock)) {
            System.out.println("ERROR: el valor ha de ser un enter positiu");
            return;
        } else if (Integer.parseInt(stock)<0) {
            System.out.println("ERROR: el valor ha de ser un enter positiu");
            return;
        } else if (stock.isBlank()) {
            stock = "0";
        }
        Vi nouVi = new Vi(entrada,Integer.parseInt(preu),Integer.parseInt(stock));
        if (botiga.afegeix(nouVi)==null) {
            System.out.println("ERROR: no s'ha pogut afegi");
        } else {
            botiga.afegeix(nouVi);
            System.out.println("Intoduït:\n"+nouVi);
        }
    }
    // Funcio cerca vi
    public void procesaCerca() {
        String entrada = "";
        System.out.println("nom (enter cancel·la)> ");
        entrada = Entrada.readLine();
        if(!entrada.isBlank()) {
            Vi cercat = botiga.cerca(entrada);
            if (cercat == null) {
                System.out.println("No trobat");
            } else {
                System.out.println(cercat);
            }
        }
    }
    // Permite modificar vinos
    public void procesaModifica() {
        String entrada = "";
        System.out.print("nom (enter cancel·la)> ");
        entrada = Entrada.readLine();
        if(entrada.isBlank()) {return;}
        Vi cercat = botiga.cerca(entrada);
        if(cercat==null) {
            System.out.println("No trobat");
            return;
        }
        System.out.println();
        System.out.printf("preu (enter %s)> ",cercat.getPreu());
        String preu = Entrada.readLine();
        System.out.println();
        if (!UtilString.esEnter(preu)){
            System.out.println("ERROR: el valor ha de ser un enter positiu");
            return;
        } else if (Integer.parseInt(preu)<0) {
            System.out.println("ERROR: el valor ha de ser un enter positiu");
            return;
        } else if (preu.isBlank()) {
            preu = Integer.toString(cercat.getPreu());
        }
        System.out.print("estoc (enter sense estoc)> ");
        String stock = Entrada.readLine();
        System.out.println();
        if (!UtilString.esEnter(stock)) {
            System.out.println("ERROR: el valor ha de ser un enter positiu");
            return;
        } else if (Integer.parseInt(stock)<0) {
            System.out.println("ERROR: el valor ha de ser un enter positiu");
            return;
        } else if (stock.isBlank()) {
            stock = Integer.toString(cercat.getEstoc());
        }
        int precio = Integer.parseInt(preu);
        int estoc = Integer.parseInt(stock);
        Vi modificat = botiga.modificaVi(entrada, precio, estoc);
        System.out.println("Modificat:\n"+botiga.cerca(entrada));
    }
    // ELimina vi
    public void procesaElimina() {
        String entrada ="";
        System.out.print("nom (enter cancel·la)> ");
        entrada = Entrada.readLine();
        System.out.println();
        if(entrada.isBlank()){return;}
        Vi cercat = botiga.cerca(entrada);
        if (cercat == null) {
            System.out.println("No trobat");
        }
        System.out.println("A eliminar:\n"+botiga.cerca(entrada));
        System.out.print("Segur?>");
        if (UtilitatsConfirmacio.respostaABoolean(Entrada.readLine())) {
            cercat = botiga.elimina(entrada);
            if (cercat == null) {
                System.out.println("Eliminat");
            } else {
                System.out.println("ERROR: no s'ha pogut eliminar");    
            }
        } else {
            System.out.println("No eliminat");
        }
    }
    public static void mostraErrorComandaDesconeguda() {
        System.out.println("ERROR: comanda no reconeguda. Escriviu help per ajuda");
    }
}