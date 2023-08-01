package MonteCarloMini;

import java.util.concurrent.RecursiveTask;

public class ParallelThreads extends RecursiveTask {

    private int hi, lo;
    private Search[] arr;

    public ParallelThreads(Search[] arr, int lo, int num_searches) {
        this.arr = arr;
        this.lo = lo;
        this.hi = num_searches;

    }

    @Override
    protected Object compute() {

        throw new UnsupportedOperationException("Unimplemented method 'compute'");
    }

}
