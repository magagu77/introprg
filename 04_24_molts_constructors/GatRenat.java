/*  Ejercicio que comprueba que valor asigna en la posicio del gat renat y en caso de que no sea correcta le dice que esta estirado
    esta versión usa el 'this' para llamr a una variable con el mismo nombre*/
    public class GatRenat {
        private int vides;
        private String posicio;
        public GatRenat(int vides, String posicio) {
            setVides(vides);
            setPosicio(posicio);
        }
        @Override
        public String toString() {
            return String.format("Vides: %d. Posició: %s", vides, posicio);
        }
        public int getVides() { return vides; }

        public void setVides (int vides) {
            if (vides >= 0) {
                this.vides = vides;
            } else {
                this.vides = 7;
            }
        }
    
        public String getPosicio() {return posicio;}
    
        public void setPosicio (String posicio) {
            if (posicioCorrecta(posicio)) {
                this.posicio = posicio; 
            }
            else {
                this.posicio = "estirat";
            }
        }
        //Comprova posicio correcta
        public static boolean posicioCorrecta (String posicio) {
            if(posicio.equals("dret") || posicio.equals("assegut") 
            || posicio.equals("estirat")) return true; 
            else return false;
        }
    
        public static void main(String[] args) {
            System.out.println(new GatRenat(7, "dret"));
        }
    }