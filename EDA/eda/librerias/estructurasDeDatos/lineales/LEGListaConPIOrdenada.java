package librerias.estructurasDeDatos.lineales;
import librerias.estructurasDeDatos.modelos.ListaConPI;

public class LEGListaConPIOrdenada<E extends Comparable> 
    extends LEGListaConPI<E>
    implements ListaConPI<E> 
{
    public void insertar(E e){
        NodoLEG nuevo = new NodoLEG(e);
        inicio();
        while(!esFin() && recuperar().compareTo(nuevo.dato) < 0 ){siguiente();}
        nuevo.siguiente = ant.siguiente;
        ant.siguiente = nuevo;
        if (ant  ==  ult) {ult = nuevo; }
        ant = nuevo;
        talla++;
    }
}
