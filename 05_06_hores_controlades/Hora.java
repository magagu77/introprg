/** Clase hora que implementa horas minutos y segundos (privado),
 *  con valor por defecto 0:00:00,, con los seters y getters correspondientes 
 *  con funciones de incrementar/incrementar(int), decrementar/decrementar(int),
 *  compareTo(Hora) y toString 
 * */
public class Hora {
    private int hores = 0;
    private int minuts = 0;
    private int segons = 0;

    //Constructor de horas minutos y segundos
    public Hora(int hores, int minuts, int segons) {
        if(hores >=24 || hores <0 || minuts >= 60 || minuts < 0 ||segons >= 60 || segons < 0) {
            setHores(0);
            setMinuts(0);
            setSegons(0);
        } else {
            setHores(hores);
            setMinuts(minuts);
            setSegons(segons);
        }
    }
    public Hora() {
        setHores(0);
        setMinuts(0);
        setSegons(0);
    }

    // Getters
    public int getHores() { return this.hores;}

    public int getMinuts() { return this.minuts;}

    public int getSegons() { return this.segons;}

    // Setters
    public boolean setHores(int hores) {
        if (hores < 24 && hores >= 0) {
            this.hores = hores;
            return true;
        } else {
            return false;
        }
    }
    public boolean setMinuts(int minuts) {
        if (minuts < 60 && minuts >= 0) {
            this.minuts = minuts;
            return true;
        } else {
            return false;
        }
    }
    public boolean setSegons (int segons) {
        if (segons < 60 && segons >= 0) {
            this.segons = segons;
            return true;
        } else {
            return false;
        }
    }

    // Incrementales
    public void incrementa() {
        if (segons + 1 < 60) {
            setSegons(getSegons()+1);
        } else if (segons + 1 == 60) {
            setSegons(0);
            if (minuts + 1 < 60) {
                setMinuts(getMinuts()+1);
            } else if (minuts + 1 == 60) {
                setMinuts(0);
                if (hores + 1 < 24) {
                    setHores(getHores()+1);
                } else if (hores + 1 >= 24) {
                    setHores(0);
                }
            }
        }
    }
    // Incrementa un numero de segundos que se le indica 
    public void incrementa(int segons) {
        int hora=0;
        int minuts=0;
        if (segons <  0) {
            decrementa(Math.abs(segons));
        } else {
            if (segons >= 3600) {
                hora = segons / 3600;
                minuts = (segons / 60) - (hora * 60);
                segons = segons - ((hora * 3600) + (minuts * 60));
                incrementa(hora, minuts, segons);
            } else if (segons < 3600 && segons > 59) {
                hora = 0;
                minuts = segons / 60;
                segons = segons - (minuts * 60);
                incrementa(hora, minuts, segons);
            } else if (segons <=59 && segons >=0) {
                hora = 0;
                minuts = 0;
                incrementa(hora, minuts, segons);
            }
        }
    }
    // hace los augmentos
    public void incrementa(int hora, int minuts, int segons) {
        if (getSegons() + segons >= 60) {
            minuts++;
            setSegons(getSegons()+segons-60);
        } else {
            setSegons(getSegons()+segons);
        }
        if (getMinuts() + minuts >= 60) {
            hora++;
            setMinuts(getMinuts()+minuts-60);
        } else {
            setMinuts(getMinuts() + minuts);
        }
        if (hora >= 24){
            int numVueltas = hora/24;
            hora = hora - numVueltas * 24;
        }
        if (getHores() + hora >= 24) {
            setHores((getHores()-24) + hora);
        } else {
            setHores(getHores() + hora);
        }      
    }

