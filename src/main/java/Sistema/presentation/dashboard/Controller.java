package Sistema.presentation.dashboard;

import Sistema.logic.Medico;
import Sistema.logic.Receta;
import Sistema.logic.Medicamento;
import Sistema.logic.Service;

import Sistema.presentation.dashboard.View;
import java.time.LocalDate;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Map;

import java.util.TreeMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

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

       refrescar();

       view.initComboMedicamentos(service.medicamentoAll());

    }

    public void generarGraficoPie() {

        // Crear el dataset
        DefaultPieDataset dataset = new DefaultPieDataset();

        //Datos
        dataset.setValue("CONFECCIONADA ",service.getCantEstadoRecetas('C'));
        dataset.setValue("PROCESO " ,service.getCantEstadoRecetas('P'));
        dataset.setValue("ENTREGADA ",service.getCantEstadoRecetas('E'));
        dataset.setValue("LISTA ",service.getCantEstadoRecetas('L'));

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



    }

    public void refrescar() {
        model.setList(service.medicamentoAll());
        model.setListaReceta(service.recetaAll());
        generarGraficoPie();
        crearGraficoLineaMedicamentos();
    }

    //Para el table model de lista de medicamentos
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
            List<Receta> recetasFiltradas = service.recetaAll().stream()
                    .filter(r -> {
                        LocalDate f = r.getFechaRetiro();
                        return !f.isBefore(fechaInicio) && !f.isAfter(fechaFin);
                    })
                    .collect(Collectors.toList());

            // Map para contar cuántas veces se recetó el medicamento por día
            Map<LocalDate, Long> conteoPorDia = recetasFiltradas.stream()
                    .flatMap(r -> r.getMedicamentos().stream()
                            .filter(rm -> rm.getMedicamento().getNombre().equalsIgnoreCase(nombreMed))
                            .map(rm -> r.getFechaRetiro()))
                    .collect(Collectors.groupingBy(fecha -> fecha, Collectors.counting()));

            // Mostrar en tabla
            view.actualizarTablaMedicamento(nombreMed, conteoPorDia);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al filtrar: " + ex.getMessage());
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

            List<Receta> recetasFiltradas = service.recetaAll().stream()
                    .filter(r -> {
                        LocalDate f = r.getFechaRetiro();
                        return !f.isBefore(fechaInicio) && !f.isAfter(fechaFin);
                    })
                    .collect(Collectors.toList());

            // Agrupar por medicamento y fecha
            Map<String, Map<LocalDate, Long>> conteoPorMedicamentoYFecha = new TreeMap<>();

            for (Receta receta : recetasFiltradas) {
                LocalDate fecha = receta.getFechaRetiro();
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
            JOptionPane.showMessageDialog(null, "Error al mostrar todas las compras: " + ex.getMessage());
        }
    }

    public void crearGraficoLineaMedicamentos() {
        TimeSeries serie = new TimeSeries("Medicamentos Entregados");

        // Obtener todas las recetas
        List<Receta> recetas = service.recetaAll();

        // Agrupar por fecha y contar medicamentos entregados
        Map<LocalDate, Long> conteoPorDia = recetas.stream()
                .collect(Collectors.groupingBy(
                        Receta::getFechaRetiro,
                        TreeMap::new, // mantiene el orden de fechas
                        Collectors.summingLong(r -> r.getMedicamentos().size())
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
    }

    public void borrarRecetaMedicamento(String nombreMedicamento, LocalDate fecha) {
        try {
            service.eliminarRecetaMedicamento(nombreMedicamento, fecha);
            JOptionPane.showMessageDialog(null, "Prescripcion eliminada correctamente");
            refrescar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + ex.getMessage());
        }
    }

}

