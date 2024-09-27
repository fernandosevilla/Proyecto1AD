package ventana;

import modelo.Contacto;
import modelo.ListaContactos;
import servicios.Servicio;
import servicios.ServicioImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

/**
 *
 */
public class Ventana {
    private JPanel panel1;
    private JTextField celdaNombre;
    private JTextField celdaTelefono;
    private JButton añadirButton;
    private JButton eliminarButton;
    private JButton consultarButton;
    private JButton editarButton;
    private JTextArea textArea;
    private JButton guardarButton;
    private JButton cargarButton;
    private JButton limpiarButton;
    private JTable jtTabla;
    private DefaultTableModel modeloTabla;
    Servicio servicio;
    ListaContactos test;

    /**
     *
     */
    public Ventana() {
        servicio = new ServicioImpl();
        test = new ListaContactos();

        modeloTabla = new DefaultTableModel(new String[] {"Nombre", "Telefono"}, 0) {
            // sobreescribimos el metodo para que no se pueda editar directamente desde la tabla un contacto
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jtTabla.setModel(modeloTabla);

        añadirButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = celdaNombre.getText();
                String telefono = celdaTelefono.getText();

                if (nombre.isEmpty() || telefono.isEmpty()) {
                    JOptionPane.showMessageDialog(panel1, "El campo de nombre o telefono está vacío",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Contacto nuevoContacto = new Contacto(nombre, telefono);
                    if (servicio.aniadirContacto(nuevoContacto)) {
                        modeloTabla.addRow(new Object[] {nombre, telefono});
                    } else {
                        JOptionPane.showMessageDialog(panel1, "El contacto que has intentado crear ya existe",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                limpiarCeldas();
            }
        });

        consultarButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = celdaNombre.getText();

                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(panel1,
                            "El campo de nombre del contacto que quieres editar está vacío",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Contacto contacto = servicio.consultarContacto(nombre);

                    if (contacto != null) {
                        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                            if (modeloTabla.getValueAt(i, 0).equals(nombre)) {
                                jtTabla.setRowSelectionInterval(i, i);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(panel1,
                                "El contacto que quieres consultar no existe",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
                limpiarCeldas();
            }
        });

        editarButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = celdaNombre.getText();
                String telefono = celdaTelefono.getText();

                if (nombre.isEmpty() || telefono.isEmpty()) {
                    JOptionPane.showMessageDialog(panel1,
                            "No has escrito el nombre o el telefono",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Contacto contactoEditado = new Contacto(nombre, telefono);
                    if (servicio.editarContacto(contactoEditado)) {
                        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                            if (modeloTabla.getValueAt(i, 0).equals(nombre)) {
                                modeloTabla.setValueAt(telefono, i, 1);
                            }
                        }
                        JOptionPane.showMessageDialog(panel1,
                                "Contacto editado",
                                "Editar", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(panel1,
                                "No se ha encontrado el contacto que se quería editar",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = celdaNombre.getText();
                String telefono = celdaTelefono.getText();

                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(panel1,
                            "No has escrito el nombre",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Contacto contacto = new Contacto(nombre, telefono);

                    if (servicio.eliminarContacto(contacto)) {
                        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                            if (modeloTabla.getValueAt(i, 0).equals(nombre)) {
                                modeloTabla.removeRow(i);
                            }
                        }
                        JOptionPane.showMessageDialog(panel1,
                                "Contacto eliminado",
                                "Eliminar",
                                JOptionPane.INFORMATION_MESSAGE);

                    } else {
                        JOptionPane.showMessageDialog(panel1,
                                "No se ha encontrado el contacto que se quiere eliminar",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

                limpiarCeldas();
            }
        });

        guardarButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser selectorFichero = new JFileChooser();
                int resultadoSelectorFichero = selectorFichero.showSaveDialog(null);

                if (resultadoSelectorFichero == JFileChooser.APPROVE_OPTION) {
                    File archivo = selectorFichero.getSelectedFile();
                    servicio.guardarContacto(archivo, servicio.listadoContactos());
                    JOptionPane.showMessageDialog(panel1, "Has guardado el archivo", "Guardado", JOptionPane.INFORMATION_MESSAGE);
                } else if (resultadoSelectorFichero == JFileChooser.ERROR_OPTION) {
                    JOptionPane.showMessageDialog(panel1, "No se ha podido guardar el archivo", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cargarButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser selectorFichero = new JFileChooser();
                int resultadoSelectorFichero = selectorFichero.showOpenDialog(null);

                if (resultadoSelectorFichero == JFileChooser.APPROVE_OPTION) {
                    File archivo = selectorFichero.getSelectedFile();
                    List<Contacto> contactosCargados = servicio.cargarContactos(archivo);
                    modeloTabla.setRowCount(0);

                    if (!contactosCargados.isEmpty()) {
                        for (Contacto contacto : contactosCargados) {
                            modeloTabla.addRow(new Object[]{contacto.getNombre(), contacto.getNumeroTelefono()});
                        }
                        JOptionPane.showMessageDialog(panel1,
                                "Se ha cargado el archivo",
                                "Cargado",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(panel1,
                                "El archivo esta vacio",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }

                } else if (resultadoSelectorFichero == JFileChooser.ERROR_OPTION) {
                    JOptionPane.showMessageDialog(panel1, "No se ha podido cargar el archivo con los datos", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        limpiarButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // textArea.setText("");
                celdaTelefono.setText("");
                celdaNombre.setText("");
            }
        });

        jtTabla.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = jtTabla.getSelectedRow(); // la fila que se selecciona

                // asegurarnos que hay una fila seleccionada
                if (fila != -1) {
                    String nombre = modeloTabla.getValueAt(fila, 0).toString();
                    String telefono = modeloTabla.getValueAt(fila, 1).toString();

                    celdaNombre.setText(nombre);
                    celdaTelefono.setText(telefono);
                }
            }
        });
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Contactos");
        frame.setContentPane(new Ventana().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /*
    private void recorrerLosContactos() {
        for (Contacto contacto : test.getListaContactos()) {
            System.out.println(contacto);
        }
    }
    */

    private void limpiarCeldas() {
        celdaNombre.setText("");
        celdaTelefono.setText("");
    }
}
