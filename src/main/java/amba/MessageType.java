package amba;

public enum MessageType {
    WELCOME_MESSAGE("Wenn Du mich besiegen willst, beantworte alle Fragen richtig" + System.lineSeparator() + "Wenn Du eine Frage falsch beantwortest, ziehe ich dir einen Punkt ab!"),
    WIN_MESSAGE("HAHA, ich habe Dich besiegt!"),
    LOOSE_MESSAGE("Oh nein, Du hast mich besiegt!"),
    QUESTION_CORRECT("Richtig"),
    QUESTION_FALSE("Falsch"),
    ALL_ENEMIES_DEAD("Oh nein, Du hast uns alle besiegt!");

    private final String MESSAGE;

    private MessageType(String message) {
        MESSAGE = message;
    }

    public String getMessage() {
        return MESSAGE;
    }
}
