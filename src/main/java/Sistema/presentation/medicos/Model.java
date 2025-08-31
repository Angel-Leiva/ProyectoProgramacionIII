package Sistema.presentation.medicos;

import Sistema.logic.Medico;
import Sistema.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    private Medico current;       // médico que estoy editando o consultando
    private List<Medico> list;    // lista de médicos (para la tabla)

    public static final String CURRENT = "current";
    public static final String LIST = "list";

    public Model() {
        current = new Medico();
        list = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
    }

    // ================= MÉDICO ACTUAL =================
    public Medico getCurrent() {
        return current;
    }

    public void setCurrent(Medico current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    // ================= LISTA DE MÉDICOS =================
    public List<Medico> getList() {
        return list;
    }

    public void setList(List<Medico> list) {
        this.list = list;
        firePropertyChange(LIST);
    }
}

