package gruppe01.amba.projekt2.lernapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Testclass to test the class PackageReader.
 */
class PackageHandlerTest {
    private PackageHandler packageHandler = new PackageHandler(new PackageReader());
    HashMap<Integer, LearningPackage> learningPackageTestMap = new HashMap<>();
    private LearningPackage learningPackageMock1 = mock(LearningPackage.class);
    private LearningPackage learningPackageMock2 = mock(LearningPackage.class);
    private LearningPackage learningPackageMock3 = mock(LearningPackage.class);

    PackageHandlerTest() throws IOException {
    }

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        learningPackageTestMap.putAll(packageHandler.getLearningPackages());
        when(learningPackageMock1.getId()).thenReturn(0);
        when(learningPackageMock1.getId()).thenReturn(1);
        when(learningPackageMock1.getId()).thenReturn(2);
    }

    /**
     * Add learning package.
     */
    @Test
    void addLearningPackage() {
        packageHandler.addLearningPackage(learningPackageMock1);
        packageHandler.addLearningPackage(learningPackageMock2);
        packageHandler.addLearningPackage(learningPackageMock3);
        assertEquals(3, packageHandler.getLearningPackages().size());
    }

    /**
     * Gets learning packages.
     */
    @Test
    void getLearningPackages() {
        assertEquals(learningPackageTestMap, packageHandler.getLearningPackages());
    }

    /**
     * Gets package.
     */
    @Test
    void getPackage() {
        assertEquals("Rechtschreibung", packageHandler.getLearningPackages().get(0).toString());
        assertEquals("Rechnen", packageHandler.getLearningPackages().get(1).toString());
    }
}