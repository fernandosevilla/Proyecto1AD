package ventana;

import controlador.Controlador;
import modelo.ListaContactos;
import servicios.Servicio;
import servicios.ServicioImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private JButton guardarButton;
    private JButton cargarButton;
    private JButton limpiarButton;
    private JTable jtTabla;
    private final DefaultTableModel modeloTabla;
    Servicio servicio;
    ListaContactos test;
    Controlador controlador;

    public JPanel getPanel1() {
        return panel1;
    }

    public JTextField getCeldaNombre() {
        return celdaNombre;
    }

    public JTextField getCeldaTelefono() {
        return celdaTelefono;
    }
    public JTable getJtTabla() {
        return jtTabla;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public Ventana() {
        servicio = new ServicioImpl();
        test = new ListaContactos();
        controlador = new Controlador();

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
                controlador.agregarContacto(Ventana.this);
                controlador.limpiarCeldas(Ventana.this);
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
                controlador.consultarContacto(Ventana.this);
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
                controlador.editarContacto(Ventana.this);
                controlador.limpiarCeldas(Ventana.this);
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
                controlador.eliminarContacto(Ventana.this);
                controlador.limpiarCeldas(Ventana.this);
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
                controlador.guardarContactos(Ventana.this);
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
                controlador.cargarContactos(Ventana.this);
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
                controlador.limpiarCeldas(Ventana.this);
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
}
