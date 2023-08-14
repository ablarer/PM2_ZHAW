package gruppe01.amba.projekt2.lernapp;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import gruppe01.amba.projekt2.lernapp.LearningApp.ScreenState;
import gruppe01.amba.projekt2.lernapp.LearningApp.EnemyScreenState;
import javafx.beans.property.SimpleStringProperty;

public class PropertyHandler {
    private final Property<ScreenState> stateProperty = new SimpleObjectProperty<>();

    /**
     * Gets state.
     *
     * @return the state
     */
    public final ScreenState getState() {
        return stateProperty.getValue();
    }

    /**
     * Sets state.
     *
     * @param state the state
     */
    public final void setState(ScreenState state) {
        stateProperty.setValue(state);
    }

    /**
     * State property property.
     *
     * @return the property
     */
    public Property<ScreenState> stateProperty() {
        return stateProperty;
    }

    private final Property<EnemyScreenState> enemyScreenStateProperty = new SimpleObjectProperty<>();

    /**
     * Gets state.
     *
     * @return the state
     */
    public final EnemyScreenState getEnemyScreenState() {
        return enemyScreenStateProperty.getValue();
    }

    /**
     * Sets state.
     *
     * @param state the state
     */
    public final void setEnemyScreenState(EnemyScreenState state) {
        enemyScreenStateProperty.setValue(state);
    }

    /**
     * State property property.
     *
     * @return the property
     */
    public Property<EnemyScreenState> enemyScreenStateProperty() {
        return enemyScreenStateProperty;
    }


}
