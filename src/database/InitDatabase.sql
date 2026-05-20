-- Baza de date: Gestionare Cursuri de Formare
-- Pentru utilizare pe Programiz SQL (https://www.programiz.com/sql/online-compiler/)

-- Tabelul Cursuri
CREATE TABLE Cursuri (
    id_curs INTEGER PRIMARY KEY AUTOINCREMENT,
    denumire TEXT NOT NULL UNIQUE,
    descriere TEXT,
    durata_ore INTEGER NOT NULL CHECK (durata_ore > 0),
    pret DECIMAL(10, 2) NOT NULL CHECK (pret >= 0),
    data_start DATE NOT NULL,
    data_end DATE NOT NULL,
    instructor TEXT NOT NULL,
    capacitate INTEGER NOT NULL CHECK (capacitate > 0),
    status TEXT DEFAULT 'PLANIFICAT',
    data_creare TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabelul Studenti
CREATE TABLE Studenti (
    id_student INTEGER PRIMARY KEY AUTOINCREMENT,
    nume TEXT NOT NULL,
    prenume TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    telefon TEXT,
    data_nastere DATE,
    adresa TEXT,
    oras TEXT,
    cod_postal TEXT,
    data_inscr TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabelul Inscrieri (relația many-to-many)
CREATE TABLE Inscrieri (
    id_inscr INTEGER PRIMARY KEY AUTOINCREMENT,
    id_student INTEGER NOT NULL,
    id_curs INTEGER NOT NULL,
    data_inscr DATE NOT NULL,
    status TEXT DEFAULT 'ACTIVA',
    nota_finala DECIMAL(3, 2) CHECK (nota_finala >= 0 AND nota_finala <= 10),
    FOREIGN KEY (id_student) REFERENCES Studenti(id_student) ON DELETE CASCADE,
    FOREIGN KEY (id_curs) REFERENCES Cursuri(id_curs) ON DELETE CASCADE,
    UNIQUE (id_student, id_curs)
);

-- Index-uri pentru performanță
CREATE INDEX idx_curs_status ON Cursuri(status);
CREATE INDEX idx_student_email ON Studenti(email);
CREATE INDEX idx_inscr_student ON Inscrieri(id_student);
CREATE INDEX idx_inscr_curs ON Inscrieri(id_curs);

-- ========== 20 CURSURI ==========
INSERT INTO Cursuri (denumire, descriere, durata_ore, pret, data_start, data_end, instructor, capacitate, status) VALUES
('Java Avanzat', 'Curs de Java pentru nivelul avansat', 40, 500.00, '2024-01-15', '2024-03-15', 'Ion Popescu', 25, 'IN_CURS'),
('Python Bases', 'Introducere în Python', 30, 350.00, '2024-02-01', '2024-03-01', 'Maria Ionescu', 30, 'PLANIFICAT'),
('Web Development', 'HTML, CSS, JavaScript', 50, 600.00, '2024-01-10', '2024-04-10', 'Andrei Vasilescu', 20, 'IN_CURS'),
('Baze de Date SQL', 'SQL și design de baze de date', 35, 450.00, '2024-03-01', '2024-04-15', 'Elena Popovici', 22, 'PLANIFICAT'),
('C++ Programare', 'Curs complet de C++', 45, 550.00, '2024-02-15', '2024-04-30', 'Mihai Georgescu', 18, 'IN_CURS'),
('React.js Masterclass', 'Frontend cu React', 42, 620.00, '2024-01-20', '2024-03-20', 'Catalina Dumitru', 28, 'IN_CURS'),
('Node.js Backend', 'Backend cu Node.js și Express', 38, 580.00, '2024-02-10', '2024-03-25', 'Rares Constantinescu', 24, 'PLANIFICAT'),
('DevOps Fundamentals', 'Docker, Kubernetes, CI/CD', 50, 700.00, '2024-03-15', '2024-05-15', 'Adrian Popescu', 20, 'PLANIFICAT'),
('Machine Learning', 'ML cu Python și TensorFlow', 55, 750.00, '2024-02-20', '2024-04-30', 'Teodora Stoica', 15, 'IN_CURS'),
('Cloud Computing AWS', 'Amazon Web Services - Cloud', 40, 650.00, '2024-03-05', '2024-04-20', 'Viktor Kovacs', 22, 'PLANIFICAT'),
('Mobile App Development', 'Android și iOS cu Flutter', 48, 680.00, '2024-01-25', '2024-03-30', 'Cristina Marinescu', 19, 'IN_CURS'),
('Data Science Analytics', 'Analiza datelor cu Python', 45, 620.00, '2024-02-05', '2024-03-25', 'Bogdan Neagu', 21, 'IN_CURS'),
('Cybersecurity Basics', 'Introducere în securitate IT', 35, 520.00, '2024-03-10', '2024-04-25', 'Sergiu Ionita', 25, 'PLANIFICAT'),
('Agile & Scrum', 'Metodologii agile de management', 24, 380.00, '2024-02-25', '2024-03-20', 'Iuliana Ghica', 35, 'IN_CURS'),
('UI/UX Design', 'Design de interfețe și experiență utilizator', 32, 480.00, '2024-01-30', '2024-03-10', 'Alexandra Rosca', 26, 'PLANIFICAT'),
('Git & Version Control', 'Controlul versiunilor cu Git', 20, 250.00, '2024-02-12', '2024-02-28', 'Vlad Dumitru', 40, 'INCHEIAT'),
('Docker & Containerization', 'Containerizare cu Docker', 30, 450.00, '2024-03-20', '2024-04-20', 'Marian Popescu', 23, 'PLANIFICAT'),
('JavaScript Advanced', 'JavaScript avansat și async', 38, 520.00, '2024-01-28', '2024-03-15', 'Roxana Dobrescu', 27, 'IN_CURS'),
('Blockchain Development', 'Smart Contracts și Blockchain', 50, 800.00, '2024-03-25', '2024-05-25', 'Pavel Vasilescu', 18, 'PLANIFICAT'),
('QA Testing Automation', 'Automatizarea testării', 40, 550.00, '2024-02-18', '2024-04-05', 'Florin Balan', 22, 'IN_CURS');

-- ========== 30 STUDENTI ==========
INSERT INTO Studenti (nume, prenume, email, telefon, data_nastere, adresa, oras, cod_postal) VALUES
('Popescu', 'Ion', 'ion.popescu@email.com', '0712345678', '2000-05-15', 'Str. Principale 10', 'București', '010101'),
('Ionescu', 'Maria', 'maria.ionescu@email.com', '0723456789', '2001-08-20', 'Str. Secundară 5', 'Cluj', '400100'),
('Vasilescu', 'Andrei', 'andrei.vasilescu@email.com', '0734567890', '1999-12-10', 'Str. Tertară 15', 'Iași', '700100'),
('Gheorghe', 'Elena', 'elena.gheorghe@email.com', '0745678901', '2002-03-25', 'Str. Patra 20', 'Timișoara', '300100'),
('Marinescu', 'Alex', 'alex.marinescu@email.com', '0756789012', '2001-07-10', 'Str. Cincia 25', 'Constanța', '900100'),
('Dumitru', 'Cristina', 'cristina.dumitru@email.com', '0767890123', '2000-11-05', 'Str. Șase 30', 'Brașov', '500100'),
('Stoica', 'Teodora', 'teodora.stoica@email.com', '0778901234', '2002-01-15', 'Str. Șapte 35', 'Galați', '800100'),
('Neagu', 'Bogdan', 'bogdan.neagu@email.com', '0789012345', '1998-09-20', 'Str. Opt 40', 'Ploiești', '100100'),
('Kovacs', 'Viktor', 'viktor.kovacs@email.com', '0790123456', '1999-04-08', 'Str. Nouă 45', 'Oradea', '410100'),
('Ionita', 'Sergiu', 'sergiu.ionita@email.com', '0701234567', '2001-06-30', 'Str. Zece 50', 'Sibiu', '550100'),
('Ghica', 'Iuliana', 'iuliana.ghica@email.com', '0712345679', '2000-02-14', 'Str. Unsprezece 55', 'Pitești', '110100'),
('Rosca', 'Alexandra', 'alexandra.rosca@email.com', '0723456780', '2002-08-18', 'Str. Doisprezece 60', 'Arad', '310100'),
('Dumitru', 'Vlad', 'vlad.dumitru@email.com', '0734567891', '1999-10-22', 'Str. Treisprezece 65', 'Bacău', '600100'),
('Popescu', 'Marian', 'marian.popescu@email.com', '0745678902', '2001-12-05', 'Str. Paisprezece 70', 'Botoșani', '710100'),
('Dobrescu', 'Roxana', 'roxana.dobrescu@email.com', '0756789013', '2000-03-17', 'Str. Cincisprezece 75', 'Buzău', '120100'),
('Vasilescu', 'Pavel', 'pavel.vasilescu@email.com', '0767890124', '1998-07-25', 'Str. Șaisprezecer 80', 'Dâmbovița', '130100'),
('Balan', 'Florin', 'florin.balan@email.com', '0778901235', '2002-05-09', 'Str. Șaptesprezece 85', 'Dolj', '140100'),
('Popovici', 'Elena', 'elena.popovici@email.com', '0789012346', '1999-01-12', 'Str. Optsprezece 90', 'Giurgiu', '150100'),
('Constantinescu', 'Rares', 'rares.constantinescu@email.com', '0790123457', '2001-09-16', 'Str. Nouăsprezece 95', 'Gorj', '160100'),
('Georgescu', 'Mihai', 'mihai.georgescu@email.com', '0701234568', '2000-04-20', 'Str. Douăzeci 100', 'Harghita', '170100'),
('Marinescu', 'Catalina', 'catalina.marinescu@email.com', '0712345680', '2002-10-30', 'Str. Douăzeci și unu 105', 'Hunedoara', '180100'),
('Ionescu', 'Victor', 'victor.ionescu@email.com', '0723456781', '1999-06-11', 'Str. Douăzeci și doi 110', 'Ialomița', '190100'),
('Popescu', 'Diana', 'diana.popescu@email.com', '0734567892', '2001-08-28', 'Str. Douăzeci și trei 115', 'Ilfov', '200100'),
('Stanescu', 'Cosmin', 'cosmin.stanescu@email.com', '0745678903', '2000-12-07', 'Str. Douăzeci și patru 120', 'Maramureș', '210100'),
('Ionita', 'Ioana', 'ioana.ionita@email.com', '0756789014', '2002-02-19', 'Str. Douăzeci și cinci 125', 'Mehedinți', '220100'),
('Georgescu', 'Georgiana', 'georgiana.georgescu@email.com', '0767890125', '1998-11-03', 'Str. Douăzeci și șase 130', 'Mureș', '230100'),
('Stoian', 'Sorin', 'sorin.stoian@email.com', '0778901236', '2001-05-14', 'Str. Douăzeci și șapte 135', 'Neamț', '240100'),
('Nica', 'Nica', 'nica.nica@email.com', '0789012347', '2000-09-21', 'Str. Douăzeci și opt 140', 'Olt', '250100'),
('Pavlovic', 'Pavlo', 'pavlo.pavlovic@email.com', '0790123458', '2002-07-08', 'Str. Douăzeci și nouă 145', 'Sălaj', '260100'),
('Radulescu', 'Radula', 'radula.radulescu@email.com', '0701234569', '1999-03-26', 'Str. Treizeci 150', 'Satu Mare', '270100'),
('Serban', 'Serban', 'serban.serban@email.com', '0712345681', '2001-10-17', 'Str. Treizeci și unu 155', 'Suceava', '280100');

-- ========== 60 INSCRIERI ==========
INSERT INTO Inscrieri (id_student, id_curs, data_inscr, status, nota_finala) VALUES
(1, 1, '2024-01-10', 'ACTIVA', NULL),
(2, 2, '2024-01-25', 'ACTIVA', NULL),
(3, 3, '2024-01-05', 'ACTIVA', 8.50),
(1, 3, '2024-01-05', 'ACTIVA', NULL),
(4, 4, '2024-02-15', 'ACTIVA', NULL),
(5, 5, '2024-02-20', 'ACTIVA', 7.80),
(6, 6, '2024-01-30', 'ACTIVA', NULL),
(7, 7, '2024-02-10', 'ACTIVA', 9.20),
(8, 8, '2024-03-01', 'ACTIVA', NULL),
(9, 9, '2024-02-25', 'ACTIVA', 8.90),
(10, 10, '2024-03-10', 'ACTIVA', NULL),
(11, 11, '2024-02-05', 'ACTIVA', 7.50),
(12, 12, '2024-01-20', 'ACTIVA', 8.70),
(13, 13, '2024-03-15', 'ACTIVA', NULL),
(14, 14, '2024-02-28', 'ACTIVA', 9.00),
(15, 15, '2024-01-25', 'ACTIVA', 7.20),
(16, 16, '2024-02-12', 'FINALIZATA', 8.40),
(17, 17, '2024-03-20', 'ACTIVA', NULL),
(18, 18, '2024-01-28', 'ACTIVA', 8.80),
(19, 19, '2024-03-25', 'ACTIVA', NULL),
(20, 20, '2024-02-18', 'ACTIVA', 7.90),
(1, 5, '2024-02-22', 'ACTIVA', NULL),
(2, 6, '2024-02-01', 'ACTIVA', 8.30),
(3, 7, '2024-02-15', 'ACTIVA', NULL),
(4, 8, '2024-03-05', 'ACTIVA', 8.60),
(5, 9, '2024-03-01', 'ACTIVA', NULL),
(6, 10, '2024-03-12', 'ACTIVA', 7.40),
(7, 11, '2024-02-08', 'ACTIVA', NULL),
(8, 12, '2024-01-25', 'ACTIVA', 8.95),
(9, 13, '2024-03-18', 'ACTIVA', NULL),
(10, 14, '2024-02-25', 'ACTIVA', 8.70),
(11, 15, '2024-01-30', 'ACTIVA', 7.10),
(12, 16, '2024-02-14', 'FINALIZATA', 8.50),
(13, 17, '2024-03-22', 'ACTIVA', NULL),
(14, 18, '2024-01-30', 'ACTIVA', 8.60),
(15, 19, '2024-03-28', 'ACTIVA', NULL),
(16, 20, '2024-02-20', 'ACTIVA', 8.00),
(17, 1, '2024-01-15', 'ACTIVA', NULL),
(18, 2, '2024-02-02', 'ACTIVA', 7.60),
(19, 3, '2024-01-08', 'ACTIVA', NULL),
(20, 4, '2024-02-18', 'ACTIVA', 8.20),
(21, 5, '2024-02-24', 'ACTIVA', NULL),
(22, 6, '2024-02-05', 'ACTIVA', 8.40),
(23, 7, '2024-02-17', 'ACTIVA', NULL),
(24, 8, '2024-03-08', 'ACTIVA', 8.80),
(25, 9, '2024-03-03', 'ACTIVA', NULL),
(26, 10, '2024-03-14', 'ACTIVA', 7.50),
(27, 11, '2024-02-10', 'ACTIVA', NULL),
(28, 12, '2024-01-28', 'ACTIVA', 9.10),
(29, 13, '2024-03-20', 'ACTIVA', NULL),
(30, 14, '2024-02-27', 'ACTIVA', 8.80),
(1, 2, '2024-02-05', 'SUSPENDATA', 6.50),
(2, 4, '2024-02-20', 'FINALIZATA', 8.00),
(3, 5, '2024-02-25', 'ACTIVA', NULL),
(4, 6, '2024-02-08', 'ACTIVA', 7.70),
(5, 7, '2024-02-12', 'ACTIVA', NULL),
(6, 8, '2024-03-10', 'ACTIVA', 8.50),
(7, 9, '2024-03-05', 'ACTIVA', NULL),
(8, 10, '2024-03-15', 'ACTIVA', 7.80);
