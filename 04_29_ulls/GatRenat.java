/*  Ejercicio que comprueba que valor asigna en la posicio del gat renat y en caso de que no sea correcta le dice que esta estirado
    esta versión usa el 'this' para llamr a una variable con el mismo nombre, con muchos constructores
    implementa la clase Ull que permite al gato tener ojos */
    public class GatRenat {
        private int vides = 7;
        private String posicio = "estirat";
        private UllDeGat[] ulls = new UllDeGat [2];

        public GatRenat(){
            setPosicio("estirat");
            setVides(7);
            ulls[0] = new UllDeGat();
            ulls[1] = new UllDeGat();
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
        public UllDeGat getUllDret() {
            return ulls[0];
        }
        
        public UllDeGat getUllEsquerre() {
            return ulls[1];
        }
        public void aixecat(){
            setPosicio("dret");
            ulls[1].obret();
            ulls[0].obret();
        }
        public void seu() {
            setPosicio("assegut");
            ulls[1].tancat();
            ulls[0].obret();
        }
        public void estirat() {
            setPosicio("estirat");
            ulls[0].tancat();
            ulls[1].tancat();
        }

   //main del programa     	

public static void main(String[] args) {
    GatRenat renat = new GatRenat();
    UllDeGat ullDret = renat.getUllDret();
    UllDeGat ullEsquerre = renat.getUllEsquerre();
    System.out.printf("Quan està %s: %b + %b%n",
            renat.getPosicio(),
            renat.getUllDret().estaObert(),
            renat.getUllEsquerre().estaObert());
    renat.seu();
    System.out.printf("Quan està %s: %b + %b%n",
            renat.getPosicio(),
            renat.getUllDret().estaObert(),
            renat.getUllEsquerre().estaObert());
    renat.aixecat();
    System.out.printf("Quan està %s: %b + %b%n",
            renat.getPosicio(),
            renat.getUllDret().estaObert(),
            renat.getUllEsquerre().estaObert());
}

    }