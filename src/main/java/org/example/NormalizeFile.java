package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class NormalizeFile {
    public static void main(String[] args) {
        String inputFile = "C:\\Users\\icabi\\Documents\\MP - DOCS\\Automações e Scripts\\Test\\Splitting\\path.txt";
        String outputFile = "C:\\Users\\icabi\\Documents\\MP - DOCS\\Automações e Scripts\\Test\\Splitting\\normalized_path.txt";

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
    }

    public static String normalizeText(String text) {
        String normalizedText = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizedText).replaceAll("");
    }
}
