/**
 * Simulate a system to see its Percolation Threshold, but use a UnionFind
 * implementation to determine whether simulation occurs. The main idea is that
 * initially all cells of a simulated grid are each part of their own set so
 * that there will be n^2 sets in an nxn simulated grid. Finding an open cell
 * will connect the cell being marked to its neighbors --- this means that the
 * set in which the open cell is 'found' will be unioned with the sets of each
 * neighboring cell. The union/find implementation supports the 'find' and
 * 'union' typical of UF algorithms.
 * <P>
 * 
 * @author Owen Astrachan
 * @author Jeff Forbes
 *
 */

public class PercolationUF implements IPercolate {
	private final int OUT_BOUNDS = -1;
	private boolean[][] myGrid;
	private IUnionFind myFinder;
	private int myOpenCount;
	private final int VTOP;
	private final int VBOTTOM;

	/**
	 * Constructs a Percolation object for a nxn grid that that creates
	 * a IUnionFind object to determine whether cells are full
	 */

	public PercolationUF(int size, IUnionFind finder) {
		// TODO complete PercolationUF constructor
		if (size <= 0) throw new IllegalArgumentException("Invalid size input!");
		myGrid = new boolean[size][size];
		myFinder = finder;
		myFinder.initialize((size*size) + 2);
		myOpenCount = 0;
		VTOP = size*size;
		VBOTTOM = (size*size) + 1;

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				myGrid[i][j] = false;
			}
		}
	}

	/**
	 * Return an index that uniquely identifies (row,col), typically an index
	 * based on row-major ordering of cells in a two-dimensional grid. However,
	 * if (row,col) is out-of-bounds, return OUT_BOUNDS.
	 */
	private int getIndex(int row, int col) {
		// TODO complete getIndex
		if (row >= myGrid.length || row < 0 || col < 0 || col >= myGrid[0].length )
			return OUT_BOUNDS;
		return (row*myGrid[0].length) + col;
	}

	public void open(int i, int j) {
		// TODO complete open
		if (getIndex(i,j) == OUT_BOUNDS) throw new IndexOutOfBoundsException("Out of Bounds!");
		if (myGrid[i][j] == true) return;
		myOpenCount += 1;
		myGrid[i][j] = true;
		updateOnOpen(i,j);
	}

	public boolean isOpen(int i, int j) {
		// TODO complete isOpen
		if (getIndex(i,j) == OUT_BOUNDS) throw new IndexOutOfBoundsException("Out of Bounds!");		
		return myGrid[i][j] == true;
	}

	public boolean isFull(int i, int j) {
		// TODO complete isFull
		if (getIndex(i,j) == OUT_BOUNDS) throw new IndexOutOfBoundsException("Out of Bounds!");
		return myFinder.connected(VTOP, getIndex(i,j));
	}

	public int numberOfOpenSites() {
		// TODO return the number of calls to open new sites
		return myOpenCount;
	}

	public boolean percolates() {
		// TODO complete percolates
		if (myFinder.connected(VTOP,VBOTTOM)) return true;
		return false;
	}

	/**
	 * Connect new site (row, col) to all adjacent open sites
	 */
	private void updateOnOpen(int row, int col) {
		if (getIndex(row,col) != OUT_BOUNDS) {
			if (getIndex(row-1,col) != OUT_BOUNDS && isOpen(row-1,col) == true) 
				myFinder.union(getIndex(row,col), getIndex(row-1,col));
			if (getIndex(row+1,col) != OUT_BOUNDS && isOpen(row+1,col) == true) 
				myFinder.union(getIndex(row,col), getIndex(row+1,col));
			if (getIndex(row,col-1) != OUT_BOUNDS && isOpen(row,col-1) == true) 
				myFinder.union(getIndex(row,col), getIndex(row,col-1));
			if (getIndex(row,col+1) != OUT_BOUNDS && isOpen(row,col+1) == true) 
				myFinder.union(getIndex(row,col), getIndex(row,col+1));
			if (row == 0) myFinder.union(VTOP,getIndex(row,col));
			if (row == myGrid.length-1) myFinder.union(getIndex(row,col),VBOTTOM);
		}
		else
			throw new IndexOutOfBoundsException("Out of Bounds!");
	}

}
