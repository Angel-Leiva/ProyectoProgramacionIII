package Sistema.presentation.prescribir;

import Sistema.presentation.prescribir.buscarpaciente.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        // Escuchar el botón buscar paciente
        this.view.getBuscaPaciContr().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirBuscarPaciente();
            }
        });
    }

    private void abrirBuscarPaciente() {
        Sistema.presentation.prescribir.buscarpaciente.Model bpModel = new Sistema.presentation.prescribir.buscarpaciente.Model();
        Sistema.presentation.prescribir.buscarpaciente.View bpView = new Sistema.presentation.prescribir.buscarpaciente.View();
        Sistema.presentation.prescribir.buscarpaciente.Controller bpController =
                new Sistema.presentation.prescribir.buscarpaciente.Controller(bpModel, bpView);

        bpView.pack();
        bpView.setLocationRelativeTo(null);
        bpView.setVisible(true);
    }

}
