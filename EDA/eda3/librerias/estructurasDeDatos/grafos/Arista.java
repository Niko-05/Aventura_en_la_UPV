package librerias.estructurasDeDatos.grafos;

/** Clase Arista: representa una arista de un grafo.<br> 
 *  
 *  @version noviembre 2021
 */
 
public class Arista implements Comparable<Arista>{
    
    // UNA Arista TIENE UN vertice origen y UN vertice destino:
    /*COMPLETAR*/
    int origen;
    int destino;
    // UNA Arista TIENE UN peso:
    /*COMPLETAR*/
    double peso;
    
    /** Crea una arista (v, w) con peso p.
      *
      * @param v  Vertice origen
      * @param w  Vertice destino
      * @param p  Peso
     */
    public Arista(int v, int w, double p) {
        // COMPLETAR
        origen = v;
        destino = w;
        peso = p;
    }

    /** Devuelve el vertice origen de una arista.
      *
      * @return int vertice origen
     */
    public int getOrigen() {    
        // CAMBIAR 
        return origen;
    }
    
    /** Devuelve el vertice destino de una arista.
      *
      * @return int vertice destino
     */
    public int getDestino() {  
        // CAMBIAR 
        return destino;
    }
    
    /** Devuelve el peso de una arista.
      *
      * @return double Peso de la arista
     */
    public double getPeso() {
        // CAMBIAR 
        return peso;  
    }
    
    /** Devuelve un String que representa una arista
      * en formato (origen, destino, peso)
      *
      * @return  String que representa la arista
     */
    public String toString() {
        // CAMBIAR 
        return "(" + origen + ", " + destino + ", " + peso + ")";   
    }
    public int compareTo(Arista a) {
        double aux = this.peso - a.peso;
        if (aux == 0) return 0;
        else if(aux > 0) return 1;
        else return -1;
    }
}