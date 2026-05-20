package models;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Clasa abstractă de bază pentru toate entitățile din aplicație
 * Implementează interfața Serializable pentru persistență
 * Demonstrează moștenire în OOP
 */
public abstract class Entitate implements Serializable, Comparable<Entitate> {
    private static final long serialVersionUID = 1L;
    protected int id;
    protected LocalDateTime dataCreare;

    public Entitate() {
        this.dataCreare = LocalDateTime.now();
    }

    public Entitate(int id) {
        this.id = id;
        this.dataCreare = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDataCreare() {
        return dataCreare;
    }

    public void setDataCreare(LocalDateTime dataCreare) {
        this.dataCreare = dataCreare;
    }

    /**
     * Metodă abstractă pentru validare
     * Fiecare entitate trebuie să implementeze validarea
     */
    public abstract boolean valideaza();

    /**
     * Metodă abstractă pentru reprezentarea string
     */
    @Override
    public abstract String toString();

    /**
     * Implementare default a comparației
     */
    @Override
    public int compareTo(Entitate o) {
        return Integer.compare(this.id, o.id);
    }
}
