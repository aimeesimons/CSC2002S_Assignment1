package MonteCarloMini;

import java.util.ArrayList;
/* Serial  program to use Monte Carlo method to 
 * locate a minimum in a function
 * This is the reference sequential version (Do not modify this code)
 * Michelle Kuttel 2023, University of Cape Town
 * Adapted from "Hill Climbing with Montecarlo"
 * EduHPC'22 Peachy Assignment" 
 * developed by Arturo Gonzalez Escribano  (Universidad de Valladolid 2021/2022)
 */
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ForkJoinPool;

class MonteCarloMinimizationParallel {
	static final boolean DEBUG = false;

	static long startTime = 0;
	static long endTime = 0;
	public static ConcurrentLinkedDeque<PairedThreads> Values;

	// timers - note milliseconds
	private static void tick() {
		startTime = System.currentTimeMillis();
	}

	private static void tock() {
		endTime = System.currentTimeMillis();
	}

	public static void main(String[] args) {
		Values = new ConcurrentLinkedDeque<PairedThreads>();

		try {
			int rows, columns; // grid size
			double xmin, xmax, ymin, ymax; // x and y terrain limits
			TerrainArea terrain; // object to store the heights and grid points visited by searches
			double searches_density; // Density - number of Monte Carlo searches per grid position - usually less
										// than 1!

			int num_searches; // Number of searches
			Search[] searches; // Array of searches
			Random rand = new Random(); // the random number generator

			// if (args.length != 7) {
			// System.out.println("Incorrect number of command line arguments provided.");
			// System.exit(0);
			// }
			/* Read argument values */
			rows = 6000;
			columns = 6000;
			xmin = -10000.0;
			xmax = 20000.0;
			ymin = -50000.0;
			ymax = 60000.0;
			searches_density = 0.6;

			if (DEBUG) {
				/* Print arguments */
				System.out.printf("Arguments, Rows: %d, Columns: %d\n", rows, columns);
				System.out.printf("Arguments, x_range: ( %f, %f ), y_range( %f, %f )\n", xmin, xmax, ymin, ymax);
				System.out.printf("Arguments, searches_density: %f\n", searches_density);
				System.out.printf("\n");
			}

			// Initialize
			terrain = new TerrainArea(rows, columns, xmin, xmax, ymin, ymax);
			num_searches = (int) (rows * columns * searches_density);
			searches = new Search[num_searches];
			for (int i = 0; i < num_searches; i++)
				searches[i] = new Search(i + 1, rand.nextInt(rows), rand.nextInt(columns), terrain);

			if (DEBUG) {
				/* Print initial values */
				System.out.printf("Number searches: %d\n", num_searches);
				// terrain.print_heights();
			}

			// start timer
			tick();

			// all searches
			int min = Integer.MAX_VALUE;
			// int local_min = Integer.MAX_VALUE;
			int finder = -1;

			ForkJoinPool pool = new ForkJoinPool();
			ParallelThreads para = new ParallelThreads(searches, 0, num_searches, Values, num_searches);
			pool.invoke(para);

			for (PairedThreads z : Values) {
				if (z.min < min) {
					min = z.min;
					finder = z.index;
				}
			}

			// end timer
			tock();

			if (DEBUG) {
				/* print final state */
				terrain.print_heights();
				terrain.print_visited();
			}

			System.out.printf("Run parameters\n");
			System.out.printf("\t Rows: %d, Columns: %d\n", rows, columns);
			System.out.printf("\t x: [%f, %f], y: [%f, %f]\n", xmin, xmax, ymin, ymax);
			System.out.printf("\t Search density: %f (%d searches)\n", searches_density, num_searches);

			/* Total computation time */
			System.out.printf("Time: %d ms\n", endTime - startTime);
			int tmp = terrain.getGrid_points_visited();
			System.out.printf("Grid points visited: %d  (%2.0f%s)\n", tmp, (tmp / (rows * columns * 1.0)) * 100.0, "%");
			tmp = terrain.getGrid_points_evaluated();
			System.out.printf("Grid points evaluated: %d  (%2.0f%s)\n", tmp, (tmp / (rows * columns * 1.0)) * 100.0,
					"%");

			/* Results */
			System.out.printf("Global minimum: %d at x=%.1f y=%.1f\n\n", min,
					terrain.getXcoord(searches[finder].getPos_row()), terrain.getYcoord(searches[finder].getPos_col()));

		} catch (Exception e) {
			e.printStackTrace();
			;
		}
	}

}