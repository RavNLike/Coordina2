CREATE TABLE Professor(
nif VARCHAR (8) PRIMARY KEY,
nom VARCHAR (30) NOT NULL,
cognoms VARCHAR (50) NOT NULL,
correu_upv VARCHAR (25) NOT NULL
);

CREATE TABLE AlumneTutor(
nif VARCHAR (8) PRIMARY KEY,
nom VARCHAR (30) NOT NULL,
cognoms VARCHAR (50) NOT NULL,
correu_upv VARCHAR (25) NOT NULL
);

CREATE TABLE Tutelat(
nif VARCHAR (8) PRIMARY KEY,
nom VARCHAR (30) NOT NULL,
cognoms VARCHAR (50) NOT NULL,
correu_upv VARCHAR (25) NOT NULL,
correu_personal VARCHAR (25) NOT NULL,
grup_patu VARCHAR (3),
grup_matricula VARCHAR (6),
mobil VARCHAR (9),
FOREIGN KEY (grup_patu) references Grups(grup)
);

CREATE TABLE Grup(
grup VARCHAR(3),
professor VARCHAR(8),
alumne1 VARCHAR(8),
alumne2 VARCHAR(8),
PRIMARY KEY (grup, professor, alumne1),
FOREIGN KEY (professor) references Professors(nif),
FOREIGN KEY (alumne1) references Tutors(nif),
FOREIGN KEY (alumne2) references Tutors(nif)
);
