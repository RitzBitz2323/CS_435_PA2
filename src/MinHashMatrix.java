import jdk.jshell.execution.Util;

import java.util.Arrays;
import java.util.List;

/**
 * This class represents the MinHash matrix.
 * It is a matrix of size (number of documents) x (number of permutations).
 * Each row represents a document and each column represents a permutation.
 */
public class MinHashMatrix {

    /**
     * number of permutations
     */
    private int numPermutations;

    /**
     * TermDocumentMatrix object
     */
    private TermDocumentMatrix termDocumentMatrix;

    /**
     * MinHashMatrix as a 2D array
     */
    private int[][] minHashMatrix;

    /**
     * Permutation object
     */
    private Permutation permutation;

    /**
     * permutation domain size
     */
    private int permutationDomain;

    /**
     * Constructs a MinHashMatrix object
     *
     * @param termDocumentMatrix
     * @param numPermutations
     */
    public MinHashMatrix(TermDocumentMatrix termDocumentMatrix, int numPermutations) {
        this.termDocumentMatrix = termDocumentMatrix;
        this.numPermutations = numPermutations;

        List<String> allDocuments = termDocumentMatrix.getDocuments();
        List<String> allTerms = termDocumentMatrix.getTerms();
        int M = allTerms.size();

        int prime = Helpers.findNextPrime(M);

        this.permutation = new Permutation(numPermutations, prime);

        minHashMatrix = new int[allDocuments.size()][numPermutations];
        int[][] tdMatrix = this.termDocumentMatrix.getTermDocumentMatrix();

        permutation = new Permutation(numPermutations, prime);
        this.permutationDomain = permutation.getParameters().size();

        int documentIndex, parameterIndex, termIndex;

        // looping over each document in the term-document matrix
        for (documentIndex = 0; documentIndex < tdMatrix.length; documentIndex++) {
            // looping over each hash function h_i
            for (parameterIndex = 0; parameterIndex < permutation.getParameters().size(); parameterIndex++) {
                // initializing minimum hash to infinity
                int minHashValue = Integer.MAX_VALUE;
                // looping over each term in the term-document matrix
                for (termIndex = 0; termIndex < tdMatrix[documentIndex].length; termIndex++) {
                    // frequency of this term at given document
                    int termFrequency = tdMatrix[documentIndex][termIndex];

                    if (termFrequency > 0) {
                        int hashCode = allTerms.get(termIndex).hashCode();

                        // (ax + b) % p, where x = hashCode
                        int hashValue = permutation.computeMinHash(hashCode, parameterIndex);
                        minHashValue = Helpers.min(hashValue, minHashValue);
                    }
                }
                this.minHashMatrix[documentIndex][parameterIndex] = minHashValue;
            }
        }
    }

    public int[][] getMinHashMatrix() {
        return minHashMatrix;
    }

    public int getNumDocuments() {
        return this.termDocumentMatrix.getDocuments().size();
    }

    public int getNumPermutations() {
        return numPermutations;
    }

    /**
     * Returns the length of the set of all terms occurring in D1 ∪ D2 ∪ ... ∪ Dn
     *
     * @return
     */
    public int getPermutationDomain() {
        return this.termDocumentMatrix.getTerms().size();
    }
}
