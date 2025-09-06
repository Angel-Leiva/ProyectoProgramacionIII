package Sistema.presentation.medicamentos;

import Sistema.logic.Medicamento;
import Sistema.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    private Medicamento current;
    private List<Medicamento> list;

    public static final String CURRENT = "current";
    public static final String LIST = "list";

    public Model() {
        current = new Medicamento();
        list = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
    }

    public Medicamento getCurrent() { return current; }
    public void setCurrent(Medicamento current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public List<Medicamento> getList() { return list; }
    public void setList(List<Medicamento> list) {
        this.list = list;
        firePropertyChange(LIST);
    }
}
