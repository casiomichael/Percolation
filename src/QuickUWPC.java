
public class QuickUWPC implements IUnionFind{
	private int[] parent; // parent[i] = parent of i
	private int[] size; // size[i] = number of site in subtree rooted at i
	private int count; // number of components
	
	/**
	 * Default Constructor
	 */
	public QuickUWPC() {
		this(10);
	}
	
	/**
	 * Initializes an empty union-find data structure with {@code n} sites
	 * {@code 0} through {@code n-1}. Each site is initially in its own component.
	 */
	public QuickUWPC(int n) {
		initialize(n);
	}
	
	/**
	 * Initializes an empty union-find data structure with {@code n} sites
	 * {@code 0} through {@code n-1}. Each site is initially in its own component.
	 */
	@Override
	public void initialize(int n) {
		// TODO Auto-generated method stub
		count = n;
		parent = new int[n];
		size = new int[n];
		for (int i = 0; i < n; i++) {
			parent[i] = i;
			size[i] = 1;
		}
	}

	/**
	 * Returns the number of components
	 * 
	 * @return the number of components (between {@code 1} and {@code n})
	 */
	@Override
	public int components() {
		// TODO Auto-generated method stub
		return count;
	}
	
	/**
	 * Returns the component identifier for the component containing site {@code x}.
	 * 
	 * @param x the integer representing one site
	 * @return the component identifier for the component containing site {@code x}
	 * @throws IllegalArgumentException unless {@code 0 <= p < n}
	 */
	@Override
	public int find(int x) {
		// TODO Auto-generated method stub
		if (x < 0 || x >= parent.length) throw new IllegalArgumentException ("Illegal argument!");
		int root = x;
		while (root != parent[root])
			root = parent[root];
		while (x != root) {
			int newParent = parent[x];
			parent[x] = root;
			x = newParent;
		}
		return root;
	}

	@Override
	public boolean connected(int p, int q) {
		// TODO Auto-generated method stub
		return find(p) == find(q);
	}

	@Override
	public void union(int p, int q) {
		// TODO Auto-generated method stub
		int rootP = find(p);
		int rootQ = find(q);
		if (rootP == rootQ) return;
		
		//make smaller root point to larger one
		if (size[rootP] < size[rootQ]) {
			parent[rootP] = rootQ;
			size[rootQ] += size[rootP];
		}
		else {
			parent[rootQ] = rootP;
			size[rootP] += size[rootQ];
		}
		count--;
	}
	

}
