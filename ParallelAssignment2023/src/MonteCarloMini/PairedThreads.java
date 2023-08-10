package MonteCarloMini;

/**
 * This class contains a specific local minimum found and its index.
 */
public class PairedThreads {
    public int min;
    public int index;

    /**
     * This is the constructor for the PairedThreads class
     * 
     * @param min   the local minimum found
     * @param index the index of that minimum
     * 
     */
    public PairedThreads(int min, int index) {
        this.min = min;
        this.index = index;
    }
}
