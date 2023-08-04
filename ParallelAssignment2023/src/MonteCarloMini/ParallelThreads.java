package MonteCarloMini;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ParallelThreads extends RecursiveAction {

    private int hi, lo;
    private Search[] arr;
    int tmp;
    public ConcurrentLinkedDeque<PairedThreads> Vals;
    public int finder;
    int num_searches;

    public ParallelThreads(Search[] arr, int lo, int hi, ConcurrentLinkedDeque<PairedThreads> Values,
            int num_searches) {
        this.arr = arr;
        this.lo = lo;
        this.hi = hi;
        this.Vals = Values;
        this.finder = -1;
        this.num_searches = num_searches;

    }

    @Override
    protected void compute() {
        if ((hi - lo) <= 0.1 * num_searches) {
            int local_min = Integer.MAX_VALUE;

            for (int i = lo; i < hi; i++) {
                tmp = arr[i].find_valleys();
                if ((!arr[i].isStopped()) && (tmp < local_min)) {
                    local_min = tmp;
                    finder = i;

                }
            }
            Vals.add(new PairedThreads(local_min, finder));

        } else {
            int split = (int) ((hi + lo) / 2.0);
            ParallelThreads left = new ParallelThreads(arr, lo, split, Vals, num_searches);
            ParallelThreads right = new ParallelThreads(arr, split, hi, Vals, num_searches);
            left.fork();
            right.compute();
            left.join();

        }

    }
}