    // Decrementales
    public void decrementa() {
        if (getSegons() - 1 >= 0) {
            setSegons(getSegons()-1);
        } else if (getSegons() - 1 < 0) {
            setSegons(59);
            if (getMinuts() - 1 >= 0) {
                setMinuts(getMinuts()-1);
            } else if (getMinuts() - 1 < 0) {
                setMinuts(59);
                if (getHores() - 1 >= 0) {
                    setHores(getHores()-1);
                } else if (getHores() - 1 < 0) {
                    setHores(23);
                }
            }
        }
    }
    // Decrementa un numero de sugundos que se le indica
    public void decrementa(int segons) {
        int hora=0;
        int minuts=0;
        if (segons <  0) {
            incrementa(Math.abs(segons));
        } else {
            if (segons >= 3600) {
                hora = segons / 3600;
                minuts = (segons / 60) - (hora * 60);
                segons = segons - ((hora * 3600) + (minuts * 60));
                decrementa(hora,minuts,segons);
            } else if (segons < 3600 && segons > 59) {
                hora = 0;
                minuts = segons / 60;
                segons = segons - (minuts * 60);
                decrementa(hora, minuts, segons);
            } else if (segons <=59 && segons >=0) {
                hora = 0;
                minuts = 0;
                decrementa(hora, minuts, segons);
            }
        }
    }
    // Hace la resta de segundos que toque
    public void decrementa(int hora, int minuts, int segons) {
        if (hora > 24){
            int numVueltas = hora/24;
            hora = hora - numVueltas * 24;
        }
        if (getSegons() - segons < 0) {
            minuts++;
            setSegons(getSegons()+60-segons);
        } else {
            setSegons(getSegons()-segons);
        }
        if (getMinuts()- minuts < 0) {
            hora++;
            setMinuts(getMinuts()+60-minuts);
        } else {
            setMinuts(getMinuts()-minuts);
        }
        if (getHores()-hora < 0) {
            setHores(getHores()+24-hora);
        } else {
            setHores(getHores()-hora);
        }
    }
    public int compareTo( Hora hora2) {
        if (getHores() == hora2.getHores() && getMinuts() == hora2.getMinuts() && getSegons() == hora2.getSegons()) return 0;
        else if (getHores() == hora2.getHores() && getMinuts() == hora2.getMinuts() && getSegons() > hora2.getSegons()) return 1;
        else if (getHores() == hora2.getHores() && getMinuts() > hora2.getMinuts()) return 1;
        else if (getHores() > hora2.getHores()) return 1;
        else return -1;
    }
    public static boolean esValida (int hores, int minuts, int segons) {
        if ( hores < 24 && hores >= 0 && minuts < 60 && minuts >= 0 
            && segons < 60 && segons >= 0) return true;
        else return false;
    }
    public static int compareTo(Hora hora1, Hora hora2) {
        if (hora1.getHores() == hora2.getHores() && hora1.getMinuts() == hora2.getMinuts() && hora1.getSegons() == hora2.getSegons()) return 0;
        else if (hora1.getHores() == hora2.getHores() && hora1.getMinuts() == hora2.getMinuts() && hora1.getSegons() > hora2.getSegons()) return 1;
        else if (hora1.getHores() == hora2.getHores() && hora1.getMinuts() > hora2.getMinuts()) return 1;
        else if (hora1.getHores() > hora2.getHores()) return 1;
        else return -1;
    }
    public Hora duplica() {
        Hora hora1 = new Hora();
        hora1.setHores(this.getHores());
        hora1.setMinuts(this.getMinuts());
        hora1.setSegons(this.getSegons());
        return hora1;
    }
    public static Hora duplica (Hora hora) {
        Hora hora1 = new Hora(hora.getHores(),hora.getMinuts(),hora.getSegons());
        return hora1;
    }

    @Override
    public String toString () {
        return String.format("%d:%02d:%02d", hores, minuts, segons );
    }

    /**
 * Compara dues hores i retorna l'operador corresponent
 * Per exemple, si hora1 és menor que hora2, l'operador serà "<". Els
 * altres dos valors possibles són ">" i "=="
 * @param hora1: primera hora a comparar
 * @param hora2: segona hora a comparar
 * @return operador resultant
 */
private static String composaOperadorComparacio(Hora hora1, Hora hora2) {
    int comparacio = hora1.compareTo(hora2);
    if (comparacio < 0) {
        return "<";
    } else if (comparacio > 0) {
        return ">";
    } else {
        return "==";
    }
}
}