import amba.Question;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    private Question question;

    @BeforeEach
    void setUp() {
        // Set Up: Erstellen einer Beispiel-Question-Instanz
        Map<String, Boolean> answers = new HashMap<>();
        answers.put("Answer A", true);  // Korrekte Antwort
        answers.put("Answer B", false);
        answers.put("Answer C", false);
        answers.put("Answer D", true); // Korrekte Antwort

        question = new Question("What is the capital of Germany?", answers);
    }

    @Test
    void isAnswerCorrect() {
        // Test: Korrekte Antwort
        assertTrue(question.isAnswerCorrect("Answer A"));
        assertTrue(question.isAnswerCorrect("Answer D"));

        // Test: Falsche Antwort
        assertFalse(question.isAnswerCorrect("Answer B"));

        // Test: Antwort nicht in der Liste
        assertFalse(question.isAnswerCorrect("Not an Answer"));
    }

    @Test
    void getQuestion() {
        // Test: Überprüfung, ob die Frage korrekt zurückgegeben wird
        assertEquals("What is the capital of Germany?", question.getQuestion());
    }

    @Test
    void getAnswers() {
        // Test: Überprüfen, ob die Liste der Antworten korrekt zurückgegeben wird
        ArrayList<String> expectedAnswers = new ArrayList<>();
        expectedAnswers.add("Answer A");
        expectedAnswers.add("Answer B");
        expectedAnswers.add("Answer C");
        expectedAnswers.add("Answer D");

        assertEquals(expectedAnswers, question.getAnswers());
    }
}