package servicios;

import modelo.Contacto;
import modelo.ListaContactos;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioImpl implements Servicio {
    ListaContactos contactos;

    public ServicioImpl() {
        contactos = new ListaContactos();
    }

    /**
     * @param contacto
     * @return
     */
    @Override
    public boolean aniadirContacto(Contacto contacto) {
        int posicion = posicionContacto(contacto.getNombre());

        if (posicion == -1) {
            contactos.getListaContactos().add(contacto);
            return true;
        }

        return false;
    }

    /**
     * @param contacto
     * @return
     */
    @Override
    public boolean eliminarContacto(Contacto contacto) {
        int posicion = posicionContacto(contacto.getNombre());

        if (posicion != -1) {
            contactos.getListaContactos().remove(posicion);
            return true;
        }

        return false;
    }

    /**
     * @return
     */
    @Override
    public List<Contacto> listadoContactos() {
        return contactos.getListaContactos();
    }

    /**
     * Recorre todos los contactos, verifica con los nombres de cada contacto de que este existe
     * @param nombre nombre del contacto que se le pasa
     * @return el contacto encontrado o null si no lo encuentra
     */
    @Override
    public Contacto consultarContacto(String nombre) {
        for (Contacto contacto : contactos.getListaContactos()) {
            if (contacto.getNombre().equals(nombre)) {
                return contacto;
            }
        }

        return null;
    }

    /**
     * @param contacto
     * @return
     */
    @Override
    public boolean editarContacto(Contacto contacto) {
        return false;
    }

    /**
     *
     * @param file
     * @param listaDeContactos
     */
    @Override
    public void guardarContacto(File file, List<Contacto> listaDeContactos) {
        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(file));

            for (Contacto contacto : listaDeContactos) {
                bw.write(contacto.getNombre() + "-" + contacto.getNumeroTelefono());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("ERROR: No se ha podido guardar el contacto");
            throw new RuntimeException(e);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    System.err.println("ERROR: no se ha podido cerrar el bw");
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * @param file
     * @return
     */
    @Override
    public List<Contacto> cargarContactos(File file) {
        List<Contacto> listadoContactos = new ArrayList<>();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(file));

            String linea;

            while ((linea = br.readLine()) != null) {
                String[] palabras = linea.split("-");
                String nombre = palabras[0].trim();
                String telefono = palabras[1].trim();
                listadoContactos.add(new Contacto(nombre, telefono));
            }

            contactos.setListaContactos(listadoContactos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.err.println("ERROR: no se ha podido cerrar el br");
                    throw new RuntimeException(e);
                }
            }
        }

        return listadoContactos;
    }

    /**
     *
     * @param nombre
     * @return
     */
    private int posicionContacto(String nombre) {
        int posicion = -1;

        if (!(contactos.getListaContactos().isEmpty() || contactos == null)) {
            for (Contacto contacto : contactos.getListaContactos()) {
                if (contacto.getNombre().equals(nombre)) {
                    posicion = contactos.getListaContactos().indexOf(contacto);
                }
            }
        }

        return posicion;
    }
}
