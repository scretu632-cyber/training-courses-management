package database;

import models.Inscrie;
import models.StatusInscrie;
import exceptions.DatabaseException;
import exceptions.InscreException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasă DAO (Data Access Object) pentru Înscrieri
 * Implementează operații CRUD pentru relația Inscrie
 * Demonstrează gestionarea relațiilor many-to-many
 */
public class RepositoriuInscrieri {
    private Connection connection;

    public RepositoriuInscrieri() throws DatabaseException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * CREATE - Adaugă o înscrierenoua
     */
    public int adauga(Inscrie inscrie) throws InscreException {
        try {
            if (!inscrie.valideaza()) {
                throw new InscreException("Înscrierea nu trece validarea");
            }

            String sql = "INSERT INTO Inscrieri (id_student, id_curs, data_inscr, status, nota_finala) " +
                    "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, inscrie.getId_student());
            stmt.setInt(2, inscrie.getId_curs());
            stmt.setString(3, inscrie.getData_inscr().toString());
            stmt.setString(4, inscrie.getStatus().toString());
            stmt.setObject(5, inscrie.getNota_finala());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            throw new InscreException("Eroare la inserarea înscrierii");
        } catch (SQLException e) {
            throw new InscreException("Eroare SQL la adăugarea înscrierii: " + e.getMessage(), e);
        }
    }

    /**
     * READ - Recuperează o înscrieredup ID
     */
    public Inscrie getPeId(int id) throws InscreException {
        try {
            String sql = "SELECT * FROM Inscrieri WHERE id_inscr = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapareResultSet(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new InscreException("Eroare SQL la recuperarea înscrierii: " + e.getMessage(), e);
        }
    }

    /**
     * READ - Recuperează toate înscrierile
     */
    public List<Inscrie> getToate() throws InscreException {
        List<Inscrie> inscrieri = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Inscrieri ORDER BY id_inscr";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                inscrieri.add(mapareResultSet(rs));
            }
        } catch (SQLException e) {
            throw new InscreException("Eroare SQL la recuperarea înscrierilor: " + e.getMessage(), e);
        }
        return inscrieri;
    }

    /**
     * UPDATE - Actualizează o înscriereexistentă
     */
    public boolean actualizeaza(Inscrie inscrie) throws InscreException {
        try {
            if (!inscrie.valideaza()) {
                throw new InscreException("Înscrierea nu trece validarea");
            }

            String sql = "UPDATE Inscrieri SET status=?, nota_finala=? WHERE id_inscr=?";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, inscrie.getStatus().toString());
            stmt.setObject(2, inscrie.getNota_finala());
            stmt.setInt(3, inscrie.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new InscreException("Eroare SQL la actualizarea înscrierii: " + e.getMessage(), e);
        }
    }

    /**
     * DELETE - Șterge o înscriereexistentă
     */
    public boolean sterge(int id) throws InscreException {
        try {
            String sql = "DELETE FROM Inscrieri WHERE id_inscr = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new InscreException("Eroare SQL la ștergerea înscrierii: " + e.getMessage(), e);
        }
    }

    /**
     * SEARCH - Recuperează înscrierile unui student
     */
    public List<Inscrie> getInscrieriStudent(int idStudent) throws InscreException {
        List<Inscrie> inscrieri = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Inscrieri WHERE id_student = ? ORDER BY id_inscr";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idStudent);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                inscrieri.add(mapareResultSet(rs));
            }
        } catch (SQLException e) {
            throw new InscreException("Eroare SQL la recuperarea înscrierilor: " + e.getMessage(), e);
        }
        return inscrieri;
    }

    /**
     * SEARCH - Recuperează participanții unui curs
     */
    public List<Inscrie> getParticipantiCurs(int idCurs) throws InscreException {
        List<Inscrie> inscrieri = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Inscrieri WHERE id_curs = ? ORDER BY id_inscr";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idCurs);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                inscrieri.add(mapareResultSet(rs));
            }
        } catch (SQLException e) {
            throw new InscreException("Eroare SQL la recuperarea participanților: " + e.getMessage(), e);
        }
        return inscrieri;
    }

    /**
     * FILTER - Filtrează înscrierile după status
     */
    public List<Inscrie> filtreazaDupaStatus(StatusInscrie status) throws InscreException {
        List<Inscrie> inscrieri = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Inscrieri WHERE status = ? ORDER BY id_inscr";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, status.toString());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                inscrieri.add(mapareResultSet(rs));
            }
        } catch (SQLException e) {
            throw new InscreException("Eroare SQL la filtrare: " + e.getMessage(), e);
        }
        return inscrieri;
    }

    /**
     * Mapează ResultSet la obiect Inscrie
     */
    private Inscrie mapareResultSet(ResultSet rs) throws SQLException {
        Inscrie inscrie = new Inscrie();
        inscrie.setId(rs.getInt("id_inscr"));
        inscrie.setId_student(rs.getInt("id_student"));
        inscrie.setId_curs(rs.getInt("id_curs"));
        inscrie.setData_inscr(LocalDate.parse(rs.getString("data_inscr")));
        inscrie.setStatus(StatusInscrie.valueOf(rs.getString("status")));
        
        Double nota = rs.getDouble("nota_finala");
        if (!rs.wasNull()) {
            inscrie.setNota_finala(nota);
        }
        
        return inscrie;
    }

    /**
     * Returnează numărul de înscrieri
     */
    public int getNumarInscrieri() throws InscreException {
        try {
            String sql = "SELECT COUNT(*) as total FROM Inscrieri";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("total");
            }
            return 0;
        } catch (SQLException e) {
            throw new InscreException("Eroare la numărarea înscrierilor: " + e.getMessage(), e);
        }
    }

    /**
     * Returnează media notelor pentru un student
     */
    public Double getMediaNote(int idStudent) throws InscreException {
        try {
            String sql = "SELECT AVG(nota_finala) as media FROM Inscrieri WHERE id_student = ? AND nota_finala IS NOT NULL";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idStudent);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Double media = rs.getDouble("media");
                if (!rs.wasNull()) {
                    return media;
                }
            }
            return null;
        } catch (SQLException e) {
            throw new InscreException("Eroare la calcularea mediei: " + e.getMessage(), e);
        }
    }
}
