import java.util.*;

/**
 * Compute statistics on Percolation after performing T independent experiments on an N-by-N grid.
 * Compute 95% confidence interval for the percolation threshold, and  mean and std. deviation
 * Compute and print timings
 * 
 * @author Kevin Wayne
 * @author Jeff Forbes
 * @author Josh Hug
 */

public class PercolationStats {
	public static int RANDOM_SEED = 1234;
	public static Random ourRandom = new Random(RANDOM_SEED);
	private double percThresh[];
	private static double conf = 1.96; // 95% confidence interval
	private double t;
	private double n;
	private ArrayList<int[]> cells;

	/*
	 * will perform T experiments for a NxN grid and store results in appropriate
	 * instance variables for the other methods to simply report the values of T experiments
	 * if N or T are invalid, throw an IllegalArgumentException
	 */
	public PercolationStats(int N, int T){
		if (N <= 0 || T <= 0) throw new IllegalArgumentException("Illegal Arguments!");
		n = N;
		t = T;
		percThresh = new double[T];
		cells = new ArrayList<int[]>();
		for (int i = 0; i < N; i++) { // this creates the coordinate system
			for (int j = 0; j < N; j++) {
				int[] coord = new int[2];
				coord[0] = i; // row
				coord[1] = j; // col
				cells.add(coord); // adds it to arraylist
			}
		}
		for (int i = 0; i < T; i++) {
			ArrayList<int[]> shuffle = new ArrayList<int[]>();
			shuffle = cells;
			Collections.shuffle(shuffle,ourRandom); // shuffles it so that random cells are opened
			IUnionFind finder = new QuickUWPC(N);
			IPercolate perc = new PercolationUF(N,finder);
			int index = 0;
			while (!perc.percolates()) {
				perc.open(cells.get(index)[0], cells.get(index)[1]);
				index++;
			}
			double numSites = perc.numberOfOpenSites();
			percThresh[i] = numSites / (n*n);
		}
	}

	public double mean() {
		return StdStats.mean(percThresh);
	}

	public double stddev() {
		return Math.sqrt(StdStats.var(percThresh));
	}

	public double confidenceLow() {
		return mean() - ((conf * stddev())/Math.sqrt(t));
	}

	public double confidenceHigh() {
		return mean() + ((conf * stddev())/Math.sqrt(t));
	}

	public static void main(String[] args) {
		for (int i = 1; i < 6; i++){
			int NN = 100 * (int) Math.pow(2,i);
			double start = System.nanoTime();
			PercolationStats ps = new PercolationStats(10, NN);
			double end = System.nanoTime();
			double time = (end-start)/1e9; 
			System.out.println("With N = 10 and T = " + NN +", the statistics are as follows:");
			System.out.printf("Mean: %1.4f, StDev: %1.4f, LowerConfidence: %1.4f, HighConfidence: %1.4f, Time: %1.4f \n", ps.mean(), ps.stddev(), ps.confidenceLow(), ps.confidenceHigh(), time);
		}
	}
}
