# -_sistema_de_promedio_de_alumnos_- :.
📊 Sistema de Promedio de Alumnos:

<img width="1024" height="1024" alt="image" src="https://github.com/user-attachments/assets/6ce3501b-6120-472c-a00b-9e66831c269d" />    

<img width="2548" height="1075" alt="image" src="https://github.com/user-attachments/assets/28d7f970-3de8-4ceb-9a39-8da3d4bcbc3f" />        

```
Java (Swing + IntelliJ) + Oracle 19c

Solución completa, funcional y lista para ejecutar que:

✔ Registra 50 alumnos con 5 asignaturas
✔ Calcula el promedio desde la base de datos
✔ Muestra resultados en consola y en interfaz gráfica (JTable)
✔ Usa JDBC + SwingWorker (UI no bloqueante)

🧩 1. Script Oracle 19c (Tabla + Datos)
CREATE TABLE ALUMNOS (
    ID            NUMBER PRIMARY KEY,
    NOMBRE        VARCHAR2(100),
    NOTA1         NUMBER(5,2),
    NOTA2         NUMBER(5,2),
    NOTA3         NUMBER(5,2),
    NOTA4         NUMBER(5,2),
    NOTA5         NUMBER(5,2)
);

-- Generar 50 alumnos automáticamente
BEGIN
    FOR i IN 1..50 LOOP
        INSERT INTO ALUMNOS VALUES (
            i,
            'Alumno ' || i,
            ROUND(DBMS_RANDOM.VALUE(2,5),2),
            ROUND(DBMS_RANDOM.VALUE(2,5),2),
            ROUND(DBMS_RANDOM.VALUE(2,5),2),
            ROUND(DBMS_RANDOM.VALUE(2,5),2),
            ROUND(DBMS_RANDOM.VALUE(2,5),2)
        );
    END LOOP;
    COMMIT;
END;
/

🧩 2. Clase de Conexión Oracle
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "system";
    private static final String PASS = "1234";

    public static Connection getConexion() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        return DriverManager.getConnection(URL, USER, PASS);
    }
}

🧩 3. Modelo Alumno
public class Alumno {

    private int id;
    private String nombre;
    private double promedio;

    public Alumno(int id, String nombre, double promedio) {
        this.id = id;
        this.nombre = nombre;
        this.promedio = promedio;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPromedio() { return promedio; }
}

🧩 4. DAO (Consulta y cálculo en SQL)
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAO {

    public List<Alumno> obtenerPromedios() {
        List<Alumno> lista = new ArrayList<>();

        String sql = """
            SELECT ID, NOMBRE,
                   ROUND((NOTA1+NOTA2+NOTA3+NOTA4+NOTA5)/5,2) AS PROMEDIO
            FROM ALUMNOS
            ORDER BY ID
        """;

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Alumno a = new Alumno(
                        rs.getInt("ID"),
                        rs.getString("NOMBRE"),
                        rs.getDouble("PROMEDIO")
                );

                // 👉 Salida en consola
                System.out.println(
                        a.getId() + " - " +
                        a.getNombre() + " - Promedio: " +
                        a.getPromedio()
                );

                lista.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}

🧩 5. Interfaz Gráfica (Swing + JTable)
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
}

🧩 6. Clase Principal
public class Ventana {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new Ventana().setVisible(true);
        });
    }
}

⚙️ 7. Dependencias (MUY IMPORTANTE)

Debes agregar el driver de Oracle:

ojdbc8.jar o ojdbc11.jar
📌 En IntelliJ:
File → Project Structure → Libraries → Add JAR
🚀 Resultado Final
✔ Consulta 50 alumnos desde Oracle
✔ Calcula promedio directamente en SQL
✔ Muestra resultados en:
Consola (log técnico)
Interfaz gráfica (JTable)
✔ UI fluida (gracias a SwingWorker)

🧠 Mejora opcional (Nivel Profesional)

Para llevar esta solución a producción:

🔹 Usar pool de conexiones (HikariCP)
🔹 Implementar arquitectura MVC completa
🔹 Agregar filtro por promedio
🔹 Colorear aprobados/reprobados en JTable
🔹 Exportar resultados a Excel / . 
