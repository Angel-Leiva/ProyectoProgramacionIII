package personas.presentation.personas;

import personas.logic.Persona;
import personas.presentation.personas.AbstractModel;
import java.beans.PropertyChangeListener;

public class Model extends AbstractModel {
    Persona current;

    public static final String CURRENT = "current";

    public Model() {
        current = new Persona();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
    }

    public Persona getCurrent() {
        return current;
    }

    public void setCurrent(Persona current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

}
