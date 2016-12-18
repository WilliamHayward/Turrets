package thisisxanderh.turrets.terrain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

import thisisxanderh.turrets.graphics.SpriteCache;
import thisisxanderh.turrets.graphics.SpriteList;

public class Tile extends Rectangle {
	/**
	 * 
	 */
	Texture texture = SpriteCache.loadSprite(SpriteList.PLACEHOLDER);
	private static final long serialVersionUID = -561878273190361184L;
	public static final int SIZE = 128; // Width and height (in pixels) of all tiles 
	
	public Tile(Rectangle rect) {
		super(rect);
	}

	public Tile(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	public Tile(float x, float y) {
		super(x * SIZE, y * SIZE, SIZE, SIZE);
	}

	public void draw(Batch batch, float alpha) {
		batch.draw(texture, x, y, width, height);
	}
}
