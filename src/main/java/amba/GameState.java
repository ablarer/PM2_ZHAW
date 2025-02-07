package amba;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static amba.LearningApp.ScreenState.*;

/**
 * The GameState class is the interface between the package classes and the UI. It manages the current enemy, as well as
 * the associated current learning packages. It also has all the methods that provide the UI with the necessary
 * information from the packages and also has the logic to evaluate a question for correctness.
 *
 * @author Basil Ermatinger
 * @version 2020-05-10
 */
public class GameState {

    private PackageHandler packageHandler;
    private PackageReader packageReader;
    private LearningPackage currentLearningPackage;
    private Enemy currentEnemy;
    private Player player;
    private PropertyHandler propertyHandler;
    private final int DEFAULT_PLAYER_HEALTH = 3;

    /** Creates a GameState object and instanciates the initial objects */
    public GameState(PropertyHandler propertyHandler) {
        try {
            packageReader = new PackageReader();
            packageHandler = new PackageHandler(packageReader);
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
        this.propertyHandler = propertyHandler;
        currentEnemy = null;
        currentLearningPackage = null;
        player = new Player(1, DEFAULT_PLAYER_HEALTH, null, "Player");
    }

    /** @return Returns the enemy that is currently being fought */
    public Enemy getCurrentEnemy() { return currentEnemy; }

    /** @return all learning packages that are managed by the package handler. */
    public HashMap<Integer, LearningPackage> getLearningPackages() { return packageHandler.getLearningPackages(); }

    /** @return current question as a String from current enemy */
    public String getCurrentQuestionFromEnemy() { return currentEnemy.getCurrentQuestion().getQuestion(); }

    /** @return a HashMap of all enemies from the current learning package */
    public HashMap<Integer, Enemy> getEnemies() {
        return currentLearningPackage.getEnemies();
    }

    /** @return an ArrayList of all answers from the current question */
    public ArrayList<String> getAnswersOfCurrentQuestion() { return currentEnemy.getCurrentQuestion().getAnswers(); }

    /** @return the health points that the player owns at the current time */
    public int getPlayerHealthPoints() { return player.getHealthPoints(); }

    /**
     * Fetches a learning package from the package handler by its ID and hands it over.
     *
     * @param id ID of requested learning package
     * @return requested learning package
     */
    public LearningPackage startLearningPackage(int id) {
        currentLearningPackage = packageHandler.getPackage(id);
        return currentLearningPackage;
    }

    /**
     * Lets the packageHandler add a package to the package library. This is done by passing the file to be parsed to
     * the PackageReader, which prepares the file and returns it as a finished LearningPackage. The returned
     * learning package is then transferred to the PackageHandler.
     *
     * @param filepath the path of the file which has to be parsed into a learning package.
     * @throws FileNotFoundException
     */
    public void importLearningPackage(String filepath) {
            packageHandler.addLearningPackage(packageReader.createLearningPackage(filepath));
    }

    /**
     * Lets start a fight by fetching the requested enemy from the learning package as well as the first question of
     * this enemy. It then tells the controller via the property handler to load the enemy screen and to update it.
     *
     * @param id ID of the enemy to be controlled
     */
    public void startFight(int id) {
        player.setHealthPoints(DEFAULT_PLAYER_HEALTH);
        player.riseOfThePhoenix();
        currentEnemy = currentLearningPackage.getEnemies().get(id);
        currentEnemy.talk(MessageType.WELCOME_MESSAGE);
        propertyHandler.setState(ENEMY_SCREEN_STATE);
        propertyHandler.setEnemyScreenState(LearningApp.EnemyScreenState.CLEAR_QUESTIONS);
        propertyHandler.setEnemyScreenState(LearningApp.EnemyScreenState.NEXT_QUESTION);
    }

    /**
     * Checks wether the answer to the question asked is correct. If the answer is correct, one health point is taken
     * from the enemy, otherwise from the player.
     * Afterwards it is checked whether the enemy or the player is still alive. If one of the two is dead, the
     * corresponding message is issued and the fight ends. Also, if the enemy is dead, the system checks if there are
     * still living enemies. If they are all dead, a message will be displayed.
     * If both (player and enemy) are still alive, the fight is continued with the next question.
     *
     * @param answer the answer to be checked for correctness
     */
    public void checkAnswer(String answer) {
        if(currentEnemy.getCurrentQuestion().isAnswerCorrect(answer)) {
            currentEnemy.decreaseHealth();
            currentEnemy.render();
        }
        else {
            player.decreaseHealth();
        }

        if(!currentEnemy.isCharacterAlive()) {
            if(allEnemiesDead()) currentEnemy.talk(MessageType.ALL_ENEMIES_DEAD);
            else currentEnemy.talk(MessageType.LOOSE_MESSAGE);
            propertyHandler.setEnemyScreenState(LearningApp.EnemyScreenState.NO_QUESTIONS);
        } else if(!player.isCharacterAlive()) {
            currentEnemy.talk(MessageType.WIN_MESSAGE);
            propertyHandler.setEnemyScreenState(LearningApp.EnemyScreenState.NO_QUESTIONS);
        } else {
            currentEnemy.talk(MessageType.WELCOME_MESSAGE);
            currentEnemy.getNextQuestion();
            propertyHandler.setEnemyScreenState(LearningApp.EnemyScreenState.CLEAR_QUESTIONS);
            propertyHandler.setEnemyScreenState(LearningApp.EnemyScreenState.NEXT_QUESTION);
        }
    }

    /**
     * Goes through the list of all enemies of the current learning package and check if they are all dead or not.
     *
     * @return true, if all enemies from the current learning package are dead, otherwise false.
     */
    private boolean allEnemiesDead() {
        boolean allEnemiesDead = true;

        for(Enemy enemy : currentLearningPackage.getEnemies().values()) {
            if(enemy.isCharacterAlive()) allEnemiesDead = false;
        }

        return allEnemiesDead;
    }

}
