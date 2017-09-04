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
grup_patu VARCHAR (3) NOT NULL,
grup_matricula VARCHAR (6),
mobil VARCHAR (9),
FOREIGN KEY (grup_patu) references Grups(grup)
);

CREATE TABLE Grup(
grup VARCHAR(3) PRIMARY KEY,
professor VARCHAR(8) NOT NULL,
alumne1 VARCHAR(8) NOT NULL,
alumne2 VARCHAR(8),
alumne3 VARCHAR(8),
alumne4 VARCHAR(8),
FOREIGN KEY (professor) references Professors(nif),
FOREIGN KEY (alumne1) references Tutors(nif),
FOREIGN KEY (alumne2) references Tutors(nif),
FOREIGN KEY (alumne3) references Tutors(nif),
FOREIGN KEY (alumne4) references Tutors(nif)
);

CREATE TABLE Metainfo(
mail VARCHAR(60) PRIMARY KEY,
pass TEXT NOT NULL,
inicialitzat int NOT NULL);

Insert into Metainfo values('beta.integraetsinf@gmail.com', 'T0bL9jdQ44XHT+/+nnexmg==', 1);
