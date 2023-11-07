import jdk.jshell.execution.Util;

import java.util.Arrays;
import java.util.List;

public class MinHashMatrix {

    private int numPermutations;

    private TermDocumentMatrix termDocumentMatrix;

    private int[][] minHashMatrix;

    private Permutation permutation;

    public MinHashMatrix(TermDocumentMatrix termDocumentMatrix, int numPermutations) {
        this.termDocumentMatrix = termDocumentMatrix;
        this.numPermutations = numPermutations;

        List<String> allDocuments = termDocumentMatrix.getDocuments();
        List<String> allTerms = termDocumentMatrix.getTerms();
        int M = allTerms.size();

        int prime = Helpers.findNextPrime(M);

        this.permutation = new Permutation(numPermutations, prime);
        this.permutation.generateRandomConstants();

        minHashMatrix = new int[allDocuments.size()][numPermutations];
        int[][] tdMatrix = this.termDocumentMatrix.getTermDocumentMatrix();

        permutation = new Permutation(numPermutations, prime);
        permutation.generateRandomConstants();

        int documentIndex, parameterIndex;

        // loop over each document in the term-document matrix
        for (documentIndex = 0; documentIndex < tdMatrix.length; documentIndex++) {
            // loop over each hash function h_i
            for (parameterIndex = 0; parameterIndex < permutation.getParameters().size(); parameterIndex++) {
                int minimum = Integer.MAX_VALUE;
                IntegerPair integerPair = permutation.getParameters().get(parameterIndex);

                // loop over each term in the term-document matrix
                for (int termIndex = 0; termIndex < tdMatrix[documentIndex].length; termIndex++) {

                    int termFrequency = tdMatrix[documentIndex][termIndex];

                    if (termFrequency > 0) {
                        int hashCode = allTerms.get(termIndex).hashCode();
                        int hashValue = (integerPair.getParam1() * hashCode + integerPair.getParam2()) % permutation.getPrime();

                        if (hashValue < minimum) {
                            minimum = hashValue;
                        }
                    }

                    minHashMatrix[documentIndex][parameterIndex] = minimum;
                }
            }
        }
    }

    public int[][] getMinHashMatrix() {
        return minHashMatrix;
    }

    public int getNumPermutations() {
        return numPermutations;
    }
}
