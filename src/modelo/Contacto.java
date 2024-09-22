package modelo;

/**
 * clase para crear contactos
 */
public class Contacto {
    private String nombre;
    private String numeroTelefono;


    /**
     * Creamos un constructor con el nombre y el telefono de cada contacto
     * @param nombre el nombre del contacto
     * @param numeroTelefono el numero de telefono del contacto
     */
    public Contacto(String nombre, String numeroTelefono) {
        this.nombre = nombre;
        this.numeroTelefono = numeroTelefono;
    }

    /**
     * metodo para obtener el nombre de un contacto
     * @return el nombre de contacto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * metodo para poder cambiar el nombre de algun contacto (ACTUALMENTE EN DESUSO)
     * @param nombre : el nombre por el que queremos cambiar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * metodo para obtener el numero de telefono de un contacto
     * @return el numero de telefono del contacto
     */
    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    /**
     * metodo par poder cambiar el numero de telefono de algun contacto
     * @param numeroTelefono : el numero de telefono por el que queremos cambiar
     */
    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    /**
     * metodo para mostrar la informacion de un contacto
     * @return una cadena con la informacion de un contacto
     */
    @Override
    public String toString() {
        return "Contacto{" +
                "nombre='" + nombre + '\'' +
                ", numeroTelefono='" + numeroTelefono + '\'' +
                '}';
    }
}
