package amba;

import javafx.scene.image.Image;
import java.util.List;
import java.util.Map;

/**
 * This class represents an enemy in this game
 *
 * @author Marc Meyer
 * @version 11.5.2020
 */
public class Enemy extends Character implements Renderable {
    private String description;
    private List<Question> questions;
    private Question currentQuestion;
    private int strength;
    private Map<String, Image> imageSet;
    private int currentQuestionCounter;
    private String message;

    /**
     * Creates an Enemy object
     *
     * @param strength      The strength points the enemy will have initially. Currently not used in the prototype, but will be used in the app later on.
     * @param description   The description for the enemy to be displayed in front end. Currently not used in the prototype, but will be used in the app later on.
     * @param id            A unique id for the enemy
     * @param name          The name of the enemy
     * @param avatarImages  A map which needs to have the keys "alive" and "dead" and corresponding avatar images as values
     * @param questions     A list of questions the enemy will "ask" during the game
     */
    public Enemy(int strength, String description, int id, String name, Map<String, Image> avatarImages, List<Question> questions) {
        super(id, 3, avatarImages.get("alive"), name);
        this.description = description;
        this.questions = questions;
        currentQuestionCounter = 0;
        currentQuestion = questions.get(currentQuestionCounter);
        this.imageSet = avatarImages;
        this.strength = strength;
    }

    /**
     * Sets the avatar image of the enemy depending if he is alive or dead
     */
    public void render() {
        if(isAlive) avatar = imageSet.get("alive");
        else avatar = imageSet.get("dead");
    }

    /**
     * Returns the current question of the enemy
     * @return  The question
     */
    public Question getCurrentQuestion() { return currentQuestion; }

    /**
     * Returns the next question of the enemy (changes current question also to next question)
     * @return  The question
     */
    public Question getNextQuestion() {
        setCurrentQuestionToNextQuestion();
        Question nextQuestion = currentQuestion;
        return nextQuestion;
    }

    private void setCurrentQuestionToNextQuestion() {
        increaseCurrentQuestionCounter();
        currentQuestion = questions.get(currentQuestionCounter);
    }

    private void increaseCurrentQuestionCounter() {
        currentQuestionCounter = ++currentQuestionCounter % questions.size();
    }

    /**
     * Method to set the current message of the enemy to the value of the passed enum constant from type Message_Type.
     * Can be used in combination with getMessage() to display messages of the monster (like "You have defeated me" or other game relevant messages) in the front end
     * @param messageType   The MessageType constant which has a String as value (see class MessageType)
     * @return              An empty String, which is a mistake, but there is already code freeze, so this cannot be refactored now :-)
     */
    public String talk(MessageType messageType) {
        message = messageType.getMessage();
        return "";
    }

    /**
     * The current message of the enemy.
     * Can be used in combination with talk(MessageType messageType) to display messages of the monster (like "You have defeated me" or other game relevant messages) in the front end
     * @return  The message as String
     */
    public String getMessage() {
        return message;
    }
}
