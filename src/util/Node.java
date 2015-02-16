package util;


public class Node {
	public int x = 0;
	public int y = 0;
	public Vector2i position;
	public int placementInFractal = 0;
	public Node(int pX, int pY, int placement) {
		x = pX;
		y = pY;
		position = new Vector2i(x, y);
		placementInFractal = placement;
	}
}
