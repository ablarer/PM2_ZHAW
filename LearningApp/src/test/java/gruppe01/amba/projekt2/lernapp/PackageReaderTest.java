package gruppe01.amba.projekt2.lernapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PackageReaderTest {
    public PackageReader createLearningPackageTest;

    @BeforeEach
    void setUp() throws IOException {
        createLearningPackageTest = new PackageReader();
    }

    @Test
    void correctNumberOfEnemies(){
        LearningPackage learningPackage;
        learningPackage = createLearningPackageTest.createLearningPackage("src/main/resources/initialLearningPackages/01 Rechtschreibung_Gross- oder Kleinschreibung.txt");
        assertEquals(3, learningPackage.getEnemies().size() );
    }

    @Test
    void testCreateLearningPackageNoException() {
        //Test the validity of a file
        assertDoesNotThrow(() -> createLearningPackageTest.createLearningPackage("src/main/resources/initialLearningPackages/01 Rechtschreibung_Gross- oder Kleinschreibung.txt"));
    }
}