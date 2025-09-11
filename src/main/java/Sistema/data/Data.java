package Sistema.data;

import Sistema.logic.*;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private List<Medico> medicos;
    private List<Farmaceuta> farmaceutas;
    private List<Paciente> pacientes;
    private List<Medicamento> medicamentos;
    private List<Administrador> administradores;
    private List<Receta> recetas;

    public Data() {
        //Administradores
        administradores = new ArrayList<>();
        administradores.add(new Administrador("000","admin","admin"));
        administradores.add(new Administrador("001","admin1","admin1"));
        //Medico
        medicos = new ArrayList<>();
        medicos.add(new Medico("101", "101", "Dr. Juan Pérez", "Pediatría"));
        medicos.add(new Medico("102", "102", "Dra. María López", "Cardiología"));
        //Farma
        farmaceutas = new ArrayList<>();
        farmaceutas.add(new Farmaceuta("201", "201", "Ana Ramírez"));
        farmaceutas.add(new Farmaceuta("202", "202", "Carlos Jiménez"));
        //Paciente
        pacientes = new ArrayList<>();
        pacientes.add(new Paciente("301", "Luis Herrera", LocalDate.of(1990, 5, 12), "8888-9999"));
        pacientes.add(new Paciente("302", "Sofía Morales", LocalDate.of(1985, 8, 25), "7777-1234"));
        //Medicamento
        medicamentos = new ArrayList<>();
        medicamentos.add(new Medicamento("ACE-111","Acetaminofen", "100 mg"));
        //Receta
        recetas = new ArrayList<>();
    }

    public List<Medico> getMedicos() {
        return medicos;
    }
    public List<Farmaceuta> getFarmaceutas() {
        return farmaceutas;
    }
    public List<Paciente> getPacientes() {
        return pacientes;
    }
    public List<Medicamento> getMedicamentos() {return medicamentos;}
    public List<Administrador> getAdministradores() {return administradores;}
    public List<Receta> getRecetas() { return recetas; }
}