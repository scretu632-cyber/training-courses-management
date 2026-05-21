package services;

import models.Curs;
import models.Inscrie;
import models.Student;
import database.RepositoriuCursuri;
import database.RepositoriuStudenti;
import database.RepositoriuInscrieri;
import exceptions.DatabaseException;
import exceptions.CursException;
import exceptions.StudentException;
import exceptions.InscreException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviciu pentru generare rapoarte
 * Demonstrează agregarea datelor și generare de statistici
 * Implementează diferite tipuri de rapoarte
 */
public class ServiceRapoarte {
    private RepositoriuCursuri repoCursuri;
    private RepositoriuStudenti repoStudenti;
    private RepositoriuInscrieri repoInscrieri;

    public ServiceRapoarte() throws DatabaseException {
        this.repoCursuri = new RepositoriuCursuri();
        this.repoStudenti = new RepositoriuStudenti();
        this.repoInscrieri = new RepositoriuInscrieri();
    }

    /**
     * RAPORT 1: Raport cu statistici generale
     */
    public String genereazaRaportGeneral() throws DatabaseException, CursException, StudentException, InscreException {
        StringBuilder raport = new StringBuilder();
        raport.append("╔════════════════════════════════════════════════════════════════════╗\n");
        raport.append("║           RAPORT GENERAL - GESTIONARE CURSURI DE FORMARE           ║\n");
        raport.append("╚════════════════════════════════════════════════════════════════════╝\n\n");

        int numarCursuri = repoCursuri.getNumarCursuri();
        int numarStudenti = repoStudenti.getNumarStudenti();
        int numarInscrieri = repoInscrieri.getNumarInscrieri();

        raport.append(String.format("📊 STATISTICI GENERALE:\n"));
        raport.append(String.format("   • Numărul total de cursuri: %d\n", numarCursuri));
        raport.append(String.format("   • Numărul total de studenți: %d\n", numarStudenti));
        raport.append(String.format("   • Numărul total de înscrieri: %d\n", numarInscrieri));
        raport.append(String.format("   • Rata medie de ocupare: %.2f%%\n\n", 
            (double) numarInscrieri / (numarCursuri * 25) * 100));

        raport.append("Data generării raportului: ").append(java.time.LocalDate.now()).append("\n");
        
        return raport.toString();
    }

    /**
     * RAPORT 2: Raport cu cursuri și participanți
     */
    public String genereazaRaportCursuri() throws DatabaseException, CursException, InscreException {
        StringBuilder raport = new StringBuilder();
        raport.append("╔════════════════════════════════════════════════════════════════════╗\n");
        raport.append("║              RAPORT CURSURI - DETALII ȘI PARTICIPANȚI             ║\n");
        raport.append("╚════════════════════════════════════════════════════════════════════╝\n\n");

        List<Curs> cursuri = repoCursuri.getToate();

        for (Curs curs : cursuri) {
            List<Inscrie> participanti = repoInscrieri.getParticipantiCurs(curs.getId());
            
            raport.append(String.format("📚 %s (ID: %d)\n", curs.getDenumire(), curs.getId()));
            raport.append(String.format("   Instructor: %s\n", curs.getInstructor()));
            raport.append(String.format("   Durata: %d ore | Preț: %.2f RON\n", curs.getDurata_ore(), curs.getPret()));
            raport.append(String.format("   Status: %s\n", curs.getStatus()));
            raport.append(String.format("   Participant: %d/%d (%.1f%% ocupare)\n", 
                participanti.size(), curs.getCapacitate(), 
                (double) participanti.size / curs.getCapacitate() * 100));
            raport.append("\n");
        }

        return raport.toString();
    }

    /**
     * RAPORT 3: Raport cu studenți și performanțe
     */
    public String genereazaRaportStudenti() throws DatabaseException, StudentException, InscreException {
        StringBuilder raport = new StringBuilder();
        raport.append("╔════════════════════════════════════════════════════════════════════╗\n");
        raport.append("║             RAPORT STUDENȚI - PERFORMANȚE ȘI ÎNSCRISURI          ║\n");
        raport.append("╚════════════════════════════════════════════════════════════════════╝\n\n");

        List<Student> studenti = repoStudenti.getToate();

        for (Student student : studenti) {
            List<Inscrie> inscrieri = repoInscrieri.getInscrieriStudent(student.getId());
            Double mediaNote = repoInscrieri.getMediaNote(student.getId());

            raport.append(String.format("👤 %s %s (ID: %d)\n", student.getNume(), student.getPrenume(), student.getId()));
            raport.append(String.format("   Email: %s | Oraș: %s\n", student.getEmail(), student.getOras()));
            raport.append(String.format("   Cursuri: %d\n", inscrieri.size()));
            
            if (mediaNote != null) {
                raport.append(String.format("   Media notelor: %.2f\n", mediaNote));
            } else {
                raport.append("   Media notelor: N/A\n");
            }
            raport.append("\n");
        }

        return raport.toString();
    }

    /**
     * RAPORT 4: Raport financiar
     */
    public String genereazaRaportFinanciar() throws DatabaseException, CursException, InscreException {
        StringBuilder raport = new StringBuilder();
        raport.append("╔════════════════════════════════════════════════════════════════════╗\n");
        raport.append("║                    RAPORT FINANCIAR - VENITURI                    ║\n");
        raport.append("╚════════════════════════════════════════════════════════════════════╝\n\n");

        List<Curs> cursuri = repoCursuri.getToate();
        double venitTotal = 0;

        raport.append("💰 DETALII VENITURI PE CURS:\n\n");

        for (Curs curs : cursuri) {
            List<Inscrie> participanti = repoInscrieri.getParticipantiCurs(curs.getId());
            double veniCurs = participanti.size() * curs.getPret();
            venitTotal += veniCurs;

            raport.append(String.format("%-30s: %.2f RON (x%d participanți)\n", 
                curs.getDenumire().substring(0, Math.min(30, curs.getDenumire().length())),
                veniCurs, 
                participanti.size()));
        }

        raport.append("\n" + "=".repeat(70) + "\n");
        raport.append(String.format("TOTAL VENITURI: %.2f RON\n", venitTotal));
        raport.append("=".repeat(70) + "\n");

        return raport.toString();
    }

    /**
     * Exportă raport în format CSV
     */
    public String exportRaportCsv() throws DatabaseException, CursException, InscreException {
        StringBuilder csv = new StringBuilder();
        csv.append("ID_Curs,Denumire,Instructor,Durata_ore,Pret,Status,Numar_Participanti\n");

        List<Curs> cursuri = repoCursuri.getToate();
        for (Curs curs : cursuri) {
            List<Inscrie> participanti = repoInscrieri.getParticipantiCurs(curs.getId());
            csv.append(String.format("%d,\"%s\",\"%s\",%d,%.2f,%s,%d\n",
                curs.getId(),
                curs.getDenumire(),
                curs.getInstructor(),
                curs.getDurata_ore(),
                curs.getPret(),
                curs.getStatus(),
                participanti.size()));
        }

        return csv.toString();
    }
}
