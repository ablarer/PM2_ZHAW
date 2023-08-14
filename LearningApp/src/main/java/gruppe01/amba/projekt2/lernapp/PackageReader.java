package gruppe01.amba.projekt2.lernapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import javafx.scene.image.Image;

/**
 * The type Package reader.
 *
 * @author Albert Blarer
 * @version 27.04.20
 */
public class PackageReader {

    ImageHandler imageHandler;

    private int packageCounter = 0;

    Enemy enemy;

    public ArrayList<String> exerciseFileLines;

    public int numberOfMonsters;
    public int numberOfQuestionsPerMonster;
    public int numberOfAnswersPerQuestion;
    public String descriptionOfLearningPackage;
    public String category;
    public ArrayList<String> monsterDescriptions;
    public ArrayList<String> monsterNames;
    public ArrayList<Integer> monsterLevels;
    public ArrayList<String> questionsForAllMonsters;
    public HashMap<Integer, ArrayList<String>> questionsSeparatedForEachMonster;
    public ArrayList<String> answersForAllQuestions;
    HashMap<String, Boolean> answersAndTheirValidity;
    public  HashMap<Integer, ArrayList<String>> answersSeparatedForEachQuestion;

    public  int monsterID;

    public HashMap<Integer, Enemy> enemies;

    /**
     * Instantiates a new Package reader.
     *
     * @throws IOException the io exception
     */
    public PackageReader() throws IOException {
        imageHandler = new ImageHandler();
    }

    /**
     * Returns the substring after the first, second, and third occurrence of a delimiter.
     * The delimiter is not part of the result.
     * The substrings hold the information about the number of
     * -Firstly:  The number of monsters.
     * -Secondly: The number of question per monster.
     * -Thirdly:  The number of answers per question.
     *
     * @param exerciseFileLines the exercise file lines
     */
    private void getNumberOfMonstersQuestionPerMonsterAnswersPerQuestion(ArrayList<String> exerciseFileLines) {
        String numberOfInformationString = exerciseFileLines.get(0);
        numberOfMonsters = Integer.parseInt(subStringBefore(numberOfInformationString));
        numberOfQuestionsPerMonster = Integer.parseInt(subStringMiddle(numberOfInformationString));
        numberOfAnswersPerQuestion = Integer.parseInt(subStringAfter(numberOfInformationString));
    }

    /**
     * Gets category.
     *
     * @param exerciseFileLines the exercise file lines
     */
    private void getCategory(ArrayList<String> exerciseFileLines) {
        //1 = absolute position of the category within the file
        category = subStringBefore(exerciseFileLines.get(1));
    }

    /**
     * Gets description.
     *
     * @param exerciseFileLines the exercise file lines
     */
    private void getDescription(ArrayList<String> exerciseFileLines) {
        //2 = absolute position of the description within the file
        descriptionOfLearningPackage = subStringBefore(exerciseFileLines.get(2));
    }

    /**
     * Gets description of all monsters.
     *
     * @param exerciseFileLines the exercise file lines
     */
    private void getDescriptionOfAllMonsters(ArrayList<String> exerciseFileLines) {
        //3 = absolute position of the first monster's description within the file
        int positionOfFirstMonsterDescriptionInFile = 3;
        for (int i = positionOfFirstMonsterDescriptionInFile; i < positionOfFirstMonsterDescriptionInFile + numberOfMonsters; i++) {
            monsterDescriptions.add(subStringBefore(exerciseFileLines.get(i)));
        }
    }

    /**
     * Gets name of all monsters.
     *
     * @param exerciseFileLines the exercise file lines
     */
    private void getNameOfAllMonsters(ArrayList<String> exerciseFileLines) {
        //3 = absolute position of the first monster's description within the file
        int positionOfFirstMonsterNameInFile = 3 + numberOfMonsters;
        for (int i = positionOfFirstMonsterNameInFile; i < positionOfFirstMonsterNameInFile + numberOfMonsters; i++) {
            monsterNames.add(subStringBefore(exerciseFileLines.get(i)));
        }
    }

    /**
     * Gets level of all monsters.
     *
     * @param exerciseFileLines the exercise file lines
     */
    private void getLevelOfAllMonsters(ArrayList<String> exerciseFileLines) {
        //3 = absolute position of the first monster's description within the file
        int positionOfFirstLevelInFile = 3 + (2 * numberOfMonsters);
        for (int i = positionOfFirstLevelInFile; i < positionOfFirstLevelInFile + numberOfMonsters; i++) {
            int level = Integer.parseInt(subStringBefore(exerciseFileLines.get(i)));
            monsterLevels.add(level);
        }
    }

