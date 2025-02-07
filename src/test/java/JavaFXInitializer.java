import javafx.application.Platform;

public class JavaFXInitializer {
    private static boolean initialized = false;

    public static void ensureJavaFXToolkitInitialized() {
        if (!initialized) {
            Platform.startup(() -> {}); // JavaFX-Toolkit initialisieren
            initialized = true;
        }
    }
}