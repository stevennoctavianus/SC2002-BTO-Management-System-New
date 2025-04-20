package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Utility class for writing data to a CSV (Comma-Separated Values) file.
 * Each row of data is written as a comma-separated line. Overwrites the file if it exists.
 */
public class CSVWriter {

    /**
     * Writes the provided list of string arrays to a CSV file.
     * Each array represents a row, and each element in the array is a column value.
     *
     * @param filePath the path where the CSV file should be written
     * @param data     a list of string arrays, where each array corresponds to one row of data
     */
    public static void writeCSV(String filePath, List<String[]> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] row : data) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


