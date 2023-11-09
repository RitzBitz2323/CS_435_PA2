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
    }

    /**
     * Returns the exact Jaccard similarity of two files
     * computes the Jaccard similarity of file1 and file2 using `this.termDocumentMatrix`
     * @param file1 String
     * @param file2 String
     * @return double
     */
    public double exactJaccard(String file1, String file2) {
        System.out.printf("Computing exact Jaccard Similarity between %s and %s...\n", file1, file2);

        int[] file1TermFrequencyVector = this.termFreqVector(file1);
        int[] file2TermFrequencyVector = this.termFreqVector(file2);

        int union = 0;
        int intersection = 0;

        for (int i = 0; i < minHash.allTerms().length; i++) {
            if (file1TermFrequencyVector[i] == 1 || file2TermFrequencyVector[i] == 1)
                union++;
            if (file1TermFrequencyVector[i] == 1 && file2TermFrequencyVector[i] == 1)
                intersection++;
        }

        return (double) intersection / union;
    }

    /**
     * Returns the estimated Jaccard similarity of two files
     * Compares MinHash signatures of file1 and file2 using `this.minHashMatrix`
     * @param file1 String
     * @param file2 String
     * @return double
     */
    private double approximateJaccard(String file1, String file2) {
        System.out.printf("Computing estimated Jaccard Similarity between %s and %s...\n", file1, file2);

        int[] file1MinHashSig = this.minHashSig(file1);
        int[] file2MinHashSig = this.minHashSig(file2);
        int matchCount = 0;

        for (int i = 0; i < minHash.numPermutations(); i++) {
            if (file1MinHashSig[i] == file2MinHashSig[i])
                matchCount++;
        }
        return (double) matchCount / minHash.numPermutations();
    }

    /**
     * Returns the MinHash signature of a document named fileName
     *
     * @param fileName String
     * @return int[]
     */
    private int[] minHashSig(String fileName) {
        int fileIndex = IntStream.range(0, this.minHash.allDocs().length)
                .filter(i -> ("data/preprocessed_files/output_" + fileName).equals(this.minHash.allDocs()[i]))
                .findFirst()
                .orElse(-1);

        return this.minHashMatrix[fileIndex];
    }

    /**
     * Returns the TermFrequency vector of a document named fileName
     *
     * @param fileName String
     * @return int[]
     */
    private int[] termFreqVector(String fileName) {
        int fileIndex = IntStream.range(0, this.minHash.allDocs().length)
                .filter(i -> ("data/preprocessed_files/output_" + fileName).equals(this.minHash.allDocs()[i]))
                .findFirst()
                .orElse(-1);

        return this.termDocumentMatrix[fileIndex];
    }

    public static void main(String[] args) {
        MinHashSimilarities minHashSimilarities = new MinHashSimilarities("data/articles", 50);
        /**
         * Input the names of the files you want to compare, e.g. "baseball0.txt", "baseball1.txt"
         * The method will prepend the appropriate relative path declaration on the fly
         */
        System.out.println(minHashSimilarities.approximateJaccard("baseball0.txt", "baseball2.txt"));
        System.out.println(minHashSimilarities.exactJaccard("baseball0.txt", "baseball2.txt"));
    }
}
