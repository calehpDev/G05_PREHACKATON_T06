package vallegrande.edu.pe.prehackaton.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ParticipanteDAO {

    // INSERTAR PARTICIPANTE
    public boolean insertar(Participante p) {
        String sql = "INSERT INTO participantes (nombres, apellidos, dni, correo, telefono, institucion) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombres());
            ps.setString(2, p.getApellidos());
            ps.setString(3, p.getDni());
            ps.setString(4, p.getCorreo());
            ps.setString(5, p.getTelefono());
            ps.setString(6, p.getInstitucion());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar participante: " + e.getMessage());
            return false;
        }
    }

    // LISTAR TODOS LOS PARTICIPANTES
    public List<Participante> listarTodos() {
        List<Participante> lista = new ArrayList<>();
        String sql = "SELECT * FROM participantes";
        try (Connection con = Conexion.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Participante(
                        rs.getInt("id_participante"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("dni"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rs.getString("institucion")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar participantes: " + e.getMessage());
        }
        return lista;
    }

    // ACTUALIZAR PARTICIPANTE
    public boolean actualizar(Participante p) {
        String sql = "UPDATE participantes SET nombres=?, apellidos=?, dni=?, correo=?, telefono=?, institucion=? WHERE id_participante=?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombres());
            ps.setString(2, p.getApellidos());
            ps.setString(3, p.getDni());
            ps.setString(4, p.getCorreo());
            ps.setString(5, p.getTelefono());
            ps.setString(6, p.getInstitucion());
            ps.setInt(7, p.getIdParticipante());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar participante: " + e.getMessage());
            return false;
        }
    }

    // ELIMINAR PARTICIPANTE
    public boolean eliminar(int idParticipante) {
        String sql = "DELETE FROM participantes WHERE id_participante=?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idParticipante);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar participante: " + e.getMessage());
            return false;
        }
    }

    // REPORTE: FILTRAR POR INSTITUCIÓN
    public List<Participante> listarPorInstitucion(String institucion) {
        List<Participante> lista = new ArrayList<>();
        String sql = "SELECT * FROM participantes WHERE institucion LIKE ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + institucion + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Participante(
                            rs.getInt("id_participante"),
                            rs.getString("nombres"),
                            rs.getString("apellidos"),
                            rs.getString("dni"),
                            rs.getString("correo"),
                            rs.getString("telefono"),
                            rs.getString("institucion")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al filtrar participantes: " + e.getMessage());
        }
        return lista;
    }
}