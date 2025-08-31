package Sistema.presentation.medicos;

import Sistema.logic.Medico;
import Sistema.presentation.AbstractModel;

import java.beans.PropertyChangeListener;

public class Model extends AbstractModel {
    Medico current;

    public static final String CURRENT = "current";

    public Model() {
        current = new Medico();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
    }

    public Medico getCurrent() {
        return current;
    }

    public void setCurrent(Medico current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

}
