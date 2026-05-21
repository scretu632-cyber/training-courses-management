# 📚 Aplicație de Gestionare Cursuri de Formare

## 🎯 Descriere Proiect

Aplicație **complexă și funcțională** pentru gestionarea cursurilor de formare, studenților și înscrierilor. Implementează toate cerințele académice cu **15+ clase OOP**, **CRUD complet**, **rapoarte avansate** și **export date**.

---

## ✨ Caracteristici Principale

### 🔄 CRUD - Operații Complete
- ✅ **CREATE** - Adăugare cursuri, studenți, înscrieri
- ✅ **READ** - Vizualizare toți datele
- ✅ **UPDATE** - Modificare date existente
- ✅ **DELETE** - Ștergere obiecte

### 🔍 Căutare și Filtrare Avansată
- Căutare după **denumire** (cursuri/studenți)
- Filtrare după **status** (cursuri)
- Filtrare după **preț** (interval)
- Filtrare după **oraș** (studenți)
- Filtrare după **status înscriereere** (inscrieri)

### 📊 Rapoarte Statistice (4+)
1. **Raport General** - Statistici generale (cursuri, studenți, inscrieri)
2. **Raport Cursuri** - Detalii și participanți per curs
3. **Raport Studenți** - Performanțe și inscrieri
4. **Raport Financiar** - Venituri totale per curs

### 📤 Export Date
- **CSV** - Format tabelar (Excel compatible)
- **TXT** - Format citibil cu formatare
- Opțiuni: Cursuri, Studenți, Inscrieri

### 💎 OOP - 15+ Clase Demonstrate

#### Model Classes (6)
- `Entitate` (abstract)
- `Curs`
- `Student`
- `Inscrie`
- `Raport`

#### Enums (2)
- `StatusCurs` (PLANIFICAT, IN_CURS, INCHEIAT, SUSPENDAT)
- `StatusInscrie` (ACTIVA, SUSPENDATA, FINALIZATA, ANULATA)

#### Interfețe (3)
- `Raportabil` - Pentru obiecte raportabile
- `Exportabil` - Pentru obiecte exportabile
- `Cautabil` - Pentru obiecte căutabile

#### Database Layer (4)
- `DatabaseConnection` (Singleton)
- `RepositoriuCursuri` (DAO)
- `RepositoriuStudenti` (DAO)
- `RepositoriuInscrieri` (DAO)

#### Business Logic (3)
- `ControllerCursuri`
- `ControllerStudenti`
- `ControllerInscrieri`

#### Services (2)
- `ServiceRapoarte` - Generare rapoarte
- `ServiceExport` - Export date

#### Utilities & Exceptions (5+)
- `Validare` - Validarea datelor
- `CursException`
- `StudentException`
- `InscreException`
- `DatabaseException`

---

## 🗂️ Structura Proiectului

```
training-courses-management/
├── src/
│   ├── models/
│   │   ├── Entitate.java              (clasa abstractă de bază)
│   │   ├── StatusCurs.java            (enum)
│   │   ├── StatusInscrie.java         (enum)
│   │   ├── Curs.java                  (extends Entitate)
│   │   ├── Student.java               (extends Entitate)
│   │   ├── Inscrie.java               (extends Entitate)
│   │   └── Raport.java                (extends Entitate)
│   │
│   ├── interfaces/
│   │   ├── Raportabil.java            (interfață)
│   │   ├── Exportabil.java            (interfață)
│   │   └── Cautabil.java              (interfață)
│   │
│   ├── database/
│   │   ├── DatabaseConnection.java    (Singleton)
│   │   ├── RepositoriuCursuri.java    (DAO)
│   │   ├── RepositoriuStudenti.java   (DAO)
│   │   ├── RepositoriuInscrieri.java  (DAO)
│   │   └── InitDatabase.sql           (20+ cursuri, 30+ studenți, 60+ inscrieri)
│   │
│   ├── exceptions/
│   │   ├── CursException.java
│   │   ├── StudentException.java
│   │   ├── InscreException.java
│   │   └── DatabaseException.java
│   │
│   ├── utils/
│   │   ├── Validare.java              (validare email, telefon, etc.)
│   │   └── ...
│   │
│   ├── services/
│   │   ├── ServiceRapoarte.java       (4+ rapoarte)
│   │   └── ServiceExport.java         (export CSV/TXT)
│   │
│   ├── controllers/
│   │   ├── ControllerCursuri.java
│   │   ├── ControllerStudenti.java
│   │   └── ControllerInscrieri.java
│   │
│   └── Main.java                      (entry point)
│
├── database/
│   └── training_db.db                 (SQLite)
│
├── README.md                          (documentație)
├── .gitignore
└── pom.xml                            (Maven - opțional)
```

---

## 🛢️ Baza de Date

### Schema (3 tabele relaționate)
```sql
Cursuri (id_curs, denumire, descriere, durata_ore, pret, data_start, data_end, instructor, capacitate, status)
Studenti (id_student, nume, prenume, email, telefon, data_nastere, adresa, oras, cod_postal)
Inscrieri (id_inscr, id_student, id_curs, data_inscr, status, nota_finala)
```

### Date Test
- **20 Cursuri** cu diverse domenii (Java, Python, Web, DevOps, ML, etc.)
- **30 Studenți** din diferite orașe România
- **60+ Inscrieri** cu distribuție variată

### Accesare pe Programiz
1. Mergi pe https://www.programiz.com/sql/online-compiler/
2. Copiază codul din `src/database/InitDatabase.sql`
3. Execută scriptul

