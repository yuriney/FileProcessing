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
        String inputFile = "C:\\Users\\icabi\\Documents\\MP - DOCS\\Automações e Scripts\\Test\\Splitting\\normalizer.txt";
        String outputFile = "C:\\Users\\icabi\\Documents\\MP - DOCS\\Automações e Scripts\\Test\\Splitting\\resultado.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            FileWriter writer = new FileWriter(outputFile);

            String line;
            while ((line = reader.readLine()) != null) {
                String normalizedLine = normalizeText(line);
                writer.write(normalizedLine + "\n");
            }

            reader.close();
            writer.close();
            System.out.println("Normalization and writing completed.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            List<String> paths = readPathsFromFile(inputFile);

            List<String> title_filtra = new ArrayList<>();


            for (String path : paths) {
                path = new String(path.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);


                String uppercasePath = path.replaceAll("-"," ").toUpperCase();
                uppercasePath.replaceAll("[^a-zA-Z0-9]", " ");

                int lastBackslashIndex = uppercasePath.lastIndexOf("\\");
                if (lastBackslashIndex >= 0) {
                    uppercasePath = uppercasePath.substring(0, lastBackslashIndex);
                }

                title_filtra.add(uppercasePath);
            }

            writeOutcomeToFile("C:\\Users\\icabi\\Documents\\MP - DOCS\\Automações e Scripts\\Test\\Splitting\\title_filtra.txt", title_filtra);

            System.out.println("Outcome files generated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String normalizeText(String text) {
        String normalizedText = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizedText).replaceAll("");
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
