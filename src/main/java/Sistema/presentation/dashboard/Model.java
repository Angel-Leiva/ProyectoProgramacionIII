package Sistema.presentation.dashboard;


import Sistema.logic.Receta;
import Sistema.logic.Medicamento;
import Sistema.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel{
    private Medicamento current;
    private List<Medicamento> list;
    private List<Receta> listaReceta;


    public static final String CURRENT = "current";
    public static final String LIST = "list";
    public static final String LISTAREC = "listaReceta";

    public Model() {
        current = new Medicamento();
        list = new ArrayList<>();
        listaReceta = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
        firePropertyChange(LISTAREC);
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

    public List<Receta> getListaReceta() { return listaReceta; }

    public void setListaReceta(List<Receta> listaReceta) {
        this.listaReceta = listaReceta;
        firePropertyChange(LISTAREC);
    }

}
