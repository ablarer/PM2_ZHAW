package gruppe01.amba.projekt2.lernapp;

import javafx.scene.image.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The ImageHandler class manages the images, specifically the avatars of the enemies. The main task of this class is
 * to write the images from the respective resource folder into a HashMap when starting the program. This hashmap
 * contains an array of an image pair. An image pair consists of the images of a living and the dead image of the same
 * enemy.
 *
 * @author Basil Ermatinger
 * @version 2020-05-11
 */
public class ImageHandler {

    private final ArrayList<HashMap<String, Image>> IMAGE_SET;
    private HashMap<String, Image> currentImageSet;
    private int currentImageSetCounter;

    /**
     * Creates an ImageHandler object. It loads the images from the 'avatars' folder and writes them into an array as
     * pairs of dead and alive enemies. Finally, the current pair is set.
     */
    public ImageHandler() throws IOException {
        IMAGE_SET = new ArrayList<>();
        currentImageSetCounter = 0;
        loadImages();
        setCurrentImageSet();
    }

    /**
     * Returns the next pair of images (ImageSet - consisting of a pair of images for the living and dead enemy) in the
     * corresponding ArrayList. Subsequently, the next ImageSet is set as the current ImageSet.
     *
     * @return Pair of images (living and dead state of an enemy)
     */
    public HashMap<String, Image> getNextImageSet() {
        HashMap<String, Image> nextImageSet = currentImageSet;
        setCurrentImageSet();
        return nextImageSet;
    }

    /**
     * Loads all images from the folder 'avatars' into an array. The picture are put as pairs into a HashMap.
     * A pair consists of one image for the living enemy and one for the dead enemy.
     * The state of the enemy (alive, dead) is set as key, while the corresponding image is set as value.
     *
     * @throws IOException
     */
    private void loadImages() throws IOException {
        String avatarFolder = "avatars";
        String[] avatarsSubDirectories;

        File avatarsSubDirectoriesFile = new File("src/main/resources/" + avatarFolder);
        System.out.println(avatarsSubDirectoriesFile.isDirectory());

        avatarsSubDirectories = avatarsSubDirectoriesFile.list();

        for(String stateSubDirectories : avatarsSubDirectories) {
            IMAGE_SET.add(createNewImageSet(avatarsSubDirectoriesFile, stateSubDirectories, avatarFolder));
        }
    }

    /**
     * Creates a pair of images. A pair consists of one image for the living enemy and one for the dead enemy.
     *
     * @param avatarsSubDirectoriesFile representations of all subdirectories in the avatars folder
     * @param stateSubDirectories a String of the enemy-folder, from which the image pair must be read
     * @param avatarsFolder a String of the name of the avatars-folder
     * @return A pair of (consists of one image for the living enemy and one for the dead enemy.)
     * @throws NotDirectoryException
     */
    private HashMap<String, Image> createNewImageSet(File avatarsSubDirectoriesFile, String stateSubDirectories,
                                                     String avatarsFolder) throws NotDirectoryException {
        String enemyAliveImageString, enemyDeadImageString;
        Image enemyAliveImage, enemyDeadImage;

        HashMap<String, Image> imageSet = new HashMap<>();

        File enemyAliveImageFolder = new File(avatarsSubDirectoriesFile.getPath()
                + File.separatorChar + stateSubDirectories + File.separatorChar + "alive");
        File enemyDeadImageFolder = new File(avatarsSubDirectoriesFile.getPath()
                + File.separatorChar + stateSubDirectories + File.separatorChar + "dead");

        if(enemyAliveImageFolder.isDirectory() && enemyDeadImageFolder.isDirectory()) {
            enemyAliveImageString = "/" + avatarsFolder + "/" + stateSubDirectories + "/alive/"
                    + enemyAliveImageFolder.list()[0];
            enemyDeadImageString = "/" + avatarsFolder + "/" + stateSubDirectories + "/dead/"
                    + enemyDeadImageFolder.list()[0];
        } else {
            throw new NotDirectoryException(null);
        }

        enemyAliveImage = new Image(ImageHandler.class.getResourceAsStream(enemyAliveImageString));
        enemyDeadImage = new Image(ImageHandler.class.getResourceAsStream(enemyDeadImageString));

        if(enemyAliveImage != null && enemyDeadImage != null) {
            imageSet.put("alive", enemyAliveImage);
            imageSet.put("dead", enemyDeadImage);
        } else {
            throw new NullPointerException();
        }
        return imageSet;
    }

    /**
     * Sets the current ImageSet using a counter. The counter is incremented by 1 after each set, so that the next
     * ImageSet can be set when the method is called again.
     */
    private void setCurrentImageSet() {
        currentImageSet = IMAGE_SET.get(currentImageSetCounter);
        increaseCurrentImageSetCounter();
    }

    /** Increases the current image set counter by 1 **/
    private void increaseCurrentImageSetCounter() {
        currentImageSetCounter = ++currentImageSetCounter % IMAGE_SET.size();
    }
}
