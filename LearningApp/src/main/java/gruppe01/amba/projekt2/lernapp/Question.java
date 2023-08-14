package gruppe01.amba.projekt2.lernapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Question {
    private final String QUESTION;
    private final Map<String, Boolean> ANSWERS;

    public Question(String question, Map<String, Boolean> answers) {
        this.QUESTION = question;
        this.ANSWERS = answers;
    }

    public boolean isAnswerCorrect(String answer) {
        boolean isAnswerCorrect;
        if(ANSWERS.keySet().contains(answer)) {
            isAnswerCorrect = ANSWERS.get(answer);
        }
        else isAnswerCorrect = false;
        return isAnswerCorrect;
    }

    public String getQuestion() {
        return QUESTION;
    }

    public ArrayList<String> getAnswers() {
        ArrayList<String> answers = new ArrayList<>(ANSWERS.keySet());
        return answers;
    }
}
