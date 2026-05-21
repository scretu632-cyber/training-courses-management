package services;

import models.Curs;
import models.Student;
import models.Inscrie;
import database.RepositoriuCursuri;
import database.RepositoriuStudenti;
import database.RepositoriuInscrieri;
import exceptions.DatabaseException;
import exceptions.CursException;
import exceptions.StudentException;
import exceptions.InscreException;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Serviciu pentru export date
 * Exportă date în format CSV și TXT
 * Demonstrează lucrul cu fișiere și formatare date
 */
public class ServiceExport {
    private RepositoriuCursuri repoCursuri;
    private RepositoriuStudenti repoStudenti;
    private RepositoriuInscrieri repoInscrieri;

    public ServiceExport() throws DatabaseException {
        this.repoCursuri = new RepositoriuCursuri();
        this.repoStudenti = new RepositoriuStudenti();
        this.repoInscrieri = new RepositoriuInscrieri();
    }

    /**
     * Exportă cursuri în CSV
     */
    public String exportCursuriCSV() throws CursException, InscreException {
        StringBuilder csv = new StringBuilder();
        csv.append("ID,Denumire,Instructor,Durata_ore,Pret,Data_Start,Data_End,Status,Participanti,Capacitate\n");

        List<Curs> cursuri = repoCursuri.getToate();
        for (Curs curs : cursuri) {
            List<Inscrie> participanti = repoInscrieri.getParticipantiCurs(curs.getId());
            csv.append(String.format("%d,\"%s\",\"%s\",%d,%.2f,%s,%s,%s,%d,%d\n",
                curs.getId(),
                curs.getDenumire(),
                curs.getInstructor(),
                curs.getDurata_ore(),
                curs.getPret(),
                curs.getData_start(),
                curs.getData_end(),
                curs.getStatus(),
                participanti.size(),
                curs.getCapacitate()));
        }

        return csv.toString();
    }

    /**
     * Exportă studenți în CSV
     */
    public String exportStudentiCSV() throws StudentException, InscreException {
        StringBuilder csv = new StringBuilder();
        csv.append("ID,Nume,Prenume,Email,Telefon,Oras,Cursuri_Inscrisi\n");

        List<Student> studenti = repoStudenti.getToate();
        for (Student student : studenti) {
            List<Inscrie> inscrieri = repoInscrieri.getInscrieriStudent(student.getId());
            csv.append(String.format("%d,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",%d\n",
                student.getId(),
                student.getNume(),
                student.getPrenume(),
                student.getEmail(),
                student.getTelefon() != null ? student.getTelefon() : "",
                student.getOras() != null ? student.getOras() : "",
                inscrieri.size()));
        }

        return csv.toString();
    }

    /**
     * Exportă înscrieri în CSV
     */
    public String exportInscrieriCSV() throws InscreException {
        StringBuilder csv = new StringBuilder();
        csv.append("ID_Inscr,ID_Student,ID_Curs,Data_Inscr,Status,Nota_Finala\n");

        List<Inscrie> inscrieri = repoInscrieri.getToate();
        for (Inscrie inscrie : inscrieri) {
            csv.append(String.format("%d,%d,%d,%s,%s,%s\n",
                inscrie.getId(),
                inscrie.getId_student(),
                inscrie.getId_curs(),
                inscrie.getData_inscr(),
                inscrie.getStatus(),
                inscrie.getNota_finala() != null ? String.format("%.2f", inscrie.getNota_finala()) : ""));
        }

        return csv.toString();
    }

    /**
     * Exportă cursuri în TXT
     */
    public String exportCursuriTXT() throws CursException, InscreException {
        StringBuilder txt = new StringBuilder();
        txt.append("════════════════════════════════════════════════════════════════════\n");
        txt.append("                      EXPORT CURSURI - FORMAT TXT                    \n");
        txt.append("════════════════════════════════════════════════════════════════════\n\n");

        List<Curs> cursuri = repoCursuri.getToate();
        for (Curs curs : cursuri) {
            List<Inscrie> participanti = repoInscrieri.getParticipantiCurs(curs.getId());
            txt.append(String.format("ID: %d\n", curs.getId()));
            txt.append(String.format("Denumire: %s\n", curs.getDenumire()));
            txt.append(String.format("Instructor: %s\n", curs.getInstructor()));
            txt.append(String.format("Durata: %d ore\n", curs.getDurata_ore()));
            txt.append(String.format("Preț: %.2f RON\n", curs.getPret()));
            txt.append(String.format("Data: %s - %s\n", curs.getData_start(), curs.getData_end()));
            txt.append(String.format("Status: %s\n", curs.getStatus()));
            txt.append(String.format("Participanți: %d/%d\n", participanti.size(), curs.getCapacitate()));
            txt.append("─────────────────────────────────────────────────────────────────\n\n");
        }

        return txt.toString();
    }

    /**
     * Exportă studenți în TXT
     */
    public String exportStudentiTXT() throws StudentException, InscreException {
        StringBuilder txt = new StringBuilder();
        txt.append("════════════════════════════════════════════════════════════════════\n");
        txt.append("                      EXPORT STUDENȚI - FORMAT TXT                   \n");
        txt.append("════════════════════════════════════════════════════════════════════\n\n");

        List<Student> studenti = repoStudenti.getToate();
        for (Student student : studenti) {
            List<Inscrie> inscrieri = repoInscrieri.getInscrieriStudent(student.getId());
            txt.append(String.format("ID: %d\n", student.getId()));
            txt.append(String.format("Nume: %s %s\n", student.getNume(), student.getPrenume()));
            txt.append(String.format("Email: %s\n", student.getEmail()));
            txt.append(String.format("Telefon: %s\n", student.getTelefon() != null ? student.getTelefon() : "N/A"));
            txt.append(String.format("Oraș: %s\n", student.getOras()));
            txt.append(String.format("Cursuri inscrisi: %d\n", inscrieri.size()));
            txt.append("─────────────────────────────────────────────────────────────────\n\n");
        }

        return txt.toString();
    }

    /**
     * Salvează date în fișier CSV
     */
    public boolean salvareCSV(String tip, String caleCalFisier) throws IOException, DatabaseException, CursException, StudentException, InscreException {
        String continut = "";
        
        switch (tip.toLowerCase()) {
            case "cursuri":
                continut = exportCursuriCSV();
                break;
            case "studenti":
                continut = exportStudentiCSV();
                break;
            case "inscrieri":
                continut = exportInscrieriCSV();
                break;
            default:
                throw new IllegalArgumentException("Tip necunoscut: " + tip);
        }

        try (FileWriter writer = new FileWriter(caleCalFisier)) {
            writer.write(continut);
            return true;
        }
    }

    /**
     * Salvează date în fișier TXT
     */
    public boolean salvareTXT(String tip, String caleCalFisier) throws IOException, DatabaseException, CursException, StudentException, InscreException {
        String continut = "";
        
        switch (tip.toLowerCase()) {
            case "cursuri":
                continut = exportCursuriTXT();
                break;
            case "studenti":
                continut = exportStudentiTXT();
                break;
            default:
                throw new IllegalArgumentException("Tip necunoscut: " + tip);
        }

        try (FileWriter writer = new FileWriter(caleCalFisier)) {
            writer.write(continut);
            return true;
        }
    }
}
