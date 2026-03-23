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
