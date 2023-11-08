import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Permutation class
 * stores parameters for computing permutations for min hashing
 */
public class Permutation {
    /** list of integer pairs (a, b) used for hashing */
    private List<IntegerPair> parameters = new ArrayList<>();

    private int prime;

    private int numPermutations;

    /**
     * Default constructor
     *
     * @param numPermutations number of permutations to compute
     * @param prime           prime number used for hashing
     */
    public Permutation(int numPermutations, int prime) {
        this.numPermutations = numPermutations;
        this.prime = prime;
    }

    public List<IntegerPair> getParameters() {
        return parameters;
    }

    public int getPrime() {
        return prime;
    }

    public void generateRandomConstants() {
        Random random = new Random();

        while (parameters.size() < numPermutations) {
            int param1 = random.nextInt(prime);
            int param2 = random.nextInt(prime);

            IntegerPair pair = new IntegerPair(param1, param2);

            if (!parameters.contains(pair)) {
                parameters.add(pair);
            }
        }
    }
}
