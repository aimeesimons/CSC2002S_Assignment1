package MonteCarloMini;

// import java.security.PublicKey;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.RecursiveAction;

/* M. Kuttel 2023
 * SearchParalleler class that lands somewhere random on the surfaces and 
 * then moves downhill, stopping at the local minimum.
 */

public class SearchParallel extends RecursiveAction {
	private int id; // SearchParallel identifier
	private int pos_row, pos_col; // Position in the grid
	private int steps; // number of steps to end of SearchParallel
	private boolean stopped; // Did the SearchParallel hit a previous trail?
	private int hi, lo;
	private static SearchParallel[] arr;
	int tmp;
	public ConcurrentLinkedDeque<PairedThreads> Vals;
	public int finder;
	int num_searches;

	private TerrainArea terrain;

	public SearchParallel(int id, int pos_row, int pos_col, TerrainArea terrain) {
		this.id = id;
		this.pos_row = pos_row; // randomly allocated
		this.pos_col = pos_col; // randomly allocated
		this.terrain = terrain;
		this.stopped = false;
	}

	public SearchParallel(SearchParallel[] arr, int lo, int hi, ConcurrentLinkedDeque<PairedThreads> Values,
			int num_SearchParalleles) {
		this.arr = arr;
		this.lo = lo;
		this.hi = hi;
		this.Vals = Values;
		this.finder = -1;
		this.num_searches = num_SearchParalleles;
	}

	public int find_valleys() {
		int height = Integer.MAX_VALUE;
		Direction next = Direction.STAY_HERE;
		while (terrain.visited(pos_row, pos_col) == 0) { // stop when hit existing path
			height = terrain.get_height(pos_row, pos_col);
			terrain.mark_visited(pos_row, pos_col, id); // mark current position as visited
			steps++;
			next = terrain.next_step(pos_row, pos_col);
			switch (next) {
				case STAY_HERE:
					return height; // found local valley
				case LEFT:
					pos_row--;
					break;
				case RIGHT:
					pos_row = pos_row + 1;
					break;
				case UP:
					pos_col = pos_col - 1;
					break;
				case DOWN:
					pos_col = pos_col + 1;
					break;
			}
		}
		stopped = true;
		return height;
	}

	public int getID() {
		return id;
	}

	public int getPos_row() {
		return pos_row;
	}

	public int getPos_col() {
		return pos_col;
	}

	public int getSteps() {
		return steps;
	}

	public boolean isStopped() {
		return stopped;
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
			if (finder != -1) {
				Vals.add(new PairedThreads(local_min, finder));
			}

		} else {
			int split = (int) ((hi + lo) / 2.0);
			SearchParallel left = new SearchParallel(arr, lo, split, Vals, num_searches);
			SearchParallel right = new SearchParallel(arr, split, hi, Vals, num_searches);
			left.fork();
			right.compute();
			left.join();

		}

	}

}
