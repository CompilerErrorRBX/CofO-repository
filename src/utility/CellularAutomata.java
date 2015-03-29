package utility;

import java.util.ArrayList;
import java.util.Random;

public class CellularAutomata {
	public Cell[][] cellMap;
	public ArrayList<Cell> caverns = new ArrayList<Cell>();
	private int width, height;
	private float aliveChance;
	
	public CellularAutomata(int x, int y, float aliveChance, Random random) {
		width = x;
		height = y;
		this.aliveChance = aliveChance;
		cellMap = new Cell[x][y];
		initializeMap(cellMap, random);
		for (int i = 0; i < 20; i++) {
			cellMap = doSimulationStep(cellMap);
		}
		for (int i = 0; i < cellMap.length; i++) {
			for (int j = 0; j < cellMap[0].length; j++) {
				if (cellMap[i][j].value == 0) {
					caverns.add(new Cell(i, j, 0));
				}
			}
		}
	}
	
	public Cell[][] getCells() {
		return cellMap;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Cell[][] initializeMap(Cell[][] map, Random random) {
		int counter = 0;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				//System.out.println(random.nextFloat());
				if (random.nextFloat() < aliveChance) {
					map[x][y] = new Cell(x, y, 1);
				} else map[x][y] = new Cell(x, y, 0);
			}
		}
		return map;
	}

	public Cell[][] doSimulationStep(Cell[][] oldMap) {
		Cell[][] newMap = oldMap.clone();
		for (int x = 0; x < oldMap.length; x++) {
			for (int y = 0; y < oldMap[0].length; y++) {
				int nbs = getAliveNeighbours(oldMap, x, y, 1).size();
				if (oldMap[x][y].value != 0) {
					if (nbs < 3) {
						newMap[x][y].value = 0;
					} else 
						newMap[x][y].value = 1;
				} else {
					if (nbs > 4)
						newMap[x][y].value = 1;
					else {
						newMap[x][y].value = 0;
					}
				}
			}
		}
		return newMap;
	}

	public ArrayList<int[]> getAliveNeighbours(Cell[][] map, int x, int y, int distance) {
		ArrayList<int[]> cells = new ArrayList<int[]>();
		for (int i = -(distance); i < (distance+1); i++) {
			for (int j = -(distance); j < (distance+1); j++) {
				int neighbor_x = x + i;
				int neighbor_y = y + j;
				if (neighbor_x < 0 || neighbor_y < 0
						|| neighbor_x >= map.length
						|| neighbor_y >= map[0].length) {
					cells.add(new int[]{x+i, y+j});
				} else if (map[neighbor_x][neighbor_y].value > 0) {
					cells.add(new int[]{x+i, y+j});
				}
			}
		}
		return cells;
	}
	
	public ArrayList<Cell> getFloorNeighbours(Cell[][] map, int x, int y, int distance, boolean allowDiagonal) {
		ArrayList<Cell> cells = new ArrayList<Cell>();
		for (int i = -(distance); i < (distance+1); i++) {
			for (int j = -(distance); j < (distance+1); j++) {
				if (!allowDiagonal && (i != 0) && (j != 0)) {
					continue;
				}
				int neighbor_x = x + i;
				int neighbor_y = y + j;
				if (neighbor_x < 0 || neighbor_y < 0
						|| neighbor_x >= map.length
						|| neighbor_y >= map[0].length) {
				} else if (map[neighbor_x][neighbor_y].value == 0) {
					cells.add(new Cell(x+i, y+j, 0));
				}
			}
		}
		return cells;
	}
}
