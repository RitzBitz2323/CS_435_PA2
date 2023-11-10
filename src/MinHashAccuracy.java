/**
 * Tests how accurately MinHash matrix can be used to estimate Jaccard Similarity
 */
public class MinHashAccuracy {
    private MinHashSimilarities minHashSimilarities400Permutations = null;

    private MinHashSimilarities minHashSimilarities600Permutations = null;

    private MinHashSimilarities minHashSimilarities800Permutations = null;

    public MinHashAccuracy() {
    }

    private int accuracy(String folder, int numPermutations, double epsilon) {
        MinHashSimilarities minHashSimilarities = null;

        // logic to avoid reconstruction of same minHashSimilarities object
        if (numPermutations == 400) {
            if (minHashSimilarities400Permutations == null) {
                minHashSimilarities400Permutations = new MinHashSimilarities(folder, numPermutations);
            }
            minHashSimilarities = minHashSimilarities400Permutations;
        } else if (numPermutations == 600) {
            if (minHashSimilarities600Permutations == null) {
                minHashSimilarities600Permutations = new MinHashSimilarities(folder, numPermutations);
            }
            minHashSimilarities = minHashSimilarities600Permutations;
        } else if (numPermutations == 800) {
            if (minHashSimilarities800Permutations == null) {
                minHashSimilarities800Permutations = new MinHashSimilarities(folder, numPermutations);
            }
            minHashSimilarities = minHashSimilarities800Permutations;
        }
        int i, j;
        int numPairs = 0;
        int numDocs = minHashSimilarities.minHash().allDocs().length;
        for (i = 0; i < numDocs; i++) {
            long startTime = System.currentTimeMillis();
            for (j = i + 1; j < numDocs; j++) {
                double exact = minHashSimilarities.exactJaccard(minHashSimilarities.minHash().allDocs()[i], minHashSimilarities.minHash().allDocs()[j]);
                double approximate = minHashSimilarities.approximateJaccard(minHashSimilarities.minHash().allDocs()[i], minHashSimilarities.minHash().allDocs()[j]);

                if (Math.abs(exact - approximate) > epsilon)
                    numPairs++;
            }
            long endTime = System.currentTimeMillis();
            System.out.printf("Time taken to compute exact and approximate Jaccard for file %s: %dms\n", minHashSimilarities.minHash().allDocs()[i], endTime - startTime);
            System.out.printf("%d Documents remain to compare with for run: (numPermutations=%d, epsilon=%f)\n", numDocs - i - 1, numPermutations, epsilon);
        }
        return numPairs;
    }

    public static void main(String[] args) {
        MinHashAccuracy minHashAccuracy = new MinHashAccuracy();
        long[] runtimes = new long[9];
        int[] accuracies = new int[9];

        int[] numPermutations = new int[3];
        numPermutations[0] = 400;
        numPermutations[1] = 600;
        numPermutations[2] = 800;

        double[] epsilons = new double[3];
        epsilons[0] = 0.04;
        epsilons[1] = 0.07;
        epsilons[2] = 0.09;

        long totalStartTime = System.currentTimeMillis();
        for (int permutationIndex = 0; permutationIndex < 3; permutationIndex++) {
            for (int epsilonIndex = 0; epsilonIndex < 3; epsilonIndex++) {
                long startTime = System.currentTimeMillis();
                accuracies[permutationIndex * 3 + epsilonIndex] = minHashAccuracy.accuracy("data/space", numPermutations[permutationIndex], epsilons[epsilonIndex]);
                long endTime = System.currentTimeMillis();
                runtimes[permutationIndex * 3 + epsilonIndex] = (endTime - startTime)/1000;
            }
        }
        long totalEndTime = System.currentTimeMillis();

        System.out.println("Number of bad approximations: ");
        System.out.format("%s%20s%10s%10s%n", "Epsilon", "0.04", "0.07", "0.09");
        System.out.format("%s%10d%10d%10d%n", "400 permutations:", accuracies[0], accuracies[1], accuracies[2]);
        System.out.format("%s%10d%10d%10d%n", "600 permutations:", accuracies[3], accuracies[4], accuracies[5]);
        System.out.format("%s%10d%10d%10d%n", "800 permutations:", accuracies[6], accuracies[7], accuracies[8]);

        System.out.println("\nRuntimes (s): ");
        System.out.format("%s%20s%10s%10s%n", "Epsilon", "0.04", "0.07", "0.09");
        System.out.format("%s%10d%10d%10d%n", "400 permutations:", runtimes[0], runtimes[1], runtimes[2]);
        System.out.format("%s%10d%10d%10d%n", "600 permutations:", runtimes[3], runtimes[4], runtimes[5]);
        System.out.format("%s%10d%10d%10d%n", "800 permutations:", runtimes[6], runtimes[7], runtimes[8]);

        System.out.println("\nTotal Runtime: " + (float)((totalEndTime - totalStartTime)/1000) + "s");


    }
}
