package Sistema.presentation.dashboard;

import Sistema.logic.Medico;
import Sistema.logic.Receta;
import Sistema.logic.Medicamento;
import Sistema.logic.Service;

import Sistema.presentation.dashboard.View;
import java.time.LocalDate;
import java.awt.*; // awt.BorderLayout
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Map;
import java.util.TreeMap; // Usado para mantener el orden en los mapas
import java.util.ArrayList; // Para manejar listas vacías en caso de error

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
// import org.jfree.chart.ChartFactory; // Ya importado
// import org.jfree.chart.ChartPanel; // Ya importado
// import org.jfree.chart.JFreeChart; // Ya importado

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Controller {
    View view;
    Model model;
    Service service;

    public Controller(Model model, View view) {
        this.view = view;
        this.model = model;
        this.service = Service.instance();

        view.setController(this);
        view.setModel(model);

        // Envuelve las llamadas iniciales en try-catch
        try {
            refrescar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar datos iniciales del Dashboard: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            // Asegúrate de que los gráficos y tablas no intenten usar datos nulos
            model.setList(new ArrayList<>()); // Limpia lista de medicamentos
            model.setListaReceta(new ArrayList<>()); // Limpia lista de recetas
            // No intentar generar gráficos con datos vacíos o con error
        }

        try {
            view.initComboMedicamentos(service.medicamentoAll()); // Puede lanzar Exception
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar medicamentos para el combo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            view.initComboMedicamentos(new ArrayList<>()); // Inicializa con una lista vacía
        }
    }

    public void generarGraficoPie() {
        // Crear el dataset
        DefaultPieDataset dataset = new DefaultPieDataset();
        try {
            // Datos: Asegúrate de que getCantEstadoRecetas exista y sea robusto
            dataset.setValue("CONFECCIONADA ", service.getCantEstadoRecetas('C'));
            dataset.setValue("PROCESO ", service.getCantEstadoRecetas('P'));
            dataset.setValue("ENTREGADA ", service.getCantEstadoRecetas('E'));
            dataset.setValue("LISTA ", service.getCantEstadoRecetas('L'));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener datos para el gráfico de recetas: " + ex.getMessage(), "Error Gráfico", JOptionPane.ERROR_MESSAGE);
            // Si hay error, el gráfico de pastel se mostrará vacío o con valores 0.
        }

        // Crear el gráfico
        JFreeChart chart = ChartFactory.createPieChart(
                "Recetas",
                dataset,
                true,
                true,
                true
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0} = {1} ({2})",
                new DecimalFormat("0"),
                new DecimalFormat("0.0%")
        ));

        // Insertar el gráfico en un ChartPanel
        ChartPanel chartPanel = new ChartPanel(chart);

        // Limpia el panel y agrega el ChartPanel
        view.panelPieReceta.removeAll();                  // Limpia el JPanel
        view.panelPieReceta.setLayout(new java.awt.BorderLayout()); // Asegura el layout
        view.panelPieReceta.add(chartPanel, java.awt.BorderLayout.CENTER);
        view.panelPieReceta.validate();                   // Revalida para que se muestre
        view.panelPieReceta.repaint();                    // Repinta para asegurar la actualización
    }

    public void refrescar() {
        try {
            model.setList(service.medicamentoAll()); // Puede lanzar Exception
            model.setListaReceta(service.recetaAll()); // Puede lanzar Exception
            generarGraficoPie(); // Este método ya tiene su propio try-catch interno para los datos
            crearGraficoLineaMedicamentos(); // Este método ya tiene su propio try-catch interno para los datos
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al refrescar el Dashboard: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            model.setList(new ArrayList<>());
            model.setListaReceta(new ArrayList<>());
            // No se intenta generar gráficos si falló la carga principal
        }
    }

    // Para el table model de lista de medicamentos
    public void filtrarComprasMedicamento() {
        try {
            // Obtener datos seleccionados de la vista
            String nombreMed = view.getMedicamentoSeleccionado();
            int desdeAnio = view.getDesdeAnioSeleccionado();
            int hastaAnio = view.getHastaAnioSeleccionado();
            String desdeDiaMes = view.getDesdeFechaSeleccionada();
            String hastaDiaMes = view.getHastaFechaSeleccionada();

            // Convertir día-mes-año a LocalDate
            LocalDate fechaInicio = view.convertirADate(desdeDiaMes, desdeAnio);
            LocalDate fechaFin = view.convertirADate(hastaDiaMes, hastaAnio);

            // Filtrar recetas entre esas fechas
            List<Receta> recetasFiltradas = service.recetaAll().stream() // Puede lanzar Exception
                    .filter(r -> {
                        LocalDate f = r.getFecha(); // Asegúrate de que getFecha() devuelve la fecha correcta para filtrar
                        return !f.isBefore(fechaInicio) && !f.isAfter(fechaFin);
                    })
                    .collect(Collectors.toList());

            // Map para contar cuántas veces se recetó el medicamento por día
            Map<LocalDate, Long> conteoPorDia = recetasFiltradas.stream()
                    .flatMap(r -> r.getMedicamentos().stream()
                            .filter(rm -> rm.getMedicamento().getNombre().equalsIgnoreCase(nombreMed))
                            .map(rm -> r.getFecha())) // Asume que RecetaMedicamento tiene getMedicamento().getNombre()
                    .collect(Collectors.groupingBy(fecha -> fecha, Collectors.counting()));

            // Mostrar en tabla
            view.actualizarTablaMedicamento(nombreMed, conteoPorDia);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al filtrar compras de medicamento: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            view.actualizarTablaMedicamento("", new TreeMap<>()); // Limpiar tabla
        }
    }

    public void todasLasComprasMedicamento() {
        try {
            int desdeAnio = view.getDesdeAnioSeleccionado();
            int hastaAnio = view.getHastaAnioSeleccionado();
            String desdeDiaMes = view.getDesdeFechaSeleccionada();
            String hastaDiaMes = view.getHastaFechaSeleccionada();

            LocalDate fechaInicio = view.convertirADate(desdeDiaMes, desdeAnio);
            LocalDate fechaFin = view.convertirADate(hastaDiaMes, hastaAnio);

            List<Receta> recetasFiltradas = service.recetaAll().stream() // Puede lanzar Exception
                    .filter(r -> {
                        LocalDate f = r.getFecha(); // Asegúrate de que getFecha() devuelve la fecha correcta para filtrar
                        return !f.isBefore(fechaInicio) && !f.isAfter(fechaFin);
                    })
                    .collect(Collectors.toList());

            // Agrupar por medicamento y fecha
            Map<String, Map<LocalDate, Long>> conteoPorMedicamentoYFecha = new TreeMap<>();

            for (Receta receta : recetasFiltradas) {
                LocalDate fecha = receta.getFecha();
                receta.getMedicamentos().forEach(rm -> {
                    String nombre = rm.getMedicamento().getNombre();
                    conteoPorMedicamentoYFecha
                            .computeIfAbsent(nombre, k -> new TreeMap<>())
                            .merge(fecha, 1L, Long::sum);
                });
            }

            // Crear modelo de tabla
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Medicamento");
            tableModel.addColumn("Fecha");
            tableModel.addColumn("Cantidad Comprada");

            conteoPorMedicamentoYFecha.forEach((medicamento, fechas) -> {
                fechas.forEach((fecha, cantidad) -> {
                    tableModel.addRow(new Object[]{medicamento, fecha.toString(), cantidad});
                });
            });

            view.getListaMedicamentos().setModel(tableModel);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al mostrar todas las compras: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            view.getListaMedicamentos().setModel(new DefaultTableModel()); // Limpiar tabla
        }
    }

    public void crearGraficoLineaMedicamentos() {
        TimeSeries serie = new TimeSeries("Medicamentos Entregados");

        List<Receta> recetas = new ArrayList<>();
        try {
            // Obtener todas las recetas
            recetas = service.recetaAll(); // Puede lanzar Exception
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener recetas para el gráfico de línea: " + ex.getMessage(), "Error Gráfico", JOptionPane.ERROR_MESSAGE);
            // La lista 'recetas' permanecerá vacía, lo que hará que el gráfico se muestre sin datos
        }


        // Agrupar por fecha y contar medicamentos entregados
        Map<LocalDate, Long> conteoPorDia = recetas.stream()
                .collect(Collectors.groupingBy(
                        Receta::getFecha, // Asume que getFecha() es la fecha relevante para el gráfico
                        TreeMap::new, // mantiene el orden de fechas
                        Collectors.summingLong(r -> r.getMedicamentos() != null ? r.getMedicamentos().size() : 0) // Considera nulos
                ));

        // Llenar la serie con los datos
        for (Map.Entry<LocalDate, Long> entry : conteoPorDia.entrySet()) {
            LocalDate fecha = entry.getKey();
            long cantidad = entry.getValue();
            serie.add(new Day(fecha.getDayOfMonth(), fecha.getMonthValue(), fecha.getYear()), cantidad);
        }

        // Crear dataset
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(serie);

        // Crear el gráfico
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Medicamentos Entregados por Día",
                "Fecha",
                "Cantidad",
                dataset,
                false,  // leyenda
                true,   // tooltips
                false   // URLs
        );

        // Crear el ChartPanel
        ChartPanel chartPanel = new ChartPanel(chart);

        // Insertarlo en el panel (panelGraficoMedic)
        view.panelGraficoMedic.removeAll();
        view.panelGraficoMedic.setLayout(new java.awt.BorderLayout());
        view.panelGraficoMedic.add(chartPanel, java.awt.BorderLayout.CENTER);
        view.panelGraficoMedic.validate();
        view.panelGraficoMedic.repaint(); // Repinta para asegurar la actualización
    }

    public void borrarRecetaMedicamento(int numeroReceta, String codigoMedicamento) { // Parámetros ajustados
        try {
            // NOTA: Los valores (111, "") son placeholders, deben venir de la UI o contexto
            service.eliminarRecetaMedicamento(numeroReceta, codigoMedicamento);
            JOptionPane.showMessageDialog(null, "Prescripción/Medicamento de receta eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            refrescar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el medicamento de la receta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}