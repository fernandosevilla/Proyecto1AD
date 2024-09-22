package modelo;

import java.util.ArrayList;
import java.util.List;

public class ListaContactos {
    private List<Contacto> listaContactos = new ArrayList<Contacto>();

    public ListaContactos() {
        
    }

    public List<Contacto> getListaContactos() {
        return listaContactos;
    }

    public void setListaContactos(List<Contacto> listaContactos) {
        this.listaContactos = listaContactos;
    }
}
