package models;

/**
 * Enumerare pentru statusul cursului
 * Demonstrează utilizarea enum în OOP
 */
public enum StatusCurs {
    PLANIFICAT("Planificat"),
    IN_CURS("În curs"),
    INCHEIAT("Încheiat"),
    SUSPENDAT("Suspendat");

    private final String descriere;

    StatusCurs(String descriere) {
        this.descriere = descriere;
    }

    public String getDescriere() {
        return descriere;
    }

    @Override
    public String toString() {
        return descriere;
    }
}
