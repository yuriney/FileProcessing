package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class NormalizeFile {
    public static void main(String[] args) {
        // Define input and output file paths
        String inputFile = "C:\\Users\\icabi\\Documents\\MP - DOCS\\Automações e Scripts\\Test\\Splitting\\normalizer.txt";
        String outputFile = "C:\\Users\\icabi\\Documents\\MP - DOCS\\Automações e Scripts\\Test\\Splitting\\resultado.txt";

        try {
            // Initialize a reader to read from the input file and a writer to write to the output file
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            FileWriter writer = new FileWriter(outputFile);

            String line;
            while ((line = reader.readLine()) != null) {
                // Normalize the text in each line (remove diacritical marks)
                String normalizedLine = normalizeText(line);
                // Write the normalized line to the output file with a newline character
                writer.write(normalizedLine + "\n");
            }

            // Close the reader and writer
            reader.close();
            writer.close();
            System.out.println("Normalization and writing completed.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Read paths from the input file
            List<String> paths = readPathsFromFile(inputFile);

            // Initialize a list to store filtered and formatted paths
            List<String> title_filtra = new ArrayList<>();

            for (String path : paths) {
                // Ensure proper character encoding
                path = new String(path.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);

                // Replace hyphens with spaces and convert to uppercase
                String uppercasePath = path.replaceAll("-", " ").toUpperCase();
                // Remove characters that are not letters or digits
                uppercasePath = uppercasePath.replaceAll("[^a-zA-Z0-9]", " ");

                // Extract the parent directory part of the path
                int lastBackslashIndex = uppercasePath.lastIndexOf("\\");
                if (lastBackslashIndex >= 0) {
                    uppercasePath = uppercasePath.substring(0, lastBackslashIndex);
                }

                // Add the formatted path to the list
                title_filtra.add(uppercasePath);
            }

            // Write the filtered and formatted paths to an outcome file
            writeOutcomeToFile("C:\\Users\\icabi\\Documents\\MP - DOCS\\Automações e Scripts\\Test\\Splitting\\title_filtra.txt", title_filtra);

            System.out.println("Outcome files generated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Function to normalize text by removing diacritical marks
    public static String normalizeText(String text) {
        String normalizedText = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizedText).replaceAll("");
    }

    // Function to read paths from a file
    private static List<String> readPathsFromFile(String filePath) throws IOException {
        List<String> paths = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String line;
        while ((line = reader.readLine()) != null) {
            paths.add(line.trim());
        }

        reader.close();
        return paths;
    }

    // Function to write outcome to a file
    private static void writeOutcomeToFile(String filePath, List<String> outcomes) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        for (String outcome : outcomes) {
            writer.write(outcome + "\n");
        }
        writer.close();
    }
}
