package utils;

import java.io.*;
import java.util.*;

/**
 * Utility class for reading data from a CSV (Comma-Separated Values) file.
 * <p>
 * This reader skips the first line assuming it is a header and reads the remaining lines as data.
 */
public class CSVReader {

    /**
     * Reads a CSV file and returns its contents as a list of string arrays.
     * Each array represents a row, and each element in the array is a column value.
     *
     * @param filePath the relative or absolute path to the CSV file
     * @return a list of string arrays containing the parsed CSV data (excluding the header row)
     */
    public static List<String[]> readCSV(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); // Split by comma
                data.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}


