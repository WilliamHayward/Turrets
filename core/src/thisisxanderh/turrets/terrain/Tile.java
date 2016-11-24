package thisisxanderh.turrets.terrain;

import com.badlogic.gdx.math.Rectangle;

public class Tile extends Rectangle {
	/**
	 * 
	 */
	private static final long serialVersionUID = -561878273190361184L;
	public static final int SIZE = 128; // Width and height (in pixels) of all tiles 
	
	public Tile() {
	}

	public Tile(Rectangle rect) {
		super(rect);
	}

	public Tile(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	public Tile(float x, float y) {
		super(x * SIZE, y * SIZE, SIZE, SIZE);
	}

}
