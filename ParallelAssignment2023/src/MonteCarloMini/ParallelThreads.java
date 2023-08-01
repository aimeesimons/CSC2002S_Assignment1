package MonteCarloMini;

import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ParallelThreads extends RecursiveAction {

    private int hi, lo;
    private Search[] arr;

    public ParallelThreads(Search[] arr, int lo, int num_searches) {
        this.arr = arr;
        this.lo = lo;
        this.hi = num_searches;

    }

    @Override
    protected void compute() {
        if ((hi - lo) <= 3) {
            int local_min = Integer.MAX_VALUE;
            int finder = -1;
            for (int i = lo; i < hi; i++) {
                if (arr[i].find_valleys() < local_min) {
                    local_min = arr[i].find_valleys();
                    finder = i;

                }
            }
            MonteCarloMinimizationParallel.Values.add(new PairedThreads(local_min, finder));

        } else {
            int split = (int) ((hi - lo) / 2.0);
            ParallelThreads left = new ParallelThreads(arr, lo, split);
            ParallelThreads right = new ParallelThreads(arr, split, hi);
            left.fork();
            right.compute();
            left.join();

        }

    }
}
