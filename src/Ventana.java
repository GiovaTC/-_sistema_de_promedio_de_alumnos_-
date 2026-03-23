import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Ventana extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton btnCargar;

    public Ventana() {
        setTitle("Promedio de Alumnos");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        modelo = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Promedio"}, 0
        );

        tabla = new JTable(modelo);
        btnCargar = new JButton("Cargar Datos");

        btnCargar.addActionListener(e -> cargarDatos());

        setLayout(new BorderLayout());
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(btnCargar, BorderLayout.SOUTH);
    }

    private void cargarDatos() {

        btnCargar.setEnabled(false);

        SwingWorker<Void, Alumno> worker = new SwingWorker<>() {

            @Override
            protected Void doInBackground() {
                AlumnoDAO dao = new AlumnoDAO();
                List<Alumno> lista = dao.obtenerPromedios();

                for (Alumno a : lista) {
                    publish(a);
                }
                return null;
            }

            @Override
            protected void process(List<Alumno> chunks) {
                for (Alumno a : chunks) {
                    modelo.addRow(new Object[]{
                            a.getId(),
                            a.getNombre(),
                            a.getPromedio()
                    });
                }
            }

            @Override
            protected void done() {
                btnCargar.setEnabled(true);
                JOptionPane.showMessageDialog(null, "Datos cargados");
            }
        };

        worker.execute();
    }
    
// main - metodo principal .
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new Ventana().setVisible(true);
        });
    }
}