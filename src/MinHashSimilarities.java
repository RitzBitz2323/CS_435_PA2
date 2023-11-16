import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Invokes `MinHash` to compute and estimate similarities
 */
public class MinHashSimilarities {
    /**
     * MinHash singleton
     */
    private MinHash minHash;
    /**
     * TermDocumentMatrix integer array
     */
    private int[][] termDocumentMatrix;

    /**
     * MinHashMatrix integer array
     */
    private int[][] minHashMatrix;

    private ArrayList<String> allDocuments = new ArrayList<>();

    /**
     * Constructs a MinHashSimilarities object
     * <p>
     * Creates an instance of MinHash and stores termDocumentMatrix and minHashMatrix instances
     *
     * @param folder          String
     * @param numPermutations int
     */
    public MinHashSimilarities(String folder, int numPermutations) {
        this.minHash = new MinHash(folder, numPermutations);

        this.termDocumentMatrix = this.minHash.termDocumentMatrix();
        this.minHashMatrix = this.minHash.minHashMatrix();

        this.allDocuments = new ArrayList<>(List.of(this.minHash.allDocs()));
    }

    /**
     * Returns the exact Jaccard similarity of two files
     * computes the Jaccard similarity of file1 and file2 using `this.termDocumentMatrix`
     *
     * @param file1 String
     * @param file2 String
     * @return double
     */
    public double exactJaccard(String file1, String file2) {
//        System.out.printf("Computing exact Jaccard Similarity between %s and %s...\n", file1, file2);

        int[] file1TermFrequencyVector = this.termFreqVector(file1);
        int[] file2TermFrequencyVector = this.termFreqVector(file2);

        int union = 0;
        int intersection = 0;


        long startTime = System.nanoTime();
//        System.out.printf("Fetching union and intersection between %s and %s...\n", file1, file2);
        int numTerms = this.minHash().numTerms();
        for (int i = 0; i < numTerms; i++) {
            union += Math.max(file1TermFrequencyVector[i], file2TermFrequencyVector[i]);
            intersection += Math.min(file1TermFrequencyVector[i], file2TermFrequencyVector[i]);
        }
        long endTime = System.nanoTime();
//        System.out.printf("ACTUAL FOR-LOOP COMPLETE in %dms\n", (endTime - startTime) / 1000000);

        return (double) intersection / union;
    }

    /**
     * Returns the TermFrequency vector of a document named fileName
     *
     * @param fileName String
     * @return int[]
     */
    private int[] termFreqVector(String fileName) {
        int i = this.allDocuments.indexOf(fileName);
        if (i == -1) {
            System.err.println("Document name not found: " + fileName);
        }
        return this.termDocumentMatrix[i];
    }

    /**
     * Returns the estimated Jaccard similarity of two files
     * Compares MinHash signatures of file1 and file2 using `this.minHashMatrix`
     *
     * @param file1 String
     * @param file2 String
     * @return double
     */
    public double approximateJaccard(String file1, String file2) {
//        System.out.printf("Computing estimated Jaccard Similarity between %s and %s...\n", file1, file2);

        int[] file1MinHashSig = this.minHashSig(file1);
        int[] file2MinHashSig = this.minHashSig(file2);
        int matchCount = 0;

        long startTime = System.nanoTime();
        for (int i = 0; i < minHash.numPermutations(); i++) {
            if (file1MinHashSig[i] == file2MinHashSig[i])
                matchCount++;
        }
        long endTime = System.nanoTime();
//        System.out.printf("APPROXIMATE FOR-LOOP COMPLETE in %dms\n", (endTime - startTime) / 1000000);
        return (double) matchCount / minHash.numPermutations();
    }

    /**
     * Returns the MinHash signature of a document named fileName
     *
     * @param fileName String
     * @return int[]
     */
    private int[] minHashSig(String fileName) {
        int i = this.allDocuments.indexOf(fileName);
        if (i == -1) {
            System.err.println("Document name not found: " + fileName);
        }
        return this.minHashMatrix[i];
    }

    public MinHash minHash() {
        return this.minHash;
    }

    public static void main(String[] args) {
        MinHashSimilarities minHashSimilarities = new MinHashSimilarities("/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/articles", 400);

        System.out.println("Approximate Similarity: " + minHashSimilarities.approximateJaccard("/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/articles/baseball0.txt", "/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/articles/baseball1.txt"));
        System.out.println("Actual Similarity: " + minHashSimilarities.exactJaccard("/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/articles/baseball0.txt", "/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/articles/baseball1.txt"));
        System.out.println();
        System.out.println("Approximate Similarity: " + minHashSimilarities.approximateJaccard("/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/articles/baseball0.txt", "/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/articles/baseball2.txt"));
        System.out.println("Actual Similarity: " + minHashSimilarities.exactJaccard("/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/articles/baseball0.txt", "/Users/shivneelakantan/Desktop/CS435/CS_435_PA2/data/articles/baseball2.txt"));
        System.out.println();
    }
}
