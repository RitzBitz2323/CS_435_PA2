/**
 * IntegerPair class
 */
public class IntegerPair {

    private int param1;

    private int param2;

    public IntegerPair(int param1, int param2) {
        this.param1 = param1;
        this.param2 = param2;
    }

    public int getParam1() {
        return param1;
    }

    public int getParam2() {
        return param2;
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null)
            return false;
        if (o.getClass() != getClass())
            return false;
        IntegerPair other = (IntegerPair) o;
        return param1 == other.param1 && param2 == other.param2;
    }

    @Override
    public String toString() {
        return "IntegerPair (" + param1 + ", " + param2 + ")";
    }
}
