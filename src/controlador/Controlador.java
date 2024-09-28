package controlador;

import modelo.Contacto;
import servicios.ServicioImpl;
import ventana.Ventana;

import javax.swing.*;
import java.io.File;
import java.util.List;

/**
 * clase controlador que conecta servicios con ventana
 * @author Fer
 */
public class Controlador {
    private final ServicioImpl servicio;

    /**
     * constructor vacio para controlador inicializando servicio
     */
    public Controlador() {
        servicio = new ServicioImpl();
    }

    /**
     * metodo para agregar una cuenta la cual sincroniza la informacion
     * de las celdas de nombre y de telefono, verifica si estan vacias
     * alguna de las dos y si estan vacias nos notifica y si no lo están
     * se crea un contacto con la informacion dada en las celdas,
     * si este contacto no existe se añade en la tabla una fila nueva
     * con el contacto y si existe nos salta una notificacion informandonos
     * de que no existe
     * @param vista la vista en la que estamos, se coge la informacion
     * y se plasma esta en el jtable
     */
    public void agregarContacto(Ventana vista) {
        String nombre = vista.getCeldaNombre().getText();
        String telefono = vista.getCeldaTelefono().getText();

        if (nombre.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(vista.getPanel1(),
                    "El campo de nombre o telefono está vacío",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            Contacto nuevoContacto = new Contacto(nombre, telefono);
            if (servicio.aniadirContacto(nuevoContacto)) {
                vista.getModeloTabla().addRow(new Object[] {nombre, telefono});
            } else {
                JOptionPane.showMessageDialog(vista.getPanel1(),
                        "El contacto que has intentado crear ya existe",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * metodo en el que se coge el nombre de la celda, si esta vacio
     * nos notifica de que lo está, si no crea el objeto contacto
     * llamando al metodo consultarContacto() en servicios,
     * si no es nulo recorre las filas de la tabla. Dentro del bucle
     * si en el numero de fila que sea verifica el nombre que haya en x fila
     * con el que se pasa por parametro es el mismo, es el que se selecciona
     * para mostrarlo. Si es nulo nos notifica de que el contacto
     * que se quiere consultar no existe
     * @param vista la vista en la que estamos, se coge la informacion
     * y se plasma esta en el jtable
     */
    public void consultarContacto(Ventana vista) {
        String nombre = vista.getCeldaNombre().getText();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vista.getPanel1(),
                    "El campo de nombre del contacto que quieres editar está vacío",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Contacto contacto = servicio.consultarContacto(nombre);

            if (contacto != null) {
                for (int i = 0; i < vista.getModeloTabla().getRowCount(); i++) {
                    if (vista.getModeloTabla().getValueAt(i, 0).equals(nombre)) { // i es la fila y 0 la columna (el nombre)
                        vista.getJtTabla().setRowSelectionInterval(i, i);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(vista.getPanel1(),
                        "El contacto que quieres consultar no existe",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    /**
     * metodo que cogemos la informacion de las celdas, verificamos si alguna esta
     * vacia y si es asi lo notificamos, si no es asi creamos un contacto
     * con esta informacion, hacemos un if para editar el contacto y dentro
     * recorremos la tabla hasta que coincida el nombre con el del contacto
     * que se le pasa, establecemos el nuevo numero de telefono y notificamos
     * de que se ha editado la informacion del contacto.
     * si no se pudo encontrar el contacto significa que no existe y lo notificamos
     * @param vista la vista en la que estamos, se coge la informacion
     * y se plasma esta en el jtable
     */
    public void editarContacto(Ventana vista) {
        String nombre = vista.getCeldaNombre().getText();
        String telefono = vista.getCeldaTelefono().getText();

        if (nombre.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(vista.getPanel1(),
                    "No has escrito el nombre o el telefono",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Contacto contactoEditado = new Contacto(nombre, telefono);
            if (servicio.editarContacto(contactoEditado)) {
                for (int i = 0; i < vista.getModeloTabla().getRowCount(); i++) {
                    if (vista.getModeloTabla().getValueAt(i, 0).equals(nombre)) {
                        vista.getModeloTabla().setValueAt(telefono, i, 1);
                    }
                }
                JOptionPane.showMessageDialog(vista.getPanel1(),
                        "Contacto editado",
                        "Editar", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(vista.getPanel1(),
                        "No se ha encontrado el contacto que se quería editar",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * metodo en el que cogemos la informacion de las celdas, si
     * la celda de nombre esta vacia pues nos lo notifica, si no crea
     * el contacto que vamos a eliminar, llamamos al metodo de eliminar el
     * contacto pasandole el contacto que hemos creado, recoorremos
     * las filas de la tabla y verificamos por nombre, una vez coincida pues
     * borramos el contacto y lo notificamos. Si el contacto no se encuentra
     * quiere decir que el contacto que queremos eliminar no existe asique
     * nos lo notifica.
     * @param vista la vista en la que estamos, se coge la informacion
     * y se plasma esta en el jtable
     */
    public void eliminarContacto(Ventana vista) {
        String nombre = vista.getCeldaNombre().getText();
        String telefono = vista.getCeldaTelefono().getText();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vista.getPanel1(),
                    "No has escrito el nombre",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Contacto contacto = new Contacto(nombre, telefono);

            if (servicio.eliminarContacto(contacto)) {
                for (int i = 0; i < vista.getModeloTabla().getRowCount(); i++) {
                    if (vista.getModeloTabla().getValueAt(i, 0).equals(nombre)) {
                        vista.getModeloTabla().removeRow(i);
                    }
                }
                JOptionPane.showMessageDialog(vista.getPanel1(),
                        "Contacto eliminado",
                        "Eliminar",
                        JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(vista.getPanel1(),
                        "No se ha encontrado el contacto que se quiere eliminar",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Creamos un selector de fichero y creamos una variable de tipo entera
     * el cual le pasamos el selector y le ponemos que nos saque el menu
     * de dialogo sobre el selector. Si el JFileChooser no da problemas
     * (APPROVE_OPTION) creamos un archivo que va a ser el que se crea con
     * el selector de fichero y llamamos a la funcion de servicio para guardar
     * la lista de contactos en un archivo y lo notificamos.
     * Si da un problema essto (ERROR_OPTION) pues notificamos que no se ha podido
     * guardar el archivo
     * @param vista la vista en la que estamos, se coge la informacion
     * y se plasma esta en el jtable
     */
    public void guardarContactos(Ventana vista) {
        JFileChooser selectorFichero = new JFileChooser();
        int resultadoSelectorFichero = selectorFichero.showSaveDialog(null);

        if (resultadoSelectorFichero == JFileChooser.APPROVE_OPTION) {
            File archivo = selectorFichero.getSelectedFile();
            servicio.guardarContacto(archivo, servicio.listadoContactos());
            JOptionPane.showMessageDialog(vista.getPanel1(),
                    "Has guardado el archivo",
                    "Guardado",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (resultadoSelectorFichero == JFileChooser.ERROR_OPTION) {
            JOptionPane.showMessageDialog(vista.getPanel1(),
                    "No se ha podido guardar el archivo",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * creamos un selector de ficheros, se lo pasamos con una variable de tipo
     * entera para que se abra el menu de dialogo para seleccionar un fichero
     * (IMPORTANTE, NO EL DE GUARDAR COMO EL DE ANTES, QUE SON DISTINTOS).
     * Si el selector de ficheros no da problemas (APPROVE_OPTION) pues creamos
     * el archivo que vamos a cargar pasandole pues este archivo desde el menu
     * (seleccionandolo), creamos una lista de contactos nueva a la que vamos a
     * pasarle el metodo de cargar los contactos de servicios. Ponemos a 0 las
     * filas que hay en la tabla y si la lista de contactos no esta vacia
     * pues recorremos la lista de contactos y vamos creando filas en la tabla con
     * el addRow pasandole por parametros nuevos arrays de objetos con el nombre
     * y telefono de cada contacto y una vez termine de cargar todos los datos pues lo
     * notificamos. Si el archivo esta vacio pues lo notificamos tambien.
     * Si el fichero da problemas (ERROR_OPTION) pues notificamos de que
     * no se ha podido cargar el archivo.
     * @param vista la vista en la que estamos, se coge la informacion
     * y se plasma esta en el jtable
     */
    public void cargarContactos(Ventana vista) {
        JFileChooser selectorFichero = new JFileChooser();
        int resultadoSelectorFichero = selectorFichero.showOpenDialog(null);

        if (resultadoSelectorFichero == JFileChooser.APPROVE_OPTION) {
            File archivo = selectorFichero.getSelectedFile();
            List<Contacto> contactosCargados = servicio.cargarContactos(archivo);
            vista.getModeloTabla().setRowCount(0);

            if (!contactosCargados.isEmpty()) {
                for (Contacto contacto : contactosCargados) {
                    vista.getModeloTabla().addRow(new Object[]{contacto.getNombre(), contacto.getNumeroTelefono()});
                }
                JOptionPane.showMessageDialog(vista.getPanel1(),
                        "Se ha cargado el archivo",
                        "Cargado",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(vista.getPanel1(),
                        "El archivo esta vacio",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } else if (resultadoSelectorFichero == JFileChooser.ERROR_OPTION) {
            JOptionPane.showMessageDialog(vista.getPanel1(),
                    "No se ha podido cargar el archivo con los datos",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * metodo para limpiar (vaciar de datos) las celdas de nombre y telefono
     * @param vista la vista en la que estamos, se coge la informacion
     * y se plasma esta en el jtable
     */
    public void limpiarCeldas(Ventana vista) {
        vista.getCeldaNombre().setText("");
        vista.getCeldaTelefono().setText("");
    }
}
