package gruppe01.amba.projekt2.lernapp;

import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.IOException;
import java.util.HashMap;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testclass to test class imageHandler.
 */
class ImageHandlerTest {
    private Image enemyAliveImage;
    private Image enemyDeadImage;
    private HashMap<String, Image> nextImageSet;
    @Mock
    private ImageHandler imageHandler = new ImageHandler();

    ImageHandlerTest() throws IOException {
    }

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        nextImageSet = new HashMap<>();
        nextImageSet.put("alive", enemyAliveImage);
        nextImageSet.put("dead", enemyDeadImage);
    }

    /**
     * Gets next image set.
     */
    @Test
    void getNextImageSet() {
        when(imageHandler.getNextImageSet()).thenReturn(nextImageSet);
        assertEquals(nextImageSet, imageHandler.getNextImageSet());
    }
}