
public class PercolationDFSFast extends PercolationDFS{
	
	public PercolationDFSFast(int n) {
		super(n);
	}

	@Override
	protected void updateOnOpen(int row, int col) {
		if (isFull(row,col) == true || (inBounds(row-1,col) && isFull(row-1,col) == true)
				|| (inBounds(row+1,col) && isFull(row+1,col) == true) || 
				(inBounds(row,col-1) && isFull(row,col-1) == true) || 
				(inBounds(row,col+1) && isFull(row,col+1) == true)) {
			myGrid[row][col] = FULL;
			dfs(row-1,col);
			dfs(row+1,col);
			dfs(row,col-1);
			dfs(row,col+1);
		}	
	}
	
	@Override
	public void open(int row, int col) {
		if (!inBounds(row,col))
			throw new IndexOutOfBoundsException("Out of Bounds!");
		else super.open(row, col);
	}
	
	@Override
	public boolean isOpen(int row, int col) {
		if (!inBounds(row,col))
			throw new IndexOutOfBoundsException("Out of Bounds!");
		return super.isOpen(row, col);
	}
	
	@Override
	public boolean isFull(int row, int col) {
		if (!inBounds(row,col)) 
			throw new IndexOutOfBoundsException("Out of Bounds!");
		return super.isFull(row,col);
	}

}
