package controlador;

import modelo.Contacto;
import servicios.ServicioImpl;
import ventana.Ventana;

import javax.swing.*;
import java.io.File;
import java.util.List;

public class Controlador {
    private final ServicioImpl servicio;

    public Controlador() {
        servicio = new ServicioImpl();
    }

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
                    if (vista.getModeloTabla().getValueAt(i, 0).equals(nombre)) {
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

    public void limpiarCeldas(Ventana vista) {
        vista.getCeldaNombre().setText("");
        vista.getCeldaTelefono().setText("");
    }
}
