package Sistema.presentation.acercaDe;

import Sistema.logic.Service;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    View view;
    Model model;
    Service service;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        this.service = Service.instance();

        view.setController(this);
        view.setModel(model);

    }
}
