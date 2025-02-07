import amba.InvalidExerciseFormatException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit-Tests für die Klasse InvalidExerciseFormatException.
 */
class InvalidExerciseFormatExceptionTest {

    @Test
    void testExceptionMessage() {
        // Arrange
        String expectedMessage = "Invalid exercise format detected";

        // Act
        InvalidExerciseFormatException exception = new InvalidExerciseFormatException(expectedMessage);

        // Assert
        assertEquals(expectedMessage, exception.getMessage(),
                "Die Nachricht der InvalidExerciseFormatException sollte mit dem angegebenen String übereinstimmen.");
    }

    @Test
    void testExceptionInheritance() {
        // Arrange & Act
        InvalidExerciseFormatException exception = new InvalidExerciseFormatException("Test message");

        // Assert
        assertTrue(exception instanceof Exception,
                "InvalidExerciseFormatException sollte von der Klasse Exception erben.");
    }
}