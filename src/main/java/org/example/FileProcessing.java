package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileProcessing {
    public static void main(String[] args) {
        // Define the input file path
        final String baseWorkPath = "C:\\Users\\icabi\\Documents\\MP - DOCS\\Automações e Scripts\\Test\\Splitting\\";
        String inputFile = new String(baseWorkPath).concat("am_edicao.txt");

        try {
            // Read paths from the input file
            List<String> paths = readPathsFromFile(inputFile);

            // Initialize lists to store various outcomes
            List<String> programaOutcomes = new ArrayList<>();
            List<String> fileNameOutcomes = new ArrayList<>();
            List<String> extensionFileOutcomes = new ArrayList<>();
            List<String> pathOutcomes = new ArrayList<>();
            List<String> projectOutcomes = new ArrayList<>();
            List<String> title = new ArrayList<>();

            for (String path : paths) {
                // Ensure proper character encoding
                path = new String(path.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);

                // Split the path into parts using backslashes
                String[] parts = path.split("\\\\");
                String fullPath = parts[parts.length - 1];

                // Split the full path into filename and extension
                String[] nameAndExtension = fullPath.split("\\.");

                // Extract folder, filename, and extension
                String folder = parts[1];
                String fileName = nameAndExtension[0];
                String extension = nameAndExtension[nameAndExtension.length - 1];

                // Gather the title by replacing backslashes with hyphens and removing file extensions
                String titleGatherer = path.replaceAll("\\\\", "-");
                titleGatherer = titleGatherer.replaceAll("\\.(.*)$", "");
                titleGatherer = titleGatherer.replaceAll("\\\\", "-");

                // Replace hyphens with spaces and convert to uppercase
                String uppercasePath = path.replaceAll("-", " ").toUpperCase();
                uppercasePath.replaceAll("[^a-zA-Z0-9\\\\]", "");

                // Extract the project name based on the "BRUTO" directory
                for (int i = 0; i < parts.length; i++) {
                    if (parts[i].equals("BRUTO") && i > 1) {
                        String projectTemp = parts[i - 1];
                        projectOutcomes.add(projectTemp);
                    }
                }

                // Add the outcomes to their respective lists
                programaOutcomes.add(folder);
                fileNameOutcomes.add(fileName);
                extensionFileOutcomes.add(extension);
                title.add(titleGatherer);
                pathOutcomes.add(uppercasePath.replaceAll("\\\\", " "));
            }

            // Write the outcomes to separate output files
            writeOutcomeToFile(new String().concat("programa.txt"), programaOutcomes);
            writeOutcomeToFile(new String().concat("file_name.txt"), fileNameOutcomes);
            writeOutcomeToFile(new String().concat("extension_file.txt"), extensionFileOutcomes);
            writeOutcomeToFile(new String().concat("path.txt"), pathOutcomes);
            writeOutcomeToFile(new String().concat("project.txt"), projectOutcomes);
            writeOutcomeToFile(new String().concat("title.txt"), title);

            System.out.println("Outcome files generated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    // Function to write outcomes to a file
    private static void writeOutcomeToFile(String filePath, List<String> outcomes) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        for (String outcome : outcomes) {
            writer.write(outcome + "\n");
        }
        writer.close();
    }
}
