package com.williamhayward.turrets.terrain;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.williamhayward.turrets.graphics.Sprite;
import com.williamhayward.turrets.graphics.SpriteCache;
import com.williamhayward.turrets.graphics.SpriteList;

public class Tile extends Rectangle {
	/**
	 * 
	 */
	Sprite sprite = SpriteCache.loadSprite(SpriteList.PLACEHOLDER);
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
		batch.draw(sprite.getFrame(), x, y, width, height);
	}
	
	public void growRight() {
		this.setWidth(getWidth() + SIZE);
	}
	
	public void growDown() {
		this.setHeight(getHeight() + SIZE); 
	}
}
