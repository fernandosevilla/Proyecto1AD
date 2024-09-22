package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * clase en la que se crea una lista de contactos
 */
public class ListaContactos {
    private List<Contacto> listaContactos = new ArrayList<Contacto>();

    /**
     * constructor vacio
     */
    public ListaContactos() {
        
    }

    /**
     * metodo para obtener la lista de todos los contactos
     * @return la lista de todos los contactos
     */
    public List<Contacto> getListaContactos() {
        return listaContactos;
    }

    /**
     * metodo
     * @param listaContactos
     */
    public void setListaContactos(List<Contacto> listaContactos) {
        this.listaContactos = listaContactos;
    }
}
