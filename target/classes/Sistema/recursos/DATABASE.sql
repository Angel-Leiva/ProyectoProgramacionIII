CREATE DATABASE IF NOT EXISTS SistemaMedico;
USE SistemaMedico;

CREATE TABLE Administrador (
                               id VARCHAR(50) PRIMARY KEY,
                               usuario VARCHAR(100) NOT NULL UNIQUE,
                               contrasena VARCHAR(100) NOT NULL
);

CREATE TABLE Medico (
                        id VARCHAR(50) PRIMARY KEY,
                        usuario VARCHAR(100) NOT NULL UNIQUE,
                        nombre VARCHAR(255) NOT NULL,
                        especialidad VARCHAR(255)
);

CREATE TABLE Farmaceuta (
                            id VARCHAR(50) PRIMARY KEY,
                            usuario VARCHAR(100) NOT NULL UNIQUE,
                            nombre VARCHAR(255) NOT NULL
);

CREATE TABLE Paciente (
                          id VARCHAR(50) PRIMARY KEY,
                          nombre VARCHAR(255) NOT NULL,
                          fecha_nacimiento DATE,
                          telefono VARCHAR(20)
);

CREATE TABLE Medicamento (
                             codigo VARCHAR(50) PRIMARY KEY,
                             nombre VARCHAR(255) NOT NULL,
                             presentacion VARCHAR(255)
);

CREATE TABLE Receta (
                        numero_receta INT AUTO_INCREMENT PRIMARY KEY,
                        fecha DATE NOT NULL,
                        id_medico VARCHAR(50) NOT NULL,
                        id_paciente VARCHAR(50) NOT NULL,
                        entregada BOOLEAN DEFAULT FALSE,
                        FOREIGN KEY (id_medico) REFERENCES Medico(id),
                        FOREIGN KEY (id_paciente) REFERENCES Paciente(id)
);

CREATE TABLE Receta_Medicamento (
                                    numero_receta INT NOT NULL,
                                    codigo_medicamento VARCHAR(50) NOT NULL,
                                    cantidad INT NOT NULL,
                                    PRIMARY KEY (numero_receta, codigo_medicamento),
                                    FOREIGN KEY (numero_receta) REFERENCES Receta(numero_receta),
                                    FOREIGN KEY (codigo_medicamento) REFERENCES Medicamento(codigo)
);