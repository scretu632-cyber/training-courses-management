package models;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Clasa Raport - Entitate pentru rapoarte
 * Moștenește din Entitate
 * Demonstrează polimorfism și abstractizare
 */
public class Raport extends Entitate {
    private static final long serialVersionUID = 1L;
    private String titlu;
    private String continut;
    private LocalDate data_generare;
    private String tip; // CURSURI, STUDENTI, INSCRIERI, FINANCIAR

    public Raport() {
        super();
        this.data_generare = LocalDate.now();
    }

    public Raport(int id, String titlu, String tip) {
        super(id);
        this.titlu = titlu;
        this.tip = tip;
        this.data_generare = LocalDate.now();
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getContinut() {
        return continut;
    }

    public void setContinut(String continut) {
        this.continut = continut;
    }

    public LocalDate getData_generare() {
        return data_generare;
    }

    public void setData_generare(LocalDate data_generare) {
        this.data_generare = data_generare;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    @Override
    public boolean valideaza() {
        if (titlu == null || titlu.trim().isEmpty()) {
            throw new IllegalArgumentException("Titlul raportului nu poate fi gol");
        }
        if (tip == null || tip.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipul raportului nu poate fi gol");
        }
        return true;
    }

    @Override
    public String toString() {
        return "Raport{" +
                "id=" + id +
                ", titlu='" + titlu + '\'' +
                ", tip='" + tip + '\'' +
                ", data_generare=" + data_generare +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Raport raport = (Raport) o;
        return Objects.equals(titlu, raport.titlu) && Objects.equals(tip, raport.tip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titlu, tip);
    }
}
