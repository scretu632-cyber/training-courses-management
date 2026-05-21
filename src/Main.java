import database.DatabaseConnection;
import services.ServiceRapoarte;
import services.ServiceExport;
import controllers.ControllerCursuri;
import controllers.ControllerStudenti;
import controllers.ControllerInscrieri;
import exceptions.DatabaseException;
import exceptions.CursException;
import exceptions.StudentException;
import exceptions.InscreException;

/**
 * Clasa principală a aplicației
 * Entry point pentru aplicație
 * Demonstrează gestionarea conexiunilor și serviciilor
 */
public class Main {

    public static void main(String[] args) {
        try {
            System.out.println("╔════════════════════════════════════════════════════════════════╗");
            System.out.println("║   🎓 APLICAȚIE GESTIONARE CURSURI DE FORMARE 🎓               ║");
            System.out.println("║                   Versiunea 1.0                              ║");
            System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

            // Inițializare conexiune
            System.out.println("📡 Inițializare conexiune la baza de date...");
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            dbConnection.conectare();

            // Inițializare controlleri
            ControllerCursuri controllerCursuri = new ControllerCursuri();
            ControllerStudenti controllerStudenti = new ControllerStudenti();
            ControllerInscrieri controllerInscrieri = new ControllerInscrieri();

            // Inițializare servicii
            ServiceRapoarte serviceRapoarte = new ServiceRapoarte();
            ServiceExport serviceExport = new ServiceExport();

            // Statistici generale
            System.out.println("\n📊 STATISTICI GENERALE:");
            System.out.println("━".repeat(64));
            int numarCursuri = controllerCursuri.numarCursuri();
            int numarStudenti = controllerStudenti.numarStudenti();
            int numarInscrieri = controllerInscrieri.numarInscrieri();

            System.out.println(String.format("✓ Cursuri în baza de date: %d", numarCursuri));
            System.out.println(String.format("✓ Studenți în baza de date: %d", numarStudenti));
            System.out.println(String.format("✓ Înscrieri în baza de date: %d", numarInscrieri));

            // Afișare meniu
            afiseazaMeniu();

            // Închidere conexiune
            dbConnection.disconnect();
            System.out.println("\n✓ Aplicație închisă cu succes!");

        } catch (DatabaseException e) {
            System.err.println("✗ Eroare de bază de date: " + e.getMessage());
            e.printStackTrace();
        } catch (CursException e) {
            System.err.println("✗ Eroare Curs: " + e.getMessage());
            e.printStackTrace();
        } catch (StudentException e) {
            System.err.println("✗ Eroare Student: " + e.getMessage());
            e.printStackTrace();
        } catch (InscreException e) {
            System.err.println("✗ Eroare Înscriereere: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("✗ Eroare neașteptată: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Afișează meniu cu funcționalități
     */
    private static void afiseazaMeniu() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                        MENIU PRINCIPAL                         ║");
        System.out.println("╠════════════════════════════════════════════════════════════════╣");
        System.out.println("║ CRUD OPERAȚII:                                                ║");
        System.out.println("║   [1] Adaugă/Vizualizează/Modifică/Șterge Cursuri             ║");
        System.out.println("║   [2] Adaugă/Vizualizează/Modifică/Șterge Studenți            ║");
        System.out.println("║   [3] Adaugă/Vizualizează/Modifică/Șterge Înscrieri           ║");
        System.out.println("║                                                               ║");
        System.out.println("║ CĂUTARE ȘI FILTRARE:                                         ║");
        System.out.println("║   [4] Caută cursuri după denumire                             ║");
        System.out.println("║   [5] Filtrează cursuri după status                           ║");
        System.out.println("║   [6] Caută studenți după nume                                ║");
        System.out.println("║   [7] Filtrează studenți după oraș                            ║");
        System.out.println("║                                                               ║");
        System.out.println("║ RAPOARTE:                                                    ║");
        System.out.println("║   [8] Raport General                                          ║");
        System.out.println("║   [9] Raport Cursuri                                          ║");
        System.out.println("║  [10] Raport Studenți                                         ║");
        System.out.println("║  [11] Raport Financiar                                        ║");
        System.out.println("║                                                               ║");
        System.out.println("║ EXPORT:                                                      ║");
        System.out.println("║  [12] Export Cursuri (CSV/TXT)                                ║");
        System.out.println("║  [13] Export Studenți (CSV/TXT)                               ║");
        System.out.println("║  [14] Export Înscrieri (CSV)                                  ║");
        System.out.println("║                                                               ║");
        System.out.println("║  [0] Ieșire                                                   ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        System.out.println("\n💡 Notă: Pentru utilizare avansată, recomandăm interfața GraphicFX.");
        System.out.println("   Această versiune demonstrează funcționalitatea de bază.");
    }
}
