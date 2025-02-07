package amba;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * The PackageHandler class manages the different learning packages. When the class is initialized, a HashMap is created
 * and all already predefined learning packages are created via the package reader and loaded into the HashMap just
 * created. Furthermore, the class can add new learning packages and return them using the ID of the package.
 *
 * @author Basil Ermatinger
 * @version 2020-05-11
 */
public class PackageHandler {

    private final HashMap<Integer, LearningPackage> learningPackages;
    private final PackageReader packageReader;

    /**
     * Instantiates a new Package Handler.
     *
     * @throws IOException Throws an exception if the file cannot be found
     */
    public PackageHandler(PackageReader packageReader) throws IOException {
        learningPackages = new HashMap<>();
        this.packageReader = packageReader;
        addInitialPackages();
    }

    /**
     * Instantiates learning packages.
     *
     * @param learningPackage the learning package
     */
    public void addLearningPackage(LearningPackage learningPackage) {
        learningPackages.put(learningPackage.getId(), learningPackage);
    }

    /** @return a HashMap of all existing learning packages */
    public HashMap<Integer, LearningPackage> getLearningPackages() { return learningPackages; }

    /**
     * Gets the learning package with help of its key from the HashMap learningPackages
     *
     * @param learningPackageID the id, which is the key of the learning package within the HashMap learningPackages
     * @return the learning package
     */
    public LearningPackage getPackage(int learningPackageID) {
        return learningPackages.get(learningPackageID);
    }

    /**
     * Delivers the path of all initial learning package files and initial learning packages
     *
     */
    private void addInitialPackages() {
        String[] pathNames;
        String path = "src/main/resources/initialLearningPackages/";
        File files = new File(path);

        pathNames = files.list();
        if (pathNames != null) {
            for (String pathname : pathNames) {
                LearningPackage learningPackage = packageReader.createLearningPackage(path + pathname);
                learningPackages.put(learningPackage.getId(), learningPackage);
            }
        } else {
            System.err.println("No valid or empty parameters are provided.");
        }
    }
}
