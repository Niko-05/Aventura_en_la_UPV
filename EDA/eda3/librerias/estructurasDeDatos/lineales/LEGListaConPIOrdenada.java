package librerias.estructurasDeDatos.lineales;
import librerias.estructurasDeDatos.modelos.ListaConPI;

/**
 * Write a description of class LEGListaConPIOrdenada here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LEGListaConPIOrdenada<E extends Comparable<E>> extends LEGListaConPI<E>{
    // instance variables - replace the example below with your own
    public void insertar(E e){
        inicio();
        while (!esFin()&&e.compareTo(recuperar())>0){
            siguiente();
        }
        super.insertar(e);
    }
}
