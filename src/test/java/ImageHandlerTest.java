import amba.ImageHandler;

import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.NotDirectoryException;
import java.util.HashMap;

import java.lang.reflect.Method;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ImageHandler.
 */
class ImageHandlerTest {

    @Mock
    private ImageHandler imageHandlerMock;

    private ImageHandler imageHandler;

    @BeforeEach
    void setUp() throws IOException {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Real instance for integration-style tests
        imageHandler = spy(new ImageHandler());
    }

    @Test
    void getNextImageSet_shouldReturnValidImageSet_whenHandlerInitialized() throws IOException {
        // Arrange
        ImageHandler handler = new ImageHandler(); // Konstruktor ruft loadImages() und damit createNewImageSet auf

        // Act
        HashMap<String, Image> imageSet = handler.getNextImageSet();

        // Assert
        assertNotNull(imageSet, "Image set should not be null");
        assertTrue(imageSet.containsKey("alive"), "Image set should contain an 'alive' key");
        assertTrue(imageSet.containsKey("dead"), "Image set should contain a 'dead' key");
        assertNotNull(imageSet.get("alive"), "Alive image should not be null");
        assertNotNull(imageSet.get("dead"), "Dead image should not be null");
    }

    @Test
    void createNewImageSet_shouldThrowInvocationTargetException_whenInvalidDirectories() throws Exception {
        // Arrange
        File invalidDirectory = new File("invalid/path");
        String stateSubDirectory = "enemy1";
        String avatarsFolder = "avatars";

        // Zugriff auf die private Methode mit Reflection
        Method method = ImageHandler.class.getDeclaredMethod(
                "createNewImageSet",
                File.class,
                String.class,
                String.class
        );
        method.setAccessible(true); // Ermöglicht den Zugriff auf private Methoden

        // Act & Assert
        assertThrows(InvocationTargetException.class, () ->
                method.invoke(imageHandler, invalidDirectory, stateSubDirectory, avatarsFolder));
    }

    @Test
    void createNewImageSet_shouldThrowNotDirectoryException_whenSubDirectoryIsInvalid() {
        // Arrange
        File invalidDirectory = new File("invalid/directory");
        String invalidSubDirectory = "nonexistentSubDir";
        String avatarsFolder = "avatars";

        // Make sure the invalid directory doesn't exist
        assertFalse(invalidDirectory.exists(), "The invalid directory should not exist.");

        try {
            // Reflective access to the private createNewImageSet method
            Method method = ImageHandler.class.getDeclaredMethod(
                    "createNewImageSet", File.class, String.class, String.class
            );
            method.setAccessible(true);

            // Act & Assert
            Throwable exception = assertThrows(InvocationTargetException.class, () ->
                    method.invoke(imageHandler, invalidDirectory, invalidSubDirectory, avatarsFolder)
            );
            assertTrue(exception.getCause() instanceof NotDirectoryException, "NotDirectoryException should be thrown for invalid subdirectories.");

        } catch (NoSuchMethodException e) {
            fail("Failed to access the createNewImageSet method via reflection.");
        }
    }

    @Test
    void createNewImageSet_shouldThrowNullPointerException_whenImageFilesAreNull() {
        File directory = null;                           // Simuliere null als Eingabe
        String stateSubDirectory = null;                 // Simulierte null-Eingabe
        String avatarsFolder = null;                     // Simulierte null-Eingabe

        // Sicherstellen, dass eine NullPointerException geworfen wird
        assertThrows(
                NullPointerException.class,
                () -> imageHandler.createNewImageSet(directory, stateSubDirectory, avatarsFolder),
                "NullPointerException should be thrown when input parameters are null!"
        );
    }

    @Test
    void getNextImageSet_shouldNotThrowException_whenImageSetIsEmpty() throws IOException {
        // Act & Assert
        try {
            HashMap<String, Image> imageSet = imageHandler.getNextImageSet();
            assertNotNull(imageSet, "getNextImageSet should return a valid (but possibly empty) image set.");
        } catch (Exception e) {
            fail("getNextImageSet should not throw any exceptions even if the image set is empty.");
        }
    }
}