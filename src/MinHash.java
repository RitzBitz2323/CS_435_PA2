import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class MinHash {
    /**
     * path to the folder containing all the documents
     */
    private String folder;
    /**
     * number of permutations to be used
     */
    private int numPermutations;
    /**
     * list of all the documents in the folder
     */
    private ArrayList<String> allDocuments;
    /**
     * MinHashMatrix object
     */
    private MinHashMatrix mhMatrix;
    /**
     * TermDocumentMatrix object
     */
    private TermDocumentMatrix tdMatrix;
    /**
     * MinHashMatrix as a 2D array
     */
    private int[][] minHashMatrix;
    /**
     * TermDocumentMatrix as a 2D array
     */
    private int[][] termDocumentMatrix;
    /**
     * permutation domain size
     */
    private int permutationDomain;
    /**
     * number of terms (columns) in the term document matrix
     */

    public MinHash(String folder, int numPermutations) throws IOException {
        this.folder = folder;
        this.numPermutations = numPermutations;

        allDocuments = new ArrayList<>();

        this.tdMatrix = new TermDocumentMatrix();
        // initialize the document list and TD matrix
        initializeTdMatrix();

        this.mhMatrix = new MinHashMatrix(tdMatrix, numPermutations);

        this.termDocumentMatrix = tdMatrix.getTermDocumentMatrix();
        this.minHashMatrix = mhMatrix.getMinHashMatrix();

        this.allDocuments = new ArrayList<>(tdMatrix.getDocuments());
        this.permutationDomain = Helpers.findNextPrime(this.tdMatrix.numTerms());
    }

    /**
     * Initializes the list of documents in the folder
     * inserts them into term document matrix
     */
    private void initializeTdMatrix() throws IOException {
        DocumentPreprocess.clearOutputFolder();

        File folderDir = new File(this.folder);
        File[] files;
        if (!folderDir.exists()) {
            System.out.println("Directory does not exist: " + folderDir.getAbsolutePath());
        } else if (!folderDir.isDirectory()) {
            System.out.println("This is not a directory: " + folderDir.getAbsolutePath());
        } else {
            files = folderDir.listFiles();
            if (files == null) {
                System.out.println("Failed to list files for directory: " + folderDir.getAbsolutePath());
            } else {
                for (File file : files) {
                    if (file.isFile()) {
                        String preProcessedFileName = DocumentPreprocess.processing(file.getAbsolutePath());
                        allDocuments.add(preProcessedFileName);
                        this.tdMatrix.loadTermsIntoTdMatrix(preProcessedFileName, getTermsFromFile(new File(preProcessedFileName)));
                    }
                }
            }
        }
    }

    /**
     * Returns a list of terms from a file
     * @param file
     * @return `List<String>`
     */
    private static List<String> getTermsFromFile(File file) throws IOException {
        ArrayList<String> terms = new ArrayList<>();
        try {
            Stream<String> lines = Files.lines(Paths.get(file.getAbsolutePath()), StandardCharsets.ISO_8859_1);

            lines.forEachOrdered(line -> {
                String[] words = line.split(" ");
                terms.addAll(Arrays.asList(words));
            });
            return terms;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }


    /**
     * Generates a random permutation of integers from 0 to size
     * @param size
     * @return
     */
    private int[] generateRandomPermutation(int size) {
        ArrayList<Integer> permutation = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            permutation.add(i);
        }
        Random rand = new Random();
        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            int index = rand.nextInt(permutation.size());
            result[i] = permutation.get(index);
            permutation.remove(index);
        }

        return result;
    }

    public String[] allDocs() {
        return allDocuments.toArray(new String[0]);
    }

    public int[][] minHashMatrix() {
        return this.minHashMatrix;
    }

    public int[][] termDocumentMatrix() {
        return this.termDocumentMatrix;
    }

    /**
     * Returns the permutation domain size\
     * @return `int`
     */
    public int permutationDomain() {
        return this.permutationDomain;
    }

    public int numPermutations() {
        return this.numPermutations;
    }

    public static void main(String[] args) {
        // Example usage of the MinHash class
        try {
            MinHash minHash = new MinHash("data/articles", 100);
            System.out.println("All Documents: ");
            String[] documents = minHash.allDocs();
            for (String document : documents) {
                System.out.println(document);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

    }
}
