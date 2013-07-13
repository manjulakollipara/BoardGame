package bean;

public class Coordinates {

	int x;
	int y;
	int parent;
	boolean visited;
	
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * @return the parent
	 */
	public int getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(int parent) {
		this.parent = parent;
	}
	/**
	 * @return the visited
	 */
	public boolean isVisited() {
		return visited;
	}
	/**
	 * @param visited the visited to set
	 */
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	public Coordinates(int x, int y, int parent, boolean visited) {
		super();
		this.x = x;
		this.y = y;
		this.parent = parent;
		this.visited = visited;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Coordinates [x=" + x + ", y=" + y + ", parent=" + parent
				+ ", visited=" + visited + "]";
	}
	
	
	
	
}
