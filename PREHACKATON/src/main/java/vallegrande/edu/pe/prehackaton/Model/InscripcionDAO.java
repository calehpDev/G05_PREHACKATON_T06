package vallegrande.edu.pe.prehackaton.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InscripcionDAO {

    public List<Inscripcion> listar() throws SQLException {
        List<Inscripcion> lista = new ArrayList<>();
        String sql = "SELECT id_inscripcion, id_evento, id_participante, fecha_inscripcion, estado " +
                "FROM inscripciones ORDER BY id_inscripcion ASC";

        try (Connection conn = vallegrande.edu.pe.sistema_web.Model.Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Inscripcion i = new Inscripcion();
                i.setIdInscripcion(rs.getInt("id_inscripcion"));
                i.setIdEvento(rs.getInt("id_evento"));
                i.setIdParticipante(rs.getInt("id_participante"));
                i.setFechaInscripcion(rs.getTimestamp("fecha_inscripcion"));
                i.setEstado(rs.getString("estado"));
                lista.add(i);
            }
        }
        return lista;
    }

    public List<Inscripcion> buscar(String texto) throws SQLException {
        List<Inscripcion> lista = new ArrayList<>();
        String sql = "SELECT id_inscripcion, id_evento, id_participante, fecha_inscripcion, estado " +
                "FROM inscripciones " +
                "WHERE CAST(id_inscripcion AS CHAR) LIKE ? OR CAST(id_evento AS CHAR) LIKE ? " +
                "OR CAST(id_participante AS CHAR) LIKE ? OR estado LIKE ? " +
                "ORDER BY id_inscripcion ASC";

        try (Connection conn = vallegrande.edu.pe.sistema_web.Model.Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String filtro = "%" + texto + "%";
            stmt.setString(1, filtro);
            stmt.setString(2, filtro);
            stmt.setString(3, filtro);
            stmt.setString(4, filtro);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Inscripcion i = new Inscripcion();
                    i.setIdInscripcion(rs.getInt("id_inscripcion"));
                    i.setIdEvento(rs.getInt("id_evento"));
                    i.setIdParticipante(rs.getInt("id_participante"));
                    i.setFechaInscripcion(rs.getTimestamp("fecha_inscripcion"));
                    i.setEstado(rs.getString("estado"));
                    lista.add(i);
                }
            }
        }
        return lista;
    }

    public void insertar(Inscripcion inscripcion) throws SQLException {
        String sql = "INSERT INTO inscripciones (id_evento, id_participante, estado) VALUES (?, ?, ?)";

        try (Connection conn = vallegrande.edu.pe.sistema_web.Model.Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, inscripcion.getIdEvento());
            stmt.setInt(2, inscripcion.getIdParticipante());
            stmt.setString(3, inscripcion.getEstado());
            stmt.executeUpdate();
        }
    }

    public void actualizar(Inscripcion inscripcion) throws SQLException {
        String sql = "UPDATE inscripciones SET id_evento = ?, id_participante = ?, estado = ? WHERE id_inscripcion = ?";

        try (Connection conn = vallegrande.edu.pe.sistema_web.Model.Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, inscripcion.getIdEvento());
            stmt.setInt(2, inscripcion.getIdParticipante());
            stmt.setString(3, inscripcion.getEstado());
            stmt.setInt(4, inscripcion.getIdInscripcion());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM inscripciones WHERE id_inscripcion = ?";

        try (Connection conn = vallegrande.edu.pe.sistema_web.Model.Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}