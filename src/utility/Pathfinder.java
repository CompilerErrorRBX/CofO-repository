package utility;

import java.util.ArrayList;

public class Pathfinder {
	boolean allowDiagonalMovement = true;
	Cell[][] cells;
	ArrayList<Cell> path = new ArrayList<Cell>();
	ArrayList<Cell> openCells = new ArrayList<Cell>();
	ArrayList<Cell> closedCells = new ArrayList<Cell>();
	public ArrayList<Cell> findPath(int[][] map, int[] from, int[] to) {
		cells = new Cell[map.length][map[0].length];
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[0].length; y++) {
				cells[x][y] = new Cell(x, y, map[x][y]);
			}
		}
		if (to[0] > cells.length || to[1] > cells[0].length) return null;
		path.clear();
		openCells.clear();
		closedCells.clear();
		
		Cell startCell = cells[from[0]][from[1]]; 
		Cell endCell = cells[to[0]][to[1]];
		startCell.cost = (float) Math.sqrt(Math.pow(startCell.x-endCell.x, 2) + Math.pow(startCell.y-endCell.y, 2));
		
		openCells.add(startCell);
		
		while (openCells.size() > 0) {
			Cell current = openCells.get(0);

			if (current == endCell) break;
			
			openCells.remove(current);
			closedCells.add(current);

			for (int x = -1; x < 2; x++) {
				for (int y = -1; y < 2; y++) {
					if ((x == 0) && (y == 0)) {
						continue;
					}
					 
					if (!allowDiagonalMovement && (x != 0) && (y != 0)) {
						continue;
					}
					
					int pX = x + current.x;
					int pY = y + current.y;
					// Checking if the cell is a corner from current - x and y must be odd.
					if (isValidLocation(pX, pY) && cells[pX][pY].value == 0) {
						boolean isNeighborObstructed = ((x != 0) && (y != 0)) && cells[current.x][pY].value == 1 && cells[pX][current.y].value == 1;
						if (isNeighborObstructed) continue;
						Cell neighbor = cells[pX][pY];
						float distance = (float) Math.sqrt(Math.pow(neighbor.x-current.x, 2) + Math.pow(neighbor.y-current.y, 2));
						float stepCost = current.cost + distance;
						if (stepCost < neighbor.cost) {
							if (openCells.contains(neighbor)) openCells.remove(neighbor);
							if (closedCells.contains(neighbor)) closedCells.remove(neighbor);
						}
						if (!openCells.contains(neighbor) && (!closedCells.contains(neighbor))) {
							neighbor.cost = stepCost;
							neighbor.heuristic = (float) Math.sqrt(Math.pow(neighbor.x-endCell.x, 2) + Math.pow(neighbor.y-endCell.y, 2));
							openCells.add(neighbor);
							neighbor.setNearestCell(current);
						}
						
					}
				}
			}
		}
		
		if (endCell.getNearestCell() == null) {
			return null;
		}
		
		Cell lastCell = endCell;
		while (lastCell != startCell) {
			path.add(0, lastCell);
			lastCell = lastCell.getNearestCell();
		}
		path.add(0, startCell);
		
		return path;
	}
	
	protected boolean isValidLocation(int x, int y) {
		boolean invalid = (x < 0) || (y < 0) || (x >= cells.length) || (y >= cells[0].length);
		return !invalid;
	}
	
	public boolean findInArrayList(ArrayList<?> list, Object obj) {
		for (Object index : list) {
			if (index == obj) return true;
		}
		return false;
	}
}
