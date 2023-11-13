import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * DocumentPreprocess
 * preprocesses the documents by removing stop words and punctuation
 */
public class DocumentPreprocess {

    /**
     * processes file and publishes processed version to output folder
     *
     * @param absoluteFilePath String
     * @return String
     */
    public static String processing(String absoluteFilePath) {

        String fileName = Helpers.extractFileName(absoluteFilePath);
        String outputFilePath;
        if (Helpers.extractFileExtension(absoluteFilePath).contains("copy"))
            outputFilePath = "data/preprocessed_files/output_" + fileName + Helpers.extractFileExtension(absoluteFilePath);
        else outputFilePath = "data/preprocessed_files/output_" + fileName + ".txt";

        try {
            // pre defined words to remove as well as punctuation to remove
            Set<String> stopwords = new HashSet<>();
            stopwords.add("the");
            stopwords.add("are");
            stopwords.add("The");
            stopwords.add("Are");

            String punctuation = ".,:;'";
            // create new output file and read in file
            BufferedReader reader = new BufferedReader(new FileReader(absoluteFilePath));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));
            String line;

            while ((line = reader.readLine()) != null) {
                //pre processing starts
                line = line.toLowerCase(); 
                line = line.replaceAll("[" + punctuation + "]", ""); 

                String[] words = line.split("\\s+");
                for (String word : words) {
                    // check if the word is a stop word or a short word to remove
                    if (word != null && !stopwords.contains(word) && word.length() > 2) {
                        writer.write(word + " "); 
                    }
                }

                writer.newLine();
            }
            //pre processing ends
            reader.close();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputFilePath;
    }

    /**
     * clears the output folder
     */
    public static void clearOutputFolder() {
        System.out.println("Clearing output folder...");
        File folder = new File("data/preprocessed_files");
        File[] files = folder.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            file.delete();
        }
    }
}