    /**
     * Gets questions of all monsters.
     *
     * @param exerciseFileLines the exercise file lines
     */
    private void getQuestionsOfAllMonsters(ArrayList<String> exerciseFileLines) {
        //3 = absolute position of the first monster's description within the file
        int positionOfQuestionsOfAllMonsters = 3 + (3 * numberOfMonsters);
        for (int i = positionOfQuestionsOfAllMonsters; i < positionOfQuestionsOfAllMonsters + numberOfQuestionsPerMonster * numberOfMonsters; i++) {
            questionsForAllMonsters.add(subStringBefore(exerciseFileLines.get(i)));
        }
    }

    /**
     * Gets answers for all question.
     *
     * @param exerciseFileLines the exercise file lines
     */
    private void getAnswersForAllQuestions(ArrayList<String> exerciseFileLines) {
        //3 = absolute position of the first monster's description within the file
        int positionOfFirstAnswerInFile = 3 + (3 * numberOfMonsters) + (numberOfQuestionsPerMonster * numberOfMonsters);
        for (int i = positionOfFirstAnswerInFile; i < positionOfFirstAnswerInFile + (numberOfQuestionsPerMonster * numberOfAnswersPerQuestion * numberOfMonsters); i++) {
            answersForAllQuestions.add(subStringBefore(exerciseFileLines.get(i)));
        }
    }


    /**
     * Gets questions separated for each monster.
     */
    private void getQuestionsSeparatedForEachMonster() {
        int i = 0;
        do {
            ArrayList<String> question = new ArrayList<>();
            int j = 0;
            do {
                question.add(questionsForAllMonsters.get(i * numberOfQuestionsPerMonster + j));
                questionsSeparatedForEachMonster.put(i, question);
                j++;
            }
            while (j < numberOfQuestionsPerMonster);
            i++;
        }
        while (i < numberOfMonsters);
    }


    /**
     * Returns the substring  before the first occurrence of the "." delimiter.
     * The delimiter ist not part of the result.
     * Prepared for a future extension of the app to provide information of the contents of the learning packages
     *
     * @param stringToProcess line of file, which contains information that has to be extracted
     * @return substring  before the first delimiter
     */
    private String subStringBefore(String stringToProcess) {
        String[] numbers = stringToProcess.split("\\.");
        return numbers[0];
    }

    /**
     * Returns the substring after the first and before the second occurrence of the "." delimiter.
     * The delimiter ist not part of the result.
     *
     * @param stringToProcess line of file, which contains information that has to be extracted
     * @return substring after the first and before the next delimiter
     */
    private String subStringMiddle(String stringToProcess) {
        String[] numbers = stringToProcess.split("\\.");
        return numbers[1];
    }

    /**
     * Returns the substring after the second and before the third occurrence of the "." delimiter.
     * The delimiter ist not part of the result.
     *
     * @param stringToProcess line of file, which contains information that has to be extracted
     * @return substring after the second and before the third delimiter
     */
    private String subStringAfter(String stringToProcess) {
        String[] numbers = stringToProcess.split("\\.");
        return numbers[2];
    }

    /**
     * getter method to relay information about the exercises and the monsters' parameter
     *
     * @return exerciseFileLines contains the information to define the exercises and the monsters' parameter
     */
    private ArrayList<String> getExerciseFileLines() {
        return exerciseFileLines;
    }

    /**
     * Constructs an array that contains the lines of the exercise file, which is used to define exercises and
     * monsters' parameters.
     *
     * An {@link InvalidExerciseFormatException} is thrown, if the exercise file is not manually designed according to the
     * manual.
     *
     * @param exerciseFile Reference to a file containing the track data
     * @throws NullPointerException  if no valid parameters are provide to handle the exercise file
     * @throws InvalidExerciseFormatException if the given exercise file could not be found
     */
    private void readFile(String exerciseFile) throws NullPointerException, InvalidExerciseFormatException {
        try {
            Scanner input = new Scanner(exerciseFile);
            File file = new File(input.nextLine());
            input = new Scanner(file);
            while (input.hasNextLine()) {
                String line = input.nextLine();
                exerciseFileLines.add(line);
            }
            isFileValid();
            input.close();
        } catch (NullPointerException e) {
            System.err.print("No valid or empty parameters are provided.");
        } catch (FileNotFoundException e) {
            System.err.print("There is a problem about the availability of the file.");
        }
    }

