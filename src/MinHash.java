import java.io.File;
import java.io.IOException;
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
    private ArrayList<String> allDocuments = new ArrayList<>();
    /**
     * MinHashMatrix object
     */
    private MinHashMatrix mhMatrix;
    /**
     * TermDocumentMatrix object
     */
    private TermDocumentMatrix tdMatrix;
    /**
     * permutation domain size
     */
    private int permutationDomain;
    /**
     * number of terms (columns) in the term document matrix
     */

    private final boolean isTesting = true;

    /**
     * Constructor for MinHash class
     *
     * @param folder String
     * @param numPermutations int
     */
    public MinHash(String folder, int numPermutations) {
        DocumentPreprocess.clearOutputFolder();
        this.folder = folder;
        this.numPermutations = numPermutations;

        // initialize the document list and TD matrix
        this.tdMatrix = new TermDocumentMatrix();

        this.processFolder();

        this.permutationDomain = this.mhMatrix.getPermutationDomain();
        this.allDocuments = new ArrayList<>(this.tdMatrix.getDocuments());
    }

    /**
     * Returns a list of terms from a file
     *
     * @param file
     * @return `List<String>`
     */
    private static List<String> getTermsFromFile(File file) {
        ArrayList<String> allTermsInFile = new ArrayList<>();

        try {
            Stream<String> lines = Files.lines(Paths.get(file.getAbsolutePath()), StandardCharsets.ISO_8859_1);
            lines.forEachOrdered(line -> {
                String[] terms = line.split("\\s+");
                allTermsInFile.addAll(Arrays.asList(terms));
            });
            lines.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allTermsInFile;
    }

    /**
     * Processes the folder containing the documents
     */
    private void processFolder() {
        File documentFolder = new File(this.folder);
        if (!documentFolder.exists()) {
            System.out.println("Directory does not exist: " + documentFolder.getAbsolutePath());
            return;
        } else if (!documentFolder.isDirectory()) {
            System.out.println("This is not a directory: " + documentFolder.getAbsolutePath());
            return;
        }

        System.out.println("Pre-processing documents in folder " + documentFolder.getAbsolutePath() + "...");
        List<File> files = Arrays.asList(Objects.requireNonNull(documentFolder.listFiles()));

        for (File file : files) {
            if (file.isDirectory() || file.getName().equals(".DS_Store")) {
                continue;
            }
            String preProcessedFileName = DocumentPreprocess.processing(file.getAbsolutePath());
            this.allDocuments.add(preProcessedFileName);
            assert preProcessedFileName != null;
            this.tdMatrix.loadTermsIntoTdMatrix(preProcessedFileName, getTermsFromFile(new File(preProcessedFileName)));
        }

        System.out.printf("Pre-processed and generated Term-document matrix for %d documents.\n", this.allDocuments.size());

        System.out.printf("Generating MinHash matrix with %d permutations...\n", this.numPermutations);
        this.mhMatrix = new MinHashMatrix(this.tdMatrix, this.numPermutations);
        System.out.println("MinHash matrix generated.");
    }

    public String[] allDocs() {
        return allDocuments.toArray(new String[0]);
    }

    public String[] allTerms() {
        return this.tdMatrix.getTerms().toArray(new String[0]);
    }

    public int[][] minHashMatrix() {
        return this.mhMatrix.getMinHashMatrix();
    }

    public int[][] termDocumentMatrix() {
        return this.tdMatrix.getTermDocumentMatrix();
    }

    /**
     * Returns the permutation domain size\
     *
     * @return `int`
     */
    public int permutationDomain() {
        return this.permutationDomain;
    }

    public int numPermutations() {
        return this.numPermutations;
    }

    public static void main(String[] args) {
        DocumentPreprocess.clearOutputFolder();
        System.out.println("Cleared Output folder (data/preprocessed_files)");
        MinHash minHash = new MinHash("data/articles", 5);
        String[] documents = minHash.allDocs();

        Helpers.prettyPrintMatrix(
                "Ttermocument matrix",
                minHash.tdMatrix.getDocuments().size(),
                minHash.tdMatrix.getTerms().size(),
                minHash.termDocumentMatrix()
        );
    }
}
