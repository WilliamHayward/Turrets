package thisisxanderh.turrets.terrain;

public class TileMap {
	private boolean tiles[][];
	public TileMap(int width, int height) {
		tiles = new boolean[width][height];
	}

	public void clear(int x, int y) {
		set(x, y, false);
	}
	
	public void set(int x, int y, boolean value) {
		tiles[x][y] = value;
	}
	
	public boolean get(int x, int y) {
		return tiles[x][y];
	}
	
	public int getWidth() {
		return tiles.length;
	}
	
	public int getHeight() {
		if (getWidth() > 0) {
			return tiles[0].length;
		}
		return 0;
	}
}
