package Sistema.logic;

public class RecetaMedicamento {
    private Medicamento medicamento;
    private int cantidad;
    private int dias;
    private String indicaciones;

    public RecetaMedicamento(Medicamento medicamento, int cantidad, int dias, String indicaciones) {
        this.medicamento = medicamento;
        this.cantidad = cantidad;
        this.dias = dias;
        this.indicaciones = indicaciones;
    }

    public Medicamento getMedicamento() { return medicamento; }
    public int getCantidad() { return cantidad; }
    public int getDias() { return dias; }
    public String getIndicaciones() { return indicaciones; }

    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public void setDias(int dias) { this.dias = dias; }
    public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }
}
