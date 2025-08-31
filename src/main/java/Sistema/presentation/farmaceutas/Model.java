package Sistema.presentation.farmaceutas;

import Sistema.logic.Farmaceuta;
import Sistema.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    private Farmaceuta current;
    private List<Farmaceuta> list;

    public static final String CURRENT = "current";
    public static final String LIST = "list";

    public Model() {
        current = new Farmaceuta();
        list = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
    }

    public Farmaceuta getCurrent() { return current; }
    public void setCurrent(Farmaceuta current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public List<Farmaceuta> getList() { return list; }
    public void setList(List<Farmaceuta> list) {
        this.list = list;
        firePropertyChange(LIST);
    }
}