    /**
     *Checks the file for its validity
     *
     * @throws InvalidExerciseFormatException if the exercise file has a different number of lines as calculated by
     * parameters in the first line of the file or if the parameters are not accessible.
     */
    private void isFileValid() throws InvalidExerciseFormatException {
        getNumberOfMonstersQuestionPerMonsterAnswersPerQuestion(exerciseFileLines);
        if (exerciseFileLines.size() != 3 + (3 * numberOfMonsters) + (numberOfQuestionsPerMonster * numberOfMonsters) + (numberOfQuestionsPerMonster*numberOfAnswersPerQuestion * numberOfMonsters)){
            throw new InvalidExerciseFormatException("Please check the exercise file format.");
        }
    }

    /**
     * Create learning package learning package.
     *
     * @param exerciseFile the exercise file
     * @return the learning package
     */
    public LearningPackage createLearningPackage(String exerciseFile) {
        monsterID = 0;
        exerciseFileLines = new ArrayList<>();
        monsterDescriptions = new ArrayList<>();
        monsterNames = new ArrayList<>();
        monsterLevels = new ArrayList<>();
        questionsForAllMonsters = new ArrayList<>();
        questionsSeparatedForEachMonster = new HashMap<>();
        answersForAllQuestions = new ArrayList<>();
        answersAndTheirValidity = new HashMap<>();
        answersSeparatedForEachQuestion = new HashMap<>();

        try {
            readFile(exerciseFile);
        } catch (InvalidExerciseFormatException e) {
            e.printStackTrace();
        }

        getNumberOfMonstersQuestionPerMonsterAnswersPerQuestion(getExerciseFileLines());
        getCategory(getExerciseFileLines());
        getDescription(getExerciseFileLines());
        getDescriptionOfAllMonsters(getExerciseFileLines());
        getNameOfAllMonsters(getExerciseFileLines());
        getLevelOfAllMonsters(getExerciseFileLines());
        getQuestionsOfAllMonsters(getExerciseFileLines());
        getAnswersForAllQuestions(getExerciseFileLines());
        getQuestionsSeparatedForEachMonster();


        enemies = new HashMap<>();
        int i = 0;
        do {
            int level = monsterLevels.get(i);
            String description = monsterDescriptions.get(i);
            int currentMonsterID = i;
            String monsterName = monsterNames.get(i);

            HashMap<String, Image> avatarImages = imageHandler.getNextImageSet();

            ArrayList<Question> question = createQuestionObjects(currentMonsterID);

            enemy = new Enemy(level, description, currentMonsterID, monsterName, avatarImages, question);

            enemies.put(i, enemy);
            i++;
        }
        while (i < numberOfMonsters);
        return (new LearningPackage(packageCounter++, enemies, category));
    }

    /**
     * Create question objects
     *
     * @param monsterID the monster id
     * @return the array list
     */
    private ArrayList<Question> createQuestionObjects(int monsterID) {
        ArrayList<String> questionsForOneMonster = questionsSeparatedForEachMonster.get(monsterID);
        ArrayList<Question> allQuestionForOneMonster = new ArrayList<>();
        // i = question 1 to n per monster
        for (int i = 0; i < questionsForOneMonster.size(); i++) {
            HashMap<String, Boolean> answersAndTheirValidity = new HashMap<>();
            //j = answer 1 - n per question per monster
            int j = 0;
            do {
                int position = 3 + (3 * numberOfMonsters + numberOfMonsters * numberOfQuestionsPerMonster) + j + (i * numberOfAnswersPerQuestion) + (monsterID *  numberOfQuestionsPerMonster * numberOfAnswersPerQuestion);
                if (j == 0) {
                    answersAndTheirValidity.put((subStringBefore(exerciseFileLines.get(position))), true);
                } else {
                    answersAndTheirValidity.put((subStringBefore(exerciseFileLines.get(position))), false);
                }
                j++;
            }
            while (j < numberOfAnswersPerQuestion);
            String question = questionsForOneMonster.get(i);
            allQuestionForOneMonster.add(new Question(question, answersAndTheirValidity));
        }
        return allQuestionForOneMonster;
    }
}



