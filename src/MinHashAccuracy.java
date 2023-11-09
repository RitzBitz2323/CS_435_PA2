/**
 * Tests how accurately MinHash matrix can be used to estimate Jaccard Similarity
 */
public class MinHashAccuracy {
    public MinHashAccuracy() {
    }

    private int accuracy(String folder, int numPermutations, double epsilon) {
        MinHashSimilarities minHashSimilarities = new MinHashSimilarities(folder, numPermutations);

        int i, j;
        int numPairs = 0;
        for (i = 0; i < minHashSimilarities.minHash().allDocs().length; i++) {
            for (j = i + 1; j < minHashSimilarities.minHash().allDocs().length; j++) {
                double exact = minHashSimilarities.exactJaccard(minHashSimilarities.minHash().allDocs()[i], minHashSimilarities.minHash().allDocs()[j]);
                double approximate = minHashSimilarities.approximateJaccard(minHashSimilarities.minHash().allDocs()[i], minHashSimilarities.minHash().allDocs()[j]);

                if (Math.abs(exact - approximate) > epsilon)
                    numPairs++;
            }
        }
        return numPairs;
    }

    public static void main(String[] args) {
        MinHashAccuracy minHashAccuracy = new MinHashAccuracy();
        int[] accuraciesFor400Permutations = new int[3];
        accuraciesFor400Permutations[0] = minHashAccuracy.accuracy("data/space", 400, 0.04);
        accuraciesFor400Permutations[1] = minHashAccuracy.accuracy("data/space", 400, 0.07);
        accuraciesFor400Permutations[2] = minHashAccuracy.accuracy("data/space", 400, 0.09);

        int[] accuraciesFor600Permutations = new int[3];
        accuraciesFor600Permutations[0] = minHashAccuracy.accuracy("data/space", 600, 0.04);
        accuraciesFor600Permutations[1] = minHashAccuracy.accuracy("data/space", 600, 0.07);
        accuraciesFor600Permutations[2] = minHashAccuracy.accuracy("data/space", 600, 0.09);

        int[] accuraciesFor800Permutations = new int[3];
        accuraciesFor800Permutations[0] = minHashAccuracy.accuracy("data/space", 800, 0.04);
        accuraciesFor800Permutations[1] = minHashAccuracy.accuracy("data/space", 800, 0.07);
        accuraciesFor800Permutations[2] = minHashAccuracy.accuracy("data/space", 800, 0.09);

        // Print table rows
        System.out.format("%s%20s%10s%10s%n", "Epsilon", "0.04", "0.07", "0.09");
        System.out.format("%s%10d%10d%10d%n", "400 permutations:", accuraciesFor400Permutations[0], accuraciesFor400Permutations[1], accuraciesFor400Permutations[2]);
        System.out.format("%s%10d%10d%10d%n", "600 permutations:", accuraciesFor600Permutations[0], accuraciesFor600Permutations[1], accuraciesFor600Permutations[2]);
        System.out.format("%s%10d%10d%10d%n", "800 permutations:", accuraciesFor800Permutations[0], accuraciesFor800Permutations[1], accuraciesFor800Permutations[2]);


    }
}
