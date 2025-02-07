package amba;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import amba.LearningApp.ScreenState;

/**
 * Class MapScreenController.
 *
 * @author Alexander Frank, Marc Meyer
 * @version 09.05.2020
 */
public class MapScreenController {
    @FXML public Button returnButton;
    @FXML public ImageView imageView1;
    @FXML public ImageView imageView2;
    @FXML public ImageView imageView3;
    @FXML public ImageView imageView4;
    @FXML public ImageView imageView5;
    @FXML public ImageView imageView6;
    @FXML public ImageView imageView7;
    @FXML public ImageView imageView8;
    @FXML public ImageView imageView9;
    private GameState gameState;
    private PropertyHandler propertyHandler;
    private ArrayList<ImageView> imageviewList = new ArrayList<>();

    /**
     * Sets property handler.
     *
     * @param propertyHandler the property handler
     */
    public void setPropertyHandler(PropertyHandler propertyHandler) {
        this.propertyHandler = propertyHandler;
    }

    /**
     * Sets game state.
     *
     * @param gameState the game state
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Adds predefined ImageView objects to the imageviewList.
     * This method initializes the imageviewList by adding a specific set of ImageView objects
     * in a particular sequence. It ensures that the list is populated with these ImageView
     * instances, which may be used later for displaying images or interacting with the user interface.
     */
    @FXML
    private void setImageView() {
        imageviewList.add(imageView5);
        imageviewList.add(imageView1);
        imageviewList.add(imageView9);
        imageviewList.add(imageView2);
        imageviewList.add(imageView8);
        imageviewList.add(imageView4);
        imageviewList.add(imageView3);
        imageviewList.add(imageView7);
        imageviewList.add(imageView6);
    }

    /**
     * Back to home.
     */
    @FXML
    private void backToHome() {
        try {
            propertyHandler.setState(ScreenState.WELCOME_SCREEN_STATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates the scene with the enemies. Each imageView object gets one enemy image.
     *
     * @param enemies the enemy.
     */
    @FXML
    private void createEnemyButtons(HashMap<Integer, Enemy> enemies) {
        setImageView(); // Initialisiert `imageviewList` mit den ImageViews

        if (enemies != null && !enemies.isEmpty()) {
            // Erstelle eine zufällige Liste aus den verfügbaren Feind-IDs
            ArrayList<Integer> enemyKeys = new ArrayList<>(enemies.keySet());
            ArrayList<ImageView> randomImageViews = new ArrayList<>(imageviewList);
            java.util.Collections.shuffle(randomImageViews); // ImageViews zufällig sortieren

            // Maximal so viele Feinde wie vorhandene ImageViews anzeigen
            int limit = Math.min(enemyKeys.size(), randomImageViews.size());
            for (int i = 0; i < limit; i++) {
                Integer enemyId = enemyKeys.get(i); // Hole einen Feind basierend auf dessen ID
                Enemy enemy = enemies.get(enemyId); // Holt den Feind

                ImageView imageView = randomImageViews.get(i); // Ordnet eine zufällige `ImageView` diesem Feind zu
                imageView.setId(String.valueOf(enemy.getId())); // Setzt die ID des Feindes
                imageView.setImage(enemy.getAvatar()); // Setzt das Avatarbild des Feindes
                imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, showEnemyScreenHandler); // Hinzufügen der bestehende Eventhandler
                imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, startFightHandler);
            }

            // Falls tote Feinde auf der Karte erscheinen, entferne die Eventhandler
            for (Enemy enemy : gameState.getEnemies().values()) {
                if (!enemy.isAlive) {
                    int id = enemy.getId();
                    ImageView imageViewToDisable = imageviewList.stream()
                            .filter(iv -> iv.getId() != null && iv.getId().equals(String.valueOf(id)))
                            .findFirst()
                            .orElse(null);
                    if (imageViewToDisable != null) {
                        imageViewToDisable.removeEventHandler(MouseEvent.MOUSE_CLICKED, showEnemyScreenHandler);
                        imageViewToDisable.removeEventHandler(MouseEvent.MOUSE_CLICKED, startFightHandler);
                    }
                }
            }
        }
    }

    /**
     * Method to populate the map screen with the enemies avatars.
     */
    @FXML
    public void populateEnemies() {
        // Leere die Karte vor jeder neuen Erstellung
        for (ImageView imageView : imageviewList) {
            imageView.setImage(null);
            imageView.setId(null);
        }
        // Ruf `createEnemyButtons` mit den aktuellen Feinden aus dem GameState auf
        createEnemyButtons(gameState.getEnemies());
    }

    /**
     * The Startfight handler.
     */
    EventHandler<MouseEvent> startFightHandler = new EventHandler<>() {
        @Override
        public void handle(MouseEvent event) {
            gameState.startFight(Integer.parseInt(((ImageView) event.getSource()).getId()));
        }
    };

    /**
     * The Showenemyscreen handler.
     */
    EventHandler<MouseEvent> showEnemyScreenHandler = new EventHandler<>() {
        @Override
        public void handle(MouseEvent event) {
            propertyHandler.setState(ScreenState.ENEMY_SCREEN_STATE);
        }
    };
}