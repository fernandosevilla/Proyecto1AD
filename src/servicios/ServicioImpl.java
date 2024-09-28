package servicios;

import modelo.Contacto;
import modelo.ListaContactos;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * clase que implementa la interfaz "Servicio" en la que implementamos todos sus metodos abstractos
 * y los sobreescribimos para su funcionamiento en el programa
 * @author Fer
 */
public class ServicioImpl implements Servicio {
    ListaContactos contactos;

    /**
     * constructor vacio para su implementacion en la clase controlador e iniciaizamos
     * el objeto contactos
     */
    public ServicioImpl() {
        contactos = new ListaContactos();
    }

    /**
     * el metodo lo primero que hace es buscar la posicion del contacto, si es
     * -1 significa que el contacto que se le esta pasando no existe entonces se añade
     * a la lista
     * @param contacto el contacto que queremos añadir a la lista de contactos
     * @return devuelve true si se ha podido añadir el contacto y false si no
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
     * el metodo lo primero que hace es buscar la posicion del contacto, si esta
     * no es -1 significa que el contacto si existe, entonces lo eliminamos
     * @param contacto el contacto que queremos eliminar de la lista de contactos
     * @return devuelve true si se ha podido eliminar el contacto y false si no
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
     * metodo para obtener la lista de contactos
     * @return devuelve la lista de contactos
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
     * buscamos la posicion del contacto que le pasamos, si es distinto a -1
     * significa que el contacto existe y le cambiamos el numero de telefono
     * @param contacto contacto que queremos cambiarle el numero de telefono
     * @return devuelte true si se ha podido editar el contacto y si no false
     */
    @Override
    public boolean editarContacto(Contacto contacto) {
        int posicion = posicionContacto(contacto.getNombre());

        if (posicion != -1) {
            // obtienemos la posicion del contacto que vamos a editar
            Contacto contactoParaEditar = contactos.getListaContactos().get(posicion);
            contactoParaEditar.setNumeroTelefono(contacto.getNumeroTelefono());
            return true;
        }

        return false;
    }

    /**
     * metodo para guardar en un archivo una lista de contactos usando BufferedWritter,
     * recorriendo la lista de contactos y separando la informacion con guiones
     * (segun el modelo: nombre-telefono) y haciendo tratamiento de excepciones
     * con entrada/salida y haciendo un finally para poder cerrar el BufferedWritter
     * con tratamiento de excepciones ahi tambien
     * @param file el archivo donde guardaremos la lista de contactos
     * @param listaDeContactos la lista de contactos que queremos guardar
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
                }
            }
        }
    }

    /**
     * metodo para cargar una lista de contactos desde un archivo, primero
     * creamos una nueva lista como ArrayList, despues inicializamos el
     * BufferedReader, hacemos que se vaya leyendo la linea con un readLine(),
     * mientras que haya lineas en el archivo pues vamos a separar mediante el
     * delimitador "-" la informacion con un array separandolo por nombre y por telefono.
     * Despues añadimos en la lista un nuevo contacto con la informacion sacada en los
     * arrays. Despues hacemos un set para establecer la lista de contactos nueva
     * y tratamos las excepciones. Por ultimo hacemos un finally tambien con tratamiento
     * de excepciones para cerrar el BufferedReader
     * @param file archivo para cargar los contactos que hayan en el
     * @return devuelve la lista de contactos
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
                }
            }
        }

        return listadoContactos;
    }

    /**
     * metodo el cual lo vamos a usar de apoyo para los demas metodos de la clase
     * el cual vamos a obtener la posicion de un contacto mediante su nombre.
     * Inicializamos la variable posicion con -1, si la lista de contactos no esta
     * vacia o no es nula vamos a recorrer la lista de contactos y comprobando
     * si los nombres de la lista de contactos coinciden con el nombre que le pasamos.
     * Si alguna coincide sacamos su posicion en la lista
     * @param nombre el nombre del contacto del que quermeos saber su posicion
     * @return devuelve o la posicion del contacto o -1 si este no existe
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
