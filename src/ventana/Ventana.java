package ventana;

import modelo.Contacto;
import modelo.ListaContactos;
import servicios.ServicioImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


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
    ServicioImpl servicio;
    ListaContactos test;

    /**
     *
     */
    public Ventana() {
        servicio = new ServicioImpl();
        test = new ListaContactos();
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
                    textArea.setText("");
                    textArea.append("No has escrito o el nombre o el numero de telefono");
                    // System.out.println("No se ha añadido");
                } else {
                    Contacto nuevoContacto = new Contacto(nombre, telefono);
                    if (servicio.aniadirContacto(nuevoContacto)) {
                        textArea.setText("");
                        textArea.append(nuevoContacto.toString());
                        // recorrerLosContactos();
                    } else {
                        textArea.setText("");
                        textArea.append("No se ha podido añadir el contacto");
                        System.out.println("No se ha añadido");
                    }
                }

                celdaNombre.setText("");
                celdaTelefono.setText("");
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
                    textArea.setText("");
                    textArea.append("No has escrito el nombre");
                } else {
                    Contacto contacto = servicio.consultarContacto(nombre);

                    if (contacto != null) {
                        textArea.setText("");
                        textArea.append(contacto.toString());
                    } else {
                        textArea.setText("");
                        textArea.append("No se ha podido consultar el contacto");
                    }

                }
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
                    textArea.setText("");
                    textArea.append("No has escrito el nombre");
                    // System.out.println("No se ha eliminado");
                } else {
                    Contacto contacto = new Contacto(celdaNombre.getText(), telefono);

                    if (servicio.eliminarContacto(contacto)) {
                        textArea.setText("");
                        textArea.setText("Contacto eliminado");
                        // recorrerLosContactos();

                    } else {
                        textArea.setText("");
                        textArea.setText("El contacto que has escrito no existe");
                        // recorrerLosContactos();
                    }
                }

                celdaNombre.setText("");
                celdaTelefono.setText("");
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
                textArea.setText("");
                celdaTelefono.setText("");
                celdaNombre.setText("");
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
}
