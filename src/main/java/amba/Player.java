package amba;

import javafx.scene.image.Image;

/**
 * class for a future extension of the app to create a individual player
 * The type Player.
 *
 * @author Albert Blarer
 * @version 06.05.2020
 */
public class Player extends Character {
    /**
     * Instantiates a new Player.
     *
     * @param id           the id of the player
     * @param healthPoints the health points of the player
     * @param avatar       the avatar of the player
     * @param name         the name of the player
     */
    public Player(int id, int healthPoints, Image avatar, String name) {
        super(id, healthPoints, avatar, name);

    }

    /**
     * Method to render the player
     */
    private void render(){
    }

    public void riseOfThePhoenix() { isAlive = true; }
}
