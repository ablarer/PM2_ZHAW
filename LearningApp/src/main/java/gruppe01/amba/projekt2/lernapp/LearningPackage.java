package gruppe01.amba.projekt2.lernapp;

import java.util.HashMap;

/**
 * The type Learning package.
 *
 * @author Albert Blarer
 * @version 27.04.20
 */

public class LearningPackage {

    private final int id;
    private final String category;
    private final HashMap<Integer, Enemy> enemies;


    /**
     * Instantiates a new Learning package.
     *
     * @param id       the id of the learning package
     * @param enemies  the enemies of the learning package
     * @param category the category of the learning package
     */
    public LearningPackage(int id, HashMap<Integer, Enemy> enemies, String category) {
        this.id = id;
        this.enemies = enemies;
        this.category = category;
    }

    /**
     * Gets id.
     *
     * @return the id of the learning package
     */
    public int getId() {
        return id;
    }


    /**
     * Gets enemies.
     *
     * @return the enemies
     */
    public HashMap<Integer, Enemy> getEnemies() {
        return enemies;
    }

    @Override
    public String toString() {
        return category;
    }
}
