import amba.Character;

import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    private Character character;

    // Subklasse für das Testen der abstrakten Klasse Character
    private static class TestCharacter extends Character {
        // Konstruktor ruft den Superklassen-Konstruktor auf
        public TestCharacter(int id, int healthPoints, Image avatar, String name) {
            super(id, healthPoints, avatar, name);
        }
    }

    @BeforeEach
    void setUp() {
        // Erstelle die Testcharakter-Instanz mit Dummy-Daten
        character = new TestCharacter(1, 100, null, "TestCharacter");
    }

    @Test
    void testConstructor() {
        assertEquals(1, character.getId(), "Die Id sollte 1 sein."); // Testet den Konstruktor
        assertEquals(100, character.getHealthPoints(), "Die HealthPoints sollten 100 sein.");
        assertEquals("TestCharacter", character.getName(), "Der Name sollte 'TestCharacter' sein.");
        assertTrue(character.isCharacterAlive(), "Der Charakter sollte zu Beginn lebendig sein.");
        assertNull(character.getAvatar(), "Das Avatar-Image sollte null sein.");
    }

    @Test
    void testDie() {
        character.die();
        assertFalse(character.isCharacterAlive(), "Nach dem Aufruf von die() sollte der Charakter tot sein.");
    }

    @Test
    void testSetHealthPoints() {
        character.setHealthPoints(80);
        assertEquals(80, character.getHealthPoints(), "Die HealthPoints sollten auf 80 gesetzt sein.");
    }

    @Test
    void testIncreaseHealth() {
        character.increaseHealth();
        assertEquals(101, character.getHealthPoints(), "Die HealthPoints sollten um 1 erhöht worden sein.");
    }

    @Test
    void testDecreaseHealth() {
        character.decreaseHealth();
        assertEquals(99, character.getHealthPoints(), "Die HealthPoints sollten um 1 reduziert worden sein.");
    }

    @Test
    void testHealthReachesZero() {
        character.setHealthPoints(1);
        character.decreaseHealth(); // Reduziert Health auf 0
        assertEquals(0, character.getHealthPoints(), "Die HealthPoints sollten 0 sein.");
        assertFalse(character.isCharacterAlive(), "Der Charakter sollte tot sein, wenn HealthPoints 0 sind.");
    }

    @Test
    void testNegativeHealth() {
        character.setHealthPoints(1);
        character.decreaseHealth(); // Reduziert Health auf 0 und ruft die() auf
        character.decreaseHealth(); // Weitere Reduktion
        assertEquals(0, character.getHealthPoints(), "Die HealthPoints sollten nicht negativ werden.");
        assertFalse(character.isCharacterAlive(), "Der Charakter sollte tot sein, wenn HealthPoints <= 0.");
    }

    @Test
    void testHealthCannotBeNegative() {
        character.setHealthPoints(1);
        character.decreaseHealth();
        character.decreaseHealth(); // Macht healthPoints = 0
        assertEquals(0, character.getHealthPoints()); // Überprüft, dass healthPoints 0 sind

        character.decreaseHealth(); // Versucht weiter zu dekrementieren
        assertEquals(0, character.getHealthPoints()); // Bleibt bei 0
    }

    @Test
    void testGetHealthPointsReturnsZeroWhenDead() {
        character.setHealthPoints(1);
        character.decreaseHealth(); // healthPoints = 0
        character.decreaseHealth(); // healthPoints < 0, aber getHealthPoints() gibt 0 zurück
        assertEquals(0, character.getHealthPoints()); // Prüft Logik von getHealthPoints()
    }

    @Test
    void testCharacterDiesCorrectly() {
        character.setHealthPoints(1);
        character.decreaseHealth(); // Macht healthPoints = 0 -> tot
        assertEquals(0, character.getHealthPoints()); // Überprüft, ob healthPoints korrekt sind
    }

    @Test
    void testGetId() {
        assertEquals(1, character.getId(), "Der Charakter sollte die ID 1 haben.");
    }

    @Test
    void testGetName() {
        assertEquals("TestCharacter", character.getName(), "Der Name des Charakters sollte 'TestCharacter' sein.");
    }

    @Test
    void testGetAvatar() {
        assertNull(character.getAvatar(), "Der Avatar sollte null sein, da kein Bild gesetzt wurde.");
    }
}