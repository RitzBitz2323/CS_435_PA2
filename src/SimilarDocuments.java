
import java.util.*;


/**
 * This class detects near duplicates in a document collection using LSH.
 */
public class SimilarDocuments {
    private LSH lsh;
    private MinHash minhash;

    private double similarityThreshold;

    /**
     * Constructor for SimilarDocuments class
     * @param nameOfFolder String
     * @param permutations int
     * @param simThreshold double
     */
    public SimilarDocuments(String nameOfFolder, int permutations, double simThreshold) {
        this.minhash = new MinHash(nameOfFolder, permutations);
        this.similarityThreshold = simThreshold;
        int rowsPerBand = (int) Math.ceil(-permutations / (Math.log(1 - Math.pow(simThreshold, 1.0 / permutations))));
        this.lsh = new LSH(minhash.minHashMatrix(), minhash.allDocs(), rowsPerBand);
    }

    // calls lsh.retrieve and prints out all similar documents
    public ArrayList<String> similaritySearch(String docName) {
        ArrayList<String> resultList = lsh.retrieveSim(docName);
        System.out.printf("The following documents have similarity >= %.2f with %s:\n", this.similarityThreshold, docName);
        for (String s : resultList) {
            System.out.println("\t" + s);
        }
        System.out.println();
        System.out.println();
        return resultList;
    }

    public static void main(String[] args) {
        SimilarDocuments similarDocumentsTest = new SimilarDocuments("/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset", 400, 0.5);
        similarDocumentsTest.similaritySearch("/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-9.txt.copy1");
        similarDocumentsTest.similaritySearch("/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-86.txt");
        similarDocumentsTest.similaritySearch("/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-87.txt");
        similarDocumentsTest.similaritySearch("/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-88.txt");
        similarDocumentsTest.similaritySearch("/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-89.txt");

        SimilarDocuments similarDocumentsTest2 = new SimilarDocuments("/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset", 600, 0.7);
        similarDocumentsTest2.similaritySearch("/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-989.txt");
        similarDocumentsTest2.similaritySearch("/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-988.txt");
        similarDocumentsTest2.similaritySearch("/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-987.txt");
        similarDocumentsTest2.similaritySearch("/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-986.txt");
        similarDocumentsTest2.similaritySearch("/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/LSH_Data_Subset/space-985.txt");
    }

}