import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Permutation class
 * stores parameters for computing permutations for min hashing
 */
public class Permutation {
    /**
     * list of integer pairs (a, b) used for hashing
     */
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
        Random random = new Random();

        while (this.parameters.size() < this.numPermutations) {
            // ensuring that the parameters are no greater than the prime
            int param1 = random.nextInt(this.prime);
            int param2 = random.nextInt(this.prime);

            IntegerPair pair = new IntegerPair(param1, param2);

            if (!this.parameters.contains(pair)) {
                this.parameters.add(pair);
            }
        }
    }

    public int computeMinHash(int x, int parameterIndex) {
        int a = this.parameters.get(parameterIndex).getParam1();
        int b = this.parameters.get(parameterIndex).getParam2();
        return (a * x + b) % this.prime;
    }

    public List<IntegerPair> getParameters() {
        return this.parameters;
    }

    public int getPrime() {
        return this.prime;
    }

}
