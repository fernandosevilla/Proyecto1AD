package ventana;

import controlador.Controlador;

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
        controlador = new Controlador();

        /*
            creamos el DefaultTableModel y le pasamos la cabecera (nombres de las columnas)
            y 0 para que no coja filas de primeras
         */
        modeloTabla = new DefaultTableModel(new String[] {"Nombre", "Telefono"}, 0) {
            /**
             * Metodo que sobreescribimos para no poder editar los contactos dando
             * doble click sobre una fila de la tabla
             * @param row la fila
             * @param column la columna
             * @return false para que no se puedan editar
             */
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // asignamos la tabla con el modelo de la tabla
        jtTabla.setModel(modeloTabla);

        añadirButton.addActionListener(new ActionListener() {
            /**
             * Cuando se invoca (le das click a añadir) pues
             * llama a los metodos agregarContacto y 
             * limpiarCeldas de controlador
             *
             * @param e el evento que hace
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.agregarContacto(Ventana.this);
                controlador.limpiarCeldas(Ventana.this);
            }

        });

        consultarButton.addActionListener(new ActionListener() {
            /**
             * Cuando se invoca (le das click a consultar) pues
             * llama al metodo de consultar el contacto
             * de la clase controlador
             *
             * @param e el evento que hace
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.consultarContacto(Ventana.this);
            }
        });

        editarButton.addActionListener(new ActionListener() {
            /**
             * Cuando se invoca (le das click a editar) pues
             * llama a los metodos de editar y de limpiar las celdas
             * de la clase controlador
             *
             * @param e el evento que hace
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.editarContacto(Ventana.this);
                controlador.limpiarCeldas(Ventana.this);
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            /**
             * Cuando se invoca (le das click a editar) pues
             * llama a los metodos eliminar contacto y limpiar
             * las celdas de la clase controlador
             *
             * @param e el evento que hace
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.eliminarContacto(Ventana.this);
                controlador.limpiarCeldas(Ventana.this);
            }
        });

        guardarButton.addActionListener(new ActionListener() {
            /**
             * Cuando se invoca (le das click a editar) pues
             * llama al metodo guardar contacto de la clase
             * controlador
             *
             * @param e el evento que hace
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.guardarContactos(Ventana.this);
            }
        });

        cargarButton.addActionListener(new ActionListener() {
            /**
             * Cuando se invoca (le das click a editar) pues
             * llama al metodo cargar contactos de la clase
             * controlador
             *
             * @param e el evento que hace
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.cargarContactos(Ventana.this);
            }
        });

        limpiarButton.addActionListener(new ActionListener() {
            /**
             * Cuando se invoca (le das click a editar) pues
             * llama al metodo limpiar celdas de la clase
             * controlador
             *
             * @param e el evento que hace
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.limpiarCeldas(Ventana.this);
            }
        });

        jtTabla.addMouseListener(new MouseAdapter() {
            /**
             * modificamos el evento de hacer click con el raton para que cuando
             * se haga click en una fila de la tabla pues se pongas los datos
             * de nombre en la celda nombre y el del telefono pues en la celda
             * del telefono
             *
             * @param e el evento que hace
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = jtTabla.getSelectedRow(); // la fila que se selecciona

                // asegurarnos que hay una fila seleccionada
                if (fila != -1) {
                    // coge el valor que hay de la columna 0 (el nombre) en la fila que se selecciona
                    String nombre = modeloTabla.getValueAt(fila, 0).toString();
                    // coge el valor que hay de la columna 1 (el telefono) en la fila que se selecciona
                    String telefono = modeloTabla.getValueAt(fila, 1).toString();

                    // establecemos en la celda el nombre que se ha cogido haciendo click en la fila de la tabla
                    celdaNombre.setText(nombre);
                    // establecemos en la celda el telefono que se ha cogido haciendo click en la fila de la tabla
                    celdaTelefono.setText(telefono);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Contactos");
        frame.setContentPane(new Ventana().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
