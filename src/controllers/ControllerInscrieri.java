package controllers;

import models.Inscrie;
import models.StatusInscrie;
import database.RepositoriuInscrieri;
import exceptions.DatabaseException;
import exceptions.InscreException;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller pentru operații cu Înscrieri
 * Intermediar între View și Model/Database
 */
public class ControllerInscrieri {
    private RepositoriuInscrieri repoInscrieri;

    public ControllerInscrieri() throws DatabaseException {
        this.repoInscrieri = new RepositoriuInscrieri();
    }

    /**
     * Adaugă o înscrierenoua
     */
    public boolean adaugaInscrierenoua(int idStudent, int idCurs, LocalDate dataInscriereere) 
            throws InscreException {
        Inscrie inscrie = new Inscrie();
        inscrie.setId_student(idStudent);
        inscrie.setId_curs(idCurs);
        inscrie.setData_inscr(dataInscriereere);
        inscrie.setStatus(StatusInscrie.ACTIVA);

        try {
            int id = repoInscrieri.adauga(inscrie);
            return id > 0;
        } catch (InscreException e) {
            throw new InscreException("Eroare la adăugarea înscrierii: " + e.getMessage(), e);
        }
    }

    /**
     * Obține o înscrieredup ID
     */
    public Inscrie obtineInscriereere(int id) throws InscreException {
        return repoInscrieri.getPeId(id);
    }

    /**
     * Obține toate înscrierile
     */
    public List<Inscrie> obtineTotiInscriereeri() throws InscreException {
        return repoInscrieri.getToate();
    }

    /**
     * Actualizează o înscriereexistentă
     */
    public boolean actualizezaInscriereere(int id, StatusInscrie status, Double nota) throws InscreException {
        Inscrie inscrie = repoInscrieri.getPeId(id);
        if (inscrie == null) {
            throw new InscreException("Înscrierea nu există");
        }

        inscrie.setStatus(status);
        inscrie.setNota_finala(nota);

        return repoInscrieri.actualizeaza(inscrie);
    }

    /**
     * Șterge o înscriereexistentă
     */
    public boolean stergeInscriereere(int id) throws InscreException {
        return repoInscrieri.sterge(id);
    }

    /**
     * Obține înscrierile unui student
     */
    public List<Inscrie> obtineInscrieriStudent(int idStudent) throws InscreException {
        return repoInscrieri.getInscrieriStudent(idStudent);
    }

    /**
     * Obține participanții unui curs
     */
    public List<Inscrie> obtineParticipantiCurs(int idCurs) throws InscreException {
        return repoInscrieri.getParticipantiCurs(idCurs);
    }

    /**
     * Filtrează înscrieri după status
     */
    public List<Inscrie> filtreazaInscrieriStatus(StatusInscrie status) throws InscreException {
        return repoInscrieri.filtreazaDupaStatus(status);
    }

    /**
     * Obține numărul total de înscrieri
     */
    public int numarInscrieri() throws InscreException {
        return repoInscrieri.getNumarInscrieri();
    }

    /**
     * Obține media notelor pentru un student
     */
    public Double obtineMediaNote(int idStudent) throws InscreException {
        return repoInscrieri.getMediaNote(idStudent);
    }
}
