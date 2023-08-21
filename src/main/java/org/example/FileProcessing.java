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
        String inputFile = "C:\\Users\\icabi\\Documents\\MP - DOCS\\Automações e Scripts\\Test\\Splitting\\am_edicao.txt";

        try {
            List<String> paths = readPathsFromFile(inputFile);

            List<String> programaOutcomes = new ArrayList<>();
            List<String> fileNameOutcomes = new ArrayList<>();
            List<String> extensionFileOutcomes = new ArrayList<>();
            List<String> pathOutcomes = new ArrayList<>();

            for (String path : paths) {
                path = new String(path.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
                String[] parts = path.split("\\\\");
                String fullPath = parts[parts.length - 1];

                String[] nameAndExtension = fullPath.split("\\.");
                String folder = parts[1];
                String fileName = nameAndExtension[0];
                String extension = nameAndExtension.length > 1 ? nameAndExtension[1] : "";
                String uppercasePath = path.replaceAll("[^a-zA-Z0-9\\\\]", "").toUpperCase();

                programaOutcomes.add(folder);
                fileNameOutcomes.add(fileName);
                extensionFileOutcomes.add(extension);
                pathOutcomes.add(uppercasePath.replaceAll("\\\\","-"));
            }

            writeOutcomeToFile("C:\\Users\\icabi\\Documents\\MP - DOCS\\Automações e Scripts\\Test\\Splitting\\programa.txt", programaOutcomes);
            writeOutcomeToFile("C:\\Users\\icabi\\Documents\\MP - DOCS\\Automações e Scripts\\Test\\Splitting\\file_name.txt", fileNameOutcomes);
            writeOutcomeToFile("C:\\Users\\icabi\\Documents\\MP - DOCS\\Automações e Scripts\\Test\\Splitting\\extension_file.txt", extensionFileOutcomes);
            writeOutcomeToFile("C:\\Users\\icabi\\Documents\\MP - DOCS\\Automações e Scripts\\Test\\Splitting\\path.txt", pathOutcomes);

            System.out.println("Outcome files generated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    private static void writeOutcomeToFile(String filePath, List<String> outcomes) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        for (String outcome : outcomes) {
            writer.write(outcome + "\n");
        }
        writer.close();
    }
}
