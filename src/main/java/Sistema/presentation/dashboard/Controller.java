package Sistema.presentation.dashboard;

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

//    public void buscar(String filtro) { //Busca por nombre o id
//        List<Receta> result = service.recetaAll().stream()
//                .filter(r -> r.getPaciente().getNombre().toLowerCase().contains(filtro.toLowerCase())
//                        || r.getPaciente().getId().contains(filtro))
//                .collect(Collectors.toList());
//        model.setList(result);
//    }

//    public void seleccionarReceta(Receta receta) {
//        model.setCurrent(receta); // Esto debería activar el propertyChange con Model.CURRENT
//        model.setListaMedic(receta.getMedicamentos()); // <-- Asegúrate que esto exista
//    }



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
            String desdeDiaMes = view.getDesdeFechaSeleccionada(); // Ej: "10 - Octubre"
            String hastaDiaMes = view.getHastaFechaSeleccionada();

            // Convertir día-mes-año a LocalDate
            LocalDate fechaInicio = view.convertirADate(desdeDiaMes, desdeAnio);
            LocalDate fechaFin = view.convertirADate(hastaDiaMes, hastaAnio);

            // Obtener recetas entre esas fechas
            List<Receta> recetas = service.recetaAll().stream()
                    .filter(r -> {
                        LocalDate f = r.getFechaRetiro();
                        return !f.isBefore(fechaInicio) && !f.isAfter(fechaFin);
                    })
                    .collect(Collectors.toList());

            // Contar cuántas veces aparece el medicamento
            long conteo = recetas.stream()
                    .flatMap(r -> r.getMedicamentos().stream())
                    .filter(rm -> rm.getMedicamento().getNombre().equalsIgnoreCase(nombreMed))
                    .count();

            // Mostrar en tabla
            view.actualizarTablaMedicamento(nombreMed, conteo);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al filtrar: " + ex.getMessage());
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

}

