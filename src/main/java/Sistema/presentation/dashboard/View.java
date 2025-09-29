package Sistema.presentation.dashboard;

import Sistema.logic.Medicamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;


public class View implements PropertyChangeListener{
    private JComboBox desdeAnioComboB;
    private JComboBox hastaAnioComboB;
    private JComboBox desdeFechaComboB;
    private JComboBox hastaFechaComboB;
    private JTable listaMedicamentos;
    private JComboBox comboBoxMedicamentos;
    private JButton goodButton;
    private JButton doubleGoodButton;
    private JButton badButton;
    private JButton doubleBadButton;
    private JPanel panel1;
    public JPanel panelPieReceta;
    public JPanel panelGraficoMedic;

    private Controller controller;
    private Model model;

    public View() {
        listaMedicamentos.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Medicamento", (String)desdeAnioComboB.getSelectedItem()+(String)desdeFechaComboB.getSelectedItem(),
                        (String)hastaAnioComboB.getSelectedItem()+(String)hastaFechaComboB.getSelectedItem()}
        ));


        int anioActual = 2030;
        int anioInicio = 2020;

        for (int i = anioInicio; i <= anioActual; i++) {
            desdeAnioComboB.addItem(i);
            hastaAnioComboB.addItem(i);
        }

        String[] meses = {
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        };

        for (String mes : meses) {
            for (int dia = 1; dia <= 31; dia++) {
                String entrada = dia + " - " + mes;
                desdeFechaComboB.addItem(entrada);
                hastaFechaComboB.addItem(entrada);
            }
        }

        goodButton.addActionListener(e -> controller.filtrarComprasMedicamento());

    }

    public void initComboMedicamentos(List<Medicamento> medicamentos) {
        comboBoxMedicamentos.removeAllItems(); // Limpiar por si acaso

        for (Medicamento m : medicamentos) {
            comboBoxMedicamentos.addItem(m.getNombre());
        }
    }


    public JPanel getPanel() { return panel1; }

    public void setController(Controller controller) { this.controller = controller; }
    public void setModel(Sistema.presentation.dashboard.Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Sistema.presentation.dashboard.Model.CURRENT:
                Medicamento m = model.getCurrent();
                break;

//            case Sistema.presentation.dashboard.Model.LIST:
//                listaMedicamentos.setModel(new DefaultTableModel(
//                        model.getList().stream()
//                                .map(med -> new Object[]{
//                                        med.getNombre(),
//
//
//
//                                })
//                                .toArray(Object[][]::new),
//                        new String[]{"Medicamento", (String)desdeAnioComboB.getSelectedItem()+(String)desdeFechaComboB.getSelectedItem(),
//                                (String)hastaAnioComboB.getSelectedItem()+(String)hastaFechaComboB.getSelectedItem()}
//                ));
//                break;

        }
    }

    public String getMedicamentoSeleccionado() {
        return (String) comboBoxMedicamentos.getSelectedItem();
    }

    public int getDesdeAnioSeleccionado() {
        return (int) desdeAnioComboB.getSelectedItem();
    }

    public int getHastaAnioSeleccionado() {
        return (int) hastaAnioComboB.getSelectedItem();
    }

    public String getDesdeFechaSeleccionada() {
        return (String) desdeFechaComboB.getSelectedItem(); // "10 - Octubre"
    }

    public String getHastaFechaSeleccionada() {
        return (String) hastaFechaComboB.getSelectedItem();
    }

    public LocalDate convertirADate(String diaMes, int anio) {
        String[] partes = diaMes.split(" - ");
        int dia = Integer.parseInt(partes[0].trim());
        String mesNombre = partes[1].trim();

        Month mes = mesesMap.getOrDefault(mesNombre, Month.JANUARY); // default por si acaso
        return LocalDate.of(anio, mes, dia);
    }

    public void actualizarTablaMedicamento(String nombreMed, long cantidad) {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Medicamento");
        tableModel.addColumn("Cantidad Comprada");

        tableModel.addRow(new Object[]{nombreMed, cantidad});
        listaMedicamentos.setModel(tableModel);
    }

    private static final Map<String, Month> mesesMap = Map.ofEntries(
            Map.entry("Enero", Month.JANUARY),
            Map.entry("Febrero", Month.FEBRUARY),
            Map.entry("Marzo", Month.MARCH),
            Map.entry("Abril", Month.APRIL),
            Map.entry("Mayo", Month.MAY),
            Map.entry("Junio", Month.JUNE),
            Map.entry("Julio", Month.JULY),
            Map.entry("Agosto", Month.AUGUST),
            Map.entry("Septiembre", Month.SEPTEMBER),
            Map.entry("Octubre", Month.OCTOBER),
            Map.entry("Noviembre", Month.NOVEMBER),
            Map.entry("Diciembre", Month.DECEMBER)
    );
}
