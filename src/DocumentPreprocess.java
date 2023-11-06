import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DocumentPreprocess {

    public String processing(String exact_inputFile){
        /**
         * Example input file string:
         * String inputFilePath = "/Users/rambekar/CS_435/CS435_PA2/CS_435_PA2/data/space/space-0.txt"; 
         * Reference dp_test.java for more info on usage
        **/
        String inputFilePath = exact_inputFile;
        // separates name of file and outputs it into new file
        int lastSeparatorIndex = inputFilePath.lastIndexOf(File.separator);
        System.out.println(lastSeparatorIndex);
        int lastPeriodIndex = inputFilePath.lastIndexOf(".");
        System.out.println(lastPeriodIndex);
        String fileName = inputFilePath.substring(lastSeparatorIndex + 1, lastPeriodIndex);
        String outputFilePath = "/Users/rambekar/CS_435/CS435_PA2/CS_435_PA2/data/preprocessed_files/output_" + fileName + ".txt"; 

        try {
            // pre defined words to remove as well as punctuation to remove
            Set<String> stopwords = new HashSet<>();
            stopwords.add("the");
            stopwords.add("are");
            String punctuation = ".,:;'";
            // create new output file and read in file
            BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));
            String line;

            while ((line = reader.readLine()) != null) {
                //pre processing starts
                line = line.toLowerCase(); 
                line = line.replaceAll("[" + punctuation + "]", ""); 

                String[] words = line.split("\\s+");
                for (String word : words) {
                    // check if the word is a stop word or a short word to remove
                    if (!stopwords.contains(word) && word.length() > 2) {
                        writer.write(word + " "); 
                    }
                }

                writer.newLine();
            }
            //pre processing ends
            reader.close();
            writer.close();

            System.out.println("Text preprocessing completed. Preprocessed text saved to " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputFilePath;
    }
}
