import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class MinHash {
    private String folder;
    private int numPermutations;
    private ArrayList<String> allDocs;
    private int[][] minHashMatrix;
    private int[][] termDocumentMatrix;
    private int permutationDomain;

    public MinHash(String folder, int numPermutations) {
        this.folder = folder;
        this.numPermutations = numPermutations;
        allDocs = new ArrayList<>();
        initializeDocumentList();
        initializeTermDocumentMatrix();
        initializeMinHashMatrix();
    }

    public String[] allDocs() {
        return allDocs.toArray(new String[0]);
    }

    public int[][] minHashMatrix() {
        return minHashMatrix;
    }

    public int[][] termDocumentMatrix() {
        return termDocumentMatrix;
    }

    public int permutationDomain() {
        return permutationDomain;
    }

    public int numPermutations() {
        return numPermutations;
    }

    private void initializeDocumentList() {
        File folderDir = new File(folder);
        File[] files = folderDir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    allDocs.add(file.getName());
                }
            }
        }
    }

    private void initializeTermDocumentMatrix() {
        termDocumentMatrix = new int[allDocs.size()][permutationDomain];
        // Implement logic to create the term-document matrix from your collection of documents
        // Populate termDocumentMatrix accordingly
    }

    private void initializeMinHashMatrix() {
        minHashMatrix = new int[numPermutations][allDocs.size()];
        // Implement logic to create the MinHash matrix using permutations
        // Populate minHashMatrix accordingly
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
