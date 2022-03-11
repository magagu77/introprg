public class Ascensor {
    private int pis = -1;
    private String moviment = "aturat";

    public int getPis() {
        return pis;
    }
    public String getMoviment() {
        return moviment;
    }
    public void setPis(String newPis) {
        if (UtilString.esEnter(newPis)) {
            int nouPis = Integer.parseInt(newPis);
            pis = nouPis;
        }    
    }
    public void setMoviment(String newMoviment) {
        moviment = newMoviment;
    }
}
