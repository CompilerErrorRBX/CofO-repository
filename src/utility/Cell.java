package utility;

public class Cell implements Comparable {
	public int value = 0;
	public int x = 0;
	public int y = 0;
	public float cost = Float.POSITIVE_INFINITY;
	public float heuristic = Float.POSITIVE_INFINITY;
	private Cell nearestCell = null;
	public Cell(int x, int y, int value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}
	public int[] getPosition() {
		return new int[]{x, y};
	}
	public Cell getNearestCell() {
		return nearestCell;
	}
	public void setNearestCell(Cell pNearestCell) {
		this.nearestCell = pNearestCell;
	}
	public int compareTo(Object other) {
		Cell o = (Cell) other;
		
		float f = heuristic + cost;
		float of = o.heuristic + o.cost;
		
		if (f < of) {
			return -1;
		} else if (f > of) {
			return 1;
		} else {
			return 0;
		}
	}
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
