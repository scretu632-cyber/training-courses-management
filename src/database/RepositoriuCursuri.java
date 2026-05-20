package database;

import models.Curs;
import models.StatusCurs;
import exceptions.DatabaseException;
import exceptions.CursException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasă DAO (Data Access Object) pentru Cursuri
 * Încapsulează operații SQL
 * Demonstrează CRUD și polimorfism
 */
public class RepositoriuCursuri {
    private Connection connection;

    public RepositoriuCursuri() throws DatabaseException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    /**
     *CREATE - Adaugă un curs nou
     */
    public int adauga(Curs curs) throws CursException {
        try {
            if (!curs.valideaza()) {
                throw new CursException("Cursul nu trece validarea");
            }

            String sql = "INSERT INTO Cursuri (denumire, descriere, durata_ore, pret, data_start, data_end, instructor, capacitate, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, curs.getDenumire());
            stmt.setString(2, curs.getDescriere());
            stmt.setInt(3, curs.getDurata_ore());
            stmt.setDouble(4, curs.getPret());
            stmt.setString(5, curs.getData_start().toString());
            stmt.setString(6, curs.getData_end().toString());
            stmt.setString(7, curs.getInstructor());
            stmt.setInt(8, curs.getCapacitate());
            stmt.setString(9, curs.getStatus().toString());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            throw new CursException("Eroare la inserarea cursului");
        } catch (SQLException e) {
            throw new CursException("Eroare SQL la adăugarea cursului: " + e.getMessage(), e);
        }
    }

    /**
     * READ - Recuperează un curs după ID
     */
    public Curs getPeId(int id) throws CursException {
        try {
            String sql = "SELECT * FROM Cursuri WHERE id_curs = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapareResultSet(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new CursException("Eroare SQL la recuperarea cursului: " + e.getMessage(), e);
        }
    }

    /**
     * READ - Recuperează toate cursurile
     */
    public List<Curs> getToate() throws CursException {
        List<Curs> cursuri = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Cursuri ORDER BY id_curs";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                cursuri.add(mapareResultSet(rs));
            }
        } catch (SQLException e) {
            throw new CursException("Eroare SQL la recuperarea cursurilor: " + e.getMessage(), e);
        }
        return cursuri;
    }

    /**
     * UPDATE - Actualizează un curs
     */
    public boolean actualizeaza(Curs curs) throws CursException {
        try {
            if (!curs.valideaza()) {
                throw new CursException("Cursul nu trece validarea");
            }

            String sql = "UPDATE Cursuri SET denumire=?, descriere=?, durata_ore=?, pret=?, " +
                    "data_start=?, data_end=?, instructor=?, capacitate=?, status=? WHERE id_curs=?";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, curs.getDenumire());
            stmt.setString(2, curs.getDescriere());
            stmt.setInt(3, curs.getDurata_ore());
            stmt.setDouble(4, curs.getPret());
            stmt.setString(5, curs.getData_start().toString());
            stmt.setString(6, curs.getData_end().toString());
            stmt.setString(7, curs.getInstructor());
            stmt.setInt(8, curs.getCapacitate());
            stmt.setString(9, curs.getStatus().toString());
            stmt.setInt(10, curs.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new CursException("Eroare SQL la actualizarea cursului: " + e.getMessage(), e);
        }
    }

    /**
     * DELETE - Șterge un curs
     */
    public boolean sterge(int id) throws CursException {
        try {
            String sql = "DELETE FROM Cursuri WHERE id_curs = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new CursException("Eroare SQL la ștergerea cursului: " + e.getMessage(), e);
        }
    }

    /**
     * SEARCH - Caută cursuri după denumire
     */
    public List<Curs> cautaDupaDenumire(String denumire) throws CursException {
        List<Curs> cursuri = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Cursuri WHERE denumire LIKE ? ORDER BY id_curs";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, "%" + denumire + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cursuri.add(mapareResultSet(rs));
            }
        } catch (SQLException e) {
            throw new CursException("Eroare SQL la căutare: " + e.getMessage(), e);
        }
        return cursuri;
    }

    /**
     * FILTER - Filtrează cursuri după status
     */
    public List<Curs> filtreazaDupaStatus(StatusCurs status) throws CursException {
        List<Curs> cursuri = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Cursuri WHERE status = ? ORDER BY id_curs";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, status.toString());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cursuri.add(mapareResultSet(rs));
            }
        } catch (SQLException e) {
            throw new CursException("Eroare SQL la filtrare: " + e.getMessage(), e);
        }
        return cursuri;
    }

    /**
     * FILTER - Filtrează cursuri după interval de preț
     */
    public List<Curs> filtreazaDupaPret(double pretMin, double pretMax) throws CursException {
        List<Curs> cursuri = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Cursuri WHERE pret >= ? AND pret <= ? ORDER BY pret";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, pretMin);
            stmt.setDouble(2, pretMax);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cursuri.add(mapareResultSet(rs));
            }
        } catch (SQLException e) {
            throw new CursException("Eroare SQL la filtrare după preț: " + e.getMessage(), e);
        }
        return cursuri;
    }

    /**
     * Mapează ResultSet la obiect Curs
     */
    private Curs mapareResultSet(ResultSet rs) throws SQLException {
        Curs curs = new Curs();
        curs.setId(rs.getInt("id_curs"));
        curs.setDenumire(rs.getString("denumire"));
        curs.setDescriere(rs.getString("descriere"));
        curs.setDurata_ore(rs.getInt("durata_ore"));
        curs.setPret(rs.getDouble("pret"));
        curs.setData_start(LocalDate.parse(rs.getString("data_start")));
        curs.setData_end(LocalDate.parse(rs.getString("data_end")));
        curs.setInstructor(rs.getString("instructor"));
        curs.setCapacitate(rs.getInt("capacitate"));
        curs.setStatus(StatusCurs.valueOf(rs.getString("status")));
        return curs;
    }

    /**
     * Returnează numărul de cursuri
     */
    public int getNumarCursuri() throws CursException {
        try {
            String sql = "SELECT COUNT(*) as total FROM Cursuri";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("total");
            }
            return 0;
        } catch (SQLException e) {
            throw new CursException("Eroare la numărarea cursurilor: " + e.getMessage(), e);
        }
    }
}
