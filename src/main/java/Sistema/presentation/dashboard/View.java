package Sistema.presentation.dashboard;

import Sistema.logic.Medicamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;
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
        int anioInicio = 2025;

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
        doubleGoodButton.addActionListener(e->controller.todasLasComprasMedicamento());
        badButton.addActionListener(e -> {
            int row = listaMedicamentos.getSelectedRow();
            if (row >= 0) {
                try {
                    // Obtener los datos necesarios de las columnas
                    int numeroReceta = (int) listaMedicamentos.getValueAt(row, 3); // Columna "Receta ID"
                    String codigoMedicamento = (String) listaMedicamentos.getValueAt(row, 4); // Columna "Med. Código"

                    // Llamar al controller con los parámetros correctos
                    controller.borrarRecetaMedicamento(numeroReceta, codigoMedicamento);
                } catch (ClassCastException ex) {
                    JOptionPane.showMessageDialog(panel1,
                            "Error en el formato de datos de la tabla. Asegúrese de seleccionar una fila válida.",
                            "Error de Datos",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel1,
                            "Error al eliminar: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(panel1,
                        "Seleccione una prescripción para borrarla",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        doubleBadButton.addActionListener(e -> {
            int rowCount = listaMedicamentos.getRowCount();
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(panel1,
                        "No hay prescripciones mostradas.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    panel1,
                    "¿Seguro que deseas eliminar todas las prescripciones visibles en la tabla?",
                    "Confirmar la eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) return;

            // Recopilar todos los elementos a borrar ANTES de empezar a borrarlos
            // para evitar problemas con la tabla que cambia al refrescar.
            List<Object[]> itemsToDelete = new ArrayList<>();
            for (int i = 0; i < rowCount; i++) {
                try {
                    int numeroReceta = (int) listaMedicamentos.getValueAt(i, 3);
                    String codigoMedicamento = (String) listaMedicamentos.getValueAt(i, 4);
                    itemsToDelete.add(new Object[]{numeroReceta, codigoMedicamento});
                } catch (ClassCastException ex) {
                    JOptionPane.showMessageDialog(panel1,
                            "Error en el formato de datos de la fila " + (i + 1) + ". No se puede eliminar por datos incorrectos.",
                            "Error de Datos",
                            JOptionPane.ERROR_MESSAGE);
                    return; // Detener el proceso si hay datos mal formados
                }
            }

            try {
                for (Object[] item : itemsToDelete) {
                    int numeroReceta = (int) item[0];
                    String codigoMedicamento = (String) item[1];
                    controller.borrarRecetaMedicamento(numeroReceta, codigoMedicamento);
                }

                JOptionPane.showMessageDialog(panel1,
                        "Todas las prescripciones se eliminaron correctamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

                controller.refrescar(); // Refrescar la tabla después de borrar todo

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel1,
                        "Error al eliminar todas las prescripciones: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });


    }

    public void initComboMedicamentos(List<Medicamento> medicamentos) {
        comboBoxMedicamentos.removeAllItems(); // Limpia

        for (Medicamento m : medicamentos) {
            comboBoxMedicamentos.addItem(m.getNombre());
        }
    }

    public JTable getListaMedicamentos() {
        return listaMedicamentos;
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

            case Sistema.presentation.dashboard.Model.LIST:
                initComboMedicamentos(model.getList());
                break;

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

    public void actualizarTablaMedicamento(String nombreMed, Map<LocalDate, Long> conteoPorDia) {
        DefaultTableModel tableModel = new DefaultTableModel();

        // Definir columnas
        tableModel.addColumn("Medicamento");
        tableModel.addColumn("Fecha");
        tableModel.addColumn("Cantidad Recetada");

        // Formato de fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd");

        // Agregar filas con los datos del mapa
        conteoPorDia.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // ordenar por fecha
                .forEach(entry -> {
                    LocalDate fecha = entry.getKey();
                    Long cantidad = entry.getValue();
                    tableModel.addRow(new Object[]{nombreMed, fecha.format(formatter), cantidad});

                });

        // Establecer el nuevo modelo en la tabla
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
