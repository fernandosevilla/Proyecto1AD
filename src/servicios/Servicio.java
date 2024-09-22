package servicios;

import modelo.Contacto;

import java.io.File;
import java.util.List;

public interface Servicio {
    /**
     *
     * @param contacto
     * @return
     */
    boolean aniadirContacto(Contacto contacto);

    /**
     *
     * @param contacto
     * @return
     */
    boolean eliminarContacto(Contacto contacto);

    /**
     *
     * @return
     */
    List<Contacto> listadoContactos();

    /**
     *
     * @param nombre
     * @return
     */
    Contacto consultarContacto(String nombre);

    /**
     *
     * @param contacto
     * @return
     */
    boolean editarContacto(Contacto contacto);

    /**
     *
     * @param file
     * @param listaDeContactos
     */
    void guardarContacto(File file, List<Contacto> listaDeContactos);

    /**
     *
     * @param file
     * @return
     */
    List<Contacto> cargarContactos(File file);
}