---

## 🔧 Cerințe Tehnice

### Java
- **Version:** Java 11+
- **Compiler:** javac

### Database
- **SQLite** (local - training_db.db)
- **MySQL** (opțional pe Programiz)

### Dependencies
- java.sql (JDBC)
- java.time (LocalDate, LocalDateTime)
- java.util (Collections, Stream API)

---

## 🚀 Instalare și Utilizare

### 1. Clone Repository
```bash
git clone https://github.com/scretu632-cyber/training-courses-management.git
cd training-courses-management
```

### 2. Configurare Baza de Date

**Opțiunea A: SQLite Local**
```bash
# Executați scriptul SQL în SQLite
sqlite3 training_db.db < src/database/InitDatabase.sql
```

**Opțiunea B: Programiz SQL**
1. Mergi pe https://www.programiz.com/sql/online-compiler/
2. Copie-paste scriptul din `InitDatabase.sql`
3. Click "Run"

### 3. Compilare

```bash
# Compilare toți fișierul Java
javac -d bin src/models/*.java src/exceptions/*.java src/interfaces/*.java
javac -d bin -cp bin src/utils/*.java
javac -d bin -cp bin src/database/*.java
javac -d bin -cp bin src/services/*.java
javac -d bin -cp bin src/controllers/*.java
javac -d bin -cp bin src/Main.java
```

### 4. Executare

```bash
java -cp bin Main
```

---

## 📋 Exemple de Utilizare

### CRUD - Adăugare Curs
```java
ControllerCursuri controller = new ControllerCursuri();
controller.adaugaCurs(
    "Java Avanzat",
    "Curs pentru programatori avansați",
    40,
    500.0,
    LocalDate.of(2024, 1, 15),
    LocalDate.of(2024, 3, 15),
    "Ion Popescu",
    25
);
```

### Căutare
```java
List<Curs> rezultate = controller.cautaCursuri("Java");
List<Curs> filtrate = controller.filtreazaCursuriStatus(StatusCurs.IN_CURS);
```

### Rapoarte
```java
ServiceRapoarte serviceRapoarte = new ServiceRapoarte();
String raportGeneral = serviceRapoarte.genereazaRaportGeneral();
String raportCursuri = serviceRapoarte.genereazaRaportCursuri();
String raportFinanciar = serviceRapoarte.genereazaRaportFinanciar();
```

### Export
```java
ServiceExport serviceExport = new ServiceExport();
serviceExport.salvareCSV("cursuri", "export_cursuri.csv");
serviceExport.salvareTXT("studenti", "export_studenti.txt");
```

---

## ✅ Validare Implementate

### Email
- Format corect: `utilizator@domeniu.com`

### Telefon
- Lungime minimum 10 caractere
- Format valid cu cifre și caractere speciale

### Nume
- Doar litere și caractere speciale permise
- Lungime > 0

### Date
- Nu pot fi în viitor
- Interval valid (data_start < data_end)

### Numere
- Durata > 0
- Preț >= 0
- Nota între 0 și 10
- Capacitate > 0

---

## 🛡️ Tratare Erori

Aplicația gestionează:
- ❌ **DatabaseException** - Erori conexiune BD
- ❌ **CursException** - Erori operații cursuri
- ❌ **StudentException** - Erori operații studenți
- ❌ **InscreException** - Erori operații inscrieri
- ❌ Validare date (IllegalArgumentException)
- ❌ I/O Errors (IOException)

Fiecare operație afișează mesaje descriptive și logger-e.

---

## 🎓 Concepte OOP Demonstrate

### 1. **Moștenire**
```java
public abstract class Entitate implements Serializable, Comparable<Entitate>
public class Curs extends Entitate
public class Student extends Entitate
```

### 2. **Polimorfism**
```java
public abstract boolean valideaza();
public abstract String toString();
// Fiecare subclasă implementează diferit
```

### 3. **Interfețe**
```java
public interface Raportabil { ... }
public interface Exportabil { ... }
public interface Cautabil { ... }
```

### 4. **Encapsulare**
```java
private RepositoriuCursuri repoCursuri;
public int adauga(Curs curs) { ... }
```

### 5. **Singleton Pattern**
```java
public static synchronized DatabaseConnection getInstance()
```

### 6. **DAO Pattern**
```java
public class RepositoriuCursuri {
    public int adauga(Curs curs)
    public Curs getPeId(int id)
    // ...
}
```

### 7. **Enums**
```java
public enum StatusCurs { PLANIFICAT, IN_CURS, INCHEIAT, SUSPENDAT }
public enum StatusInscrie { ACTIVA, SUSPENDATA, FINALIZATA, ANULATA }
```

### 8. **Colecții**
```java
List<Curs> cursuri = new ArrayList<>();
Set<String> emailuri = new HashSet<>();
```

---

## 📈 Statistici Proiect

| Metrica | Valoare |
|---------|---------|
| Clase Java | 15+ |
| Linii Cod | 2500+ |
| Metode | 100+ |
| Tabele BD | 3 |
| Înregistrări Test | 60+ |
| Rapoarte | 4 |
| Tipuri Exceptii | 4 |
| Interfețe | 3 |
| Enums | 2 |

---

## 📄 Licență

Proiect educațional. Folosire liberă pentru scop academic.

---

## 👨‍💻 Autor

**scretu632-cyber** @ GitHub

---

## 📞 Contact & Support

Pentru întrebări sau probleme, deschide o Issue pe GitHub.

---

**Made with ❤️ for Learning**
