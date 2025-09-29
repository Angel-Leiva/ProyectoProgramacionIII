package Sistema.presentation.despacho.obcionDespacho;

import Sistema.logic.Receta;
import Sistema.presentation.despacho.Controller;

import javax.swing.*;

public class ControllerObcion {
    private View view;
    private Model model;
    private Controller mainController;

    public ControllerObcion(View view, Model model, Controller mainController) {
        this.view = view;
        this.model = model;
        this.mainController = mainController;

        initListeners();
        cargarEstadoActual();
    }

    private void initListeners() {
        view.getGuardar().addActionListener(e -> {
            String estadoSeleccionado = (String) view.getEstadoReceta().getSelectedItem();
            Receta receta = model.getReceta();

            if (estadoSeleccionado.equals("Pendiente")) receta.setEstado('P');
            if (estadoSeleccionado.equals("Lista")) receta.setEstado('L');
            if (estadoSeleccionado.equals("Entregada")) receta.setEstado('E');
            if (estadoSeleccionado.equals("Confeccionada")) receta.setEstado('C');

            JOptionPane.showMessageDialog(view, "Estado actualizado");
            mainController.refrescar();
            view.dispose();
        });

        view.getCancelar().addActionListener(e -> view.dispose());
    }

    private void cargarEstadoActual() {
        Receta receta = model.getReceta();
        if (receta != null) {
            switch (receta.getEstado()) {
                case 'P': view.getEstadoReceta().setSelectedItem("Pendiente"); break;
                case 'L': view.getEstadoReceta().setSelectedItem("Lista"); break;
                case 'E': view.getEstadoReceta().setSelectedItem("Entregada"); break;
                case 'C': view.getEstadoReceta().setSelectedItem("Confeccionada"); break;
            }
        }
    }
}
