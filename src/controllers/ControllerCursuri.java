package controllers;

import models.Curs;
import models.StatusCurs;
import database.RepositoriuCursuri;
import exceptions.DatabaseException;
import exceptions.CursException;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller pentru operații cu Cursuri
 * Intermediar între View și Model/Database
 * Demonstrează pattern Controller
 */
public class ControllerCursuri {
    private RepositoriuCursuri repoCursuri;

    public ControllerCursuri() throws DatabaseException {
        this.repoCursuri = new RepositoriuCursuri();
    }

    /**
     * Adaugă un curs nou
     */
    public boolean adaugaCurs(String denumire, String descriere, int durata, double pret,
                             LocalDate dataStart, LocalDate dataEnd, String instructor, int capacitate) 
            throws CursException {
        Curs curs = new Curs();
        curs.setDenumire(denumire);
        curs.setDescriere(descriere);
        curs.setDurata_ore(durata);
        curs.setPret(pret);
        curs.setData_start(dataStart);
        curs.setData_end(dataEnd);
        curs.setInstructor(instructor);
        curs.setCapacitate(capacitate);
        curs.setStatus(StatusCurs.PLANIFICAT);

        try {
            int id = repoCursuri.adauga(curs);
            return id > 0;
        } catch (CursException e) {
            throw new CursException("Eroare la adăugarea cursului: " + e.getMessage(), e);
        }
    }

    /**
     * Obține un curs după ID
     */
    public Curs obtineCurs(int id) throws CursException {
        return repoCursuri.getPeId(id);
    }

    /**
     * Obține toate cursurile
     */
    public List<Curs> obtineToateCursurile() throws CursException {
        return repoCursuri.getToate();
    }

    /**
     * Actualizează un curs
     */
    public boolean actualizezaCurs(int id, String denumire, String descriere, int durata, 
                                  double pret, LocalDate dataStart, LocalDate dataEnd, 
                                  String instructor, int capacitate, StatusCurs status) throws CursException {
        Curs curs = repoCursuri.getPeId(id);
        if (curs == null) {
            throw new CursException("Cursul nu există");
        }

        curs.setDenumire(denumire);
        curs.setDescriere(descriere);
        curs.setDurata_ore(durata);
        curs.setPret(pret);
        curs.setData_start(dataStart);
        curs.setData_end(dataEnd);
        curs.setInstructor(instructor);
        curs.setCapacitate(capacitate);
        curs.setStatus(status);

        return repoCursuri.actualizeaza(curs);
    }

    /**
     * Șterge un curs
     */
    public boolean stergeCurs(int id) throws CursException {
        return repoCursuri.sterge(id);
    }

    /**
     * Caută cursuri după denumire
     */
    public List<Curs> cautaCursuri(String denumire) throws CursException {
        return repoCursuri.cautaDupaDenumire(denumire);
    }

    /**
     * Filtrează cursuri după status
     */
    public List<Curs> filtreazaCursuriStatus(StatusCurs status) throws CursException {
        return repoCursuri.filtreazaDupaStatus(status);
    }

    /**
     * Filtrează cursuri după preț
     */
    public List<Curs> filtreazaCursuriPret(double pretMin, double pretMax) throws CursException {
        return repoCursuri.filtreazaDupaPret(pretMin, pretMax);
    }

    /**
     * Obține numărul total de cursuri
     */
    public int numarCursuri() throws CursException {
        return repoCursuri.getNumarCursuri();
    }
}
