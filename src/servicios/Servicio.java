package servicios;

import modelo.Contacto;

import java.io.File;
import java.util.List;

/**
 * Interfaz con metodos abstractos para la implementacion en la clase ServiciosImpl
 * @author Fer
 */
public interface Servicio {
    /**
     * metodo para añadir un contacto a la lista de estos
     * @param contacto el contacto para añadir a la lista
     * @return devuelve true si se ha podido añadir o false si no se ha podido eliminar
     */
    boolean aniadirContacto(Contacto contacto);

    /**
     * metodo para eliminar un contacto de la lista de contactos
     * @param contacto el contacto que queremos eliminar
     * @return devuelve true si se ha eliminado el contacto o false si no se ha podido eliminar
     */
    boolean eliminarContacto(Contacto contacto);

    /**
     * metodo para obtener la lista de contactos
     * @return devuelve la lista de los contactos que hay en esta
     */
    List<Contacto> listadoContactos();

    /**
     * metodo para ver la informacion (el telefono) de un contacto
     * @param nombre el nombre del contacto
     * @return devuelve el contacto si existe en la lista de contactos o null si no existe
     */
    Contacto consultarContacto(String nombre);

    /**
     * metodo para editar la informacion (el telefono) de un contacto
     * @param contacto el contacto que queremos cambiarle el numero de telefono
     * @return devuelve true si se ha podido editar el contacto y false si no se ha podido editar
     */
    boolean editarContacto(Contacto contacto);

    /**
     * metodo para guardar la lista de contactos en un archivo
     * @param file el archivo donde se va a guardar los contactos
     * @param listaDeContactos la lista de contactos que vamos a guardar
     */
    void guardarContacto(File file, List<Contacto> listaDeContactos);

    /**
     * metodo para cargar en una lista de contactos los datos de un archivo
     * @param file el archivo donde se encuentran los contactos para que carguemos
     * @return si se cargan bien los contactos devuelve una lista con la informacion de los contactos
     */
    List<Contacto> cargarContactos(File file);
}
