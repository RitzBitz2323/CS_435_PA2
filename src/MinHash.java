import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class MinHash {
    private String folder;
    private int numPermutations;
    private ArrayList<String> allDocuments;

    private MinHashMatrix mhMatrix;
    private TermDocumentMatrix tdMatrix;
    private int[][] minHashMatrix;
    private int[][] termDocumentMatrix;
    private int permutationDomain;

    /**
     * number of terms (columns) in the term document matrix
     */
    private int M;

    /**
     * number of documents (rows) in the term document matrix
     */
    private int N;

    public MinHash(String folder, int numPermutations) {
        this.folder = folder;
        this.numPermutations = numPermutations;

        allDocuments = new ArrayList<>();

        this.tdMatrix = new TermDocumentMatrix();
        initializeTdMatrix();

        this.mhMatrix = new MinHashMatrix(tdMatrix, numPermutations);

        this.M = tdMatrix.numTerms();
        this.N = tdMatrix.numDocuments();

        this.termDocumentMatrix = tdMatrix.getTermDocumentMatrix();
        this.minHashMatrix = mhMatrix.getMinHashMatrix();

        this.allDocuments = new ArrayList<>(tdMatrix.getDocuments());
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

    public int permutationDomain() {
        return this.permutationDomain;
    }

    public int numPermutations() {
        return this.numPermutations;
    }

    /**
     * Initializes the list of documents in the folder
     * inserts them into term document matrix
     */
    private void initializeTdMatrix() {
        File folderDir = new File(folder);
        File[] files = folderDir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    allDocuments.add(file.getName());
                    this.tdMatrix.loadTermsIntoTdMatrix(file.getName(), getTermsFromFile(file));
                }
            }
        }
    }

    /**
     * Returns a list of terms from a file
     * @param file
     * @return `List<String>`
     */
    private static List<String> getTermsFromFile(File file) {
        ArrayList<String> terms = new ArrayList<>();
        try {
            Stream<String> lines = Files.lines(Paths.get(file.getPath()));
            lines.forEachOrdered(line -> {
                String[] words = line.split(" ");
                terms.addAll(Arrays.asList(words));
            });
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return terms;
    }


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


    public static void main(String[] args) {
        // Example usage of the MinHash class
        MinHash minHash = new MinHash("CS435_PA2/CS_435_PA2/data/articles", 100);
        System.out.println("All Documents: ");
        String[] documents = minHash.allDocs();
        for (String document : documents) {
            System.out.println(document);
        }
    }
}
