package util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVManager {

    // delimiter of csv file. Use comma delimiter by default
    private String delimiter = ",";

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * Method to read a csv file into an ArrayList
     * @param fileName - must be in absolute path
     */
    public List<List<String>> read(final String fileName) {
        final List<List<String>> images = new ArrayList<List<String>>();
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNext()) {
                images.add(getRecordFromLine(scanner.nextLine()));
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return images;
    }

    /**
     * Method to write output lists into csv file
     * @param output
     * @param fileName
     */
    public void write(final List<List<String>> output, final String fileName) {
        File csvOutputFile = new File(fileName);
        try {
            PrintWriter pw = new PrintWriter(csvOutputFile);
            output.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
            pw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * private method to convert a row of csv into a list of strings
     * @param row
     * @return
     */
    private List<String> getRecordFromLine(String row) {
        List<String> records = new ArrayList<String>();
        try  {
            Scanner rowScanner = new Scanner(row);
            rowScanner.useDelimiter(delimiter);
            while(rowScanner.hasNext()) {
                records.add(rowScanner.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;
    }

    /**
     * Private method to convert a list of strings to csv format
     * @param records
     * @return
     */
    private String convertToCSV(List<String> records) {
        String [] arr = records.toArray(new String [records.size()]);
        return Stream.of(arr)
                .collect(Collectors.joining(delimiter));
    }
}