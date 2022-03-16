public class Ascensor {
    private int pis = -1;
    private String moviment = "aturat";

    public int getPis() {
        return pis;
    }
    public String getMoviment() {
        return moviment;
    }
    public void setPis(int nouPis) {
            if (nouPis <= 10 && nouPis >= -1) {
                pis = nouPis;
            }  
    }
    public void setMoviment(String newMoviment) {
        if (newMoviment.equals("pujant") || newMoviment.equals("aturat") || newMoviment.equals("baixant")){
            moviment = newMoviment;
        }
    }
}
