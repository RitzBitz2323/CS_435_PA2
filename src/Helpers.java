import java.util.ArrayList;

public class Helpers {

    /**
     * Returns the minimum of a list of integers
     *
     * @param num1
     * @param num2
     * @return int
     */
    public static int min(int num1, int num2) {
        return Math.min(num1, num2);
    }
    
    
    /**
     * Returns the next prime number after n
     * @param n number to start searching from
     * @return int
     */
    public static int findNextPrime(int n) {
        int prime = n;
        while (true) {
            if (isPrime(prime)) {
                return prime;
            }
            prime++;
        }
    }

    private static boolean isPrime(int num) {
        if (num == 2) {
            return true;
        }
        if (num % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i <= num; i += 2) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
