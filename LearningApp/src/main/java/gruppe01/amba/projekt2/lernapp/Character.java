package gruppe01.amba.projekt2.lernapp;

import javafx.scene.image.Image;

/**
 * Super class for characters in the game like Enemy or Player
 * Holds the data and provides the methods needed by all characters
 *
 *  @author Marc Meyer
 *  @version 11.5.2020
 */
public abstract class Character {
    protected int healthPoints;
    private final int ID;
    protected Image avatar;
    protected String name;
    protected boolean isAlive;

    /**
     * Constructor for abstract class Character
     *
     * @param id            a unique id for the character
     * @param healthPoints  the initial health points of the character
     * @param avatar        An avatar image for the character to be displayed in frontend
     * @param name          Name for the enemy
     */
    public Character(int id, int healthPoints, Image avatar, String name) {
        this.ID = id;
        this.healthPoints = healthPoints;
        this.avatar = avatar;
        this.name = name;
        isAlive = true;
    }

    /**
     *  Puts the character in a dead state. Method is isCharacterAlive() will return false after calling this method
     */
    public void die() {
        isAlive = false;
    }

    /**
     *  Indicates if the character is alive or not
     * @return true if the character is currently alive, false otherwise
     */
    public boolean isCharacterAlive() {
        return isAlive;
    }

    /**
     * Method to set the health points of a character at any time. Can be used to reward a player for correct answers or to refill his health points when neccessary
     * @param health
     */
    public void setHealthPoints(int health) {
        healthPoints = health;
    }

    /**
     * Returns the health points of the character. Can be used to find out if a character is defeated in a fight
     * @return  The current health points
     */
    public int getHealthPoints() {
        return healthPoints;
    }

    /**
     * Returns the unique id of the character
     * @return  The id as int
     */
    public int getId() {
        return ID;
    }

    /**
     * @return The name of the character
     */
    public String getName() {
        return name;
    }

    /**
     * @return The current avatar image of the character
     */
    public Image getAvatar() {
        return avatar;
    }

    /**
     * Increases the health points of the character by one
     */
    public void increaseHealth() {
        healthPoints++;
    }

    /**
     * Decreases the health points of the character by one
     */
    public void decreasehealth() {
        healthPoints--;
        if(healthPoints <= 0) die();
    }
 }
