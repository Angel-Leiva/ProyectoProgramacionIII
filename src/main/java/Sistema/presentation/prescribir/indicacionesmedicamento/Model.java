package Sistema.presentation.prescribir.indicacionesmedicamento;

import Sistema.logic.Medicamento;

public class Model {
    private Medicamento medicamento;
    private int cantidad;
    private int duracionDias;
    private String indicaciones;

    public Model(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public Medicamento getMedicamento() { return medicamento; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public int getDuracionDias() { return duracionDias; }
    public void setDuracionDias(int duracionDias) { this.duracionDias = duracionDias; }

    public String getIndicaciones() { return indicaciones; }
    public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }
}
