import util.CSVManager;
import util.ImageComparator;
import util.Updater;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<Observer> observers;   // a List of view observers. In our case, there is only one observer

    private Updater updater;

    private CSVManager csvManager;

    private ImageComparator imageComparator;

    private List<List<String>> results;

    public Model() {
        this.observers = new ArrayList<Observer>();
        updater = new Updater();
        csvManager = new CSVManager();
        imageComparator = new ImageComparator();
    }

    public void addObserver(Observer observer) {
        this.observers.add(observer);
        checkUpdate(); // check update on starting the process
    }

    /**
     * Method to start reading csv file, compare the images and write results to a new csv file
     * @param file
     */
    public void startProcess(String file) {
        List<List<String>> images = csvManager.read(file);
        results = imageComparator.compare(images);
    }

    /**
     * Method to save result into csv
     * @param file
     */
    public void saveResult(String file) {
        csvManager.write(results, file);
    }

    /**
     * Method to download new source code from remote server
     */
    public void updateSoftware() {
        updater.update();
    }

    private void checkUpdate() {
        // If update is available, notify the view/user
        if (updater.hasUpdate()) {
            notifyObservers();
        }
    }

    private void notifyObservers() {
        for (Observer observer : this.observers) {
            observer.update();
        }
    }
}
