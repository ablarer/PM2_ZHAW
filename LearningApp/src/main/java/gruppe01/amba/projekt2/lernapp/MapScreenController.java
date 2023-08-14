package gruppe01.amba.projekt2.lernapp;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import gruppe01.amba.projekt2.lernapp.LearningApp.ScreenState;

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
        setImageView();
        if (enemies != null && enemies.size() < imageviewList.size()) {
            for (int i = 0; i < enemies.size(); i++) {
                ImageView imageView = imageviewList.get(i);
                imageView.setId(String.valueOf(enemies.get(i).getId()));
                imageView.setImage(enemies.get(i).getAvatar());
                imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, showEnemyScreenHandler);
                imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, startFightHandler);

            }
            for (Enemy enemy : gameState.getEnemies().values()) {
                if (!enemy.isAlive) {
                    int id = enemy.getId();
                    ImageView imageViewToDisable = imageviewList.get(id);
                    imageViewToDisable.removeEventHandler(MouseEvent.MOUSE_CLICKED, showEnemyScreenHandler);
                    imageViewToDisable.removeEventHandler(MouseEvent.MOUSE_CLICKED, startFightHandler);
                }
            }
        }
    }

    /**
     * Method to populate the map screen with the enemies avatars.
     */
    @FXML
    public void populateEnemies() {
        createEnemyButtons(gameState.getEnemies());
    }

    /**
     * Fills the imageViews in an arrayList.
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