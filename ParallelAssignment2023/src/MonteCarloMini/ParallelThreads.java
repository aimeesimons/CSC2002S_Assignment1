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

    public ParallelThreads(Search[] arr, int lo, int num_searches, ConcurrentLinkedDeque<PairedThreads> Values) {
        this.arr = arr;
        this.lo = lo;
        this.hi = num_searches;
        this.Vals = Values;
        this.finder = -1;

    }

    @Override
    protected void compute() {
        if ((hi - lo) <= 10000) {
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
            ParallelThreads left = new ParallelThreads(arr, lo, split, Vals);
            ParallelThreads right = new ParallelThreads(arr, split, hi, Vals);
            left.fork();
            right.compute();
            left.join();

        }

    }
}
