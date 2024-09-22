package modelo;

public class Contacto {
    private String nombre;
    private String numeroTelefono;

    /**
     *
     */
    public Contacto() {
    }

    /**
     *
     * @param nombre
     * @param numeroTelefono
     */
    public Contacto(String nombre, String numeroTelefono) {
        this.nombre = nombre;
        this.numeroTelefono = numeroTelefono;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    /**
     *
     * @param numeroTelefono
     */
    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Contacto{" +
                "nombre='" + nombre + '\'' +
                ", numeroTelefono='" + numeroTelefono + '\'' +
                '}';
    }
}
