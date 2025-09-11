package Sistema.presentation.Historico;

import Sistema.logic.Receta;
import Sistema.logic.RecetaMedicamento;
import Sistema.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    private Receta current;
    private List<Receta> list;
    private List<RecetaMedicamento> listaMedic;

    public static final String CURRENT = "current";
    public static final String LIST = "list";
    public static final String LISTAMED = "listaMedic";

    public Model() {
        current = new Receta();
        list = new ArrayList<>();
        listaMedic = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
        firePropertyChange(LISTAMED);
    }

    public Receta getCurrent() { return current; }
    public void setCurrent(Receta current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public List<Receta> getList() { return list; }

    public void setList(List<Receta> list) {
        this.list = list;
        firePropertyChange(LIST);
    }

    public List<RecetaMedicamento> getListaMedic() { return listaMedic; }

    public void setListaMedic(List<RecetaMedicamento> listaMedic) {
        this.listaMedic = listaMedic;
        firePropertyChange(LISTAMED);
    }
}
