package thisisxanderh.turrets.actors.buildings;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import thisisxanderh.turrets.core.Entity;
import thisisxanderh.turrets.core.GameStage;
import thisisxanderh.turrets.graphics.LayerList;
import thisisxanderh.turrets.graphics.SpriteList;
import thisisxanderh.turrets.terrain.Terrain;
import thisisxanderh.turrets.terrain.Tile;

public abstract class Building extends Entity {
	protected boolean built = false;
	private boolean validPosition = false;
	protected String name;
	private Vector2 prevPosition = new Vector2(0,0);
	public Building(SpriteList texture) {
		super(texture);
	}
	
	public boolean build() {
		if (!validPosition) {
			return false;
		}
		solid = true;
		built = true;
		layer = LayerList.BUILDING;
		return true;
	}

	@Override
	public void draw(Batch batch, float alpha) {

		SpriteBatch spriteBatch = (SpriteBatch) batch;
		Color original = spriteBatch.getColor();
		
		if (!built) {
			if (validPosition) {
				spriteBatch.setColor(Color.GREEN);
			} else {
				spriteBatch.setColor(Color.RED);
			}

		}
		super.draw(spriteBatch, alpha);
		spriteBatch.setColor(original);
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		if (!built) {
			// Only check if valid position when conditions change (Collision checks are a bit costly)
			if (prevPosition.x != getX() || prevPosition.y != getY()) {
				validPosition = validPosition();
			}
		} else {
			
		}
		prevPosition.set(getX(), getY());
	}
	
	protected abstract boolean validPosition();
	
	protected boolean validPosition(Terrain terrain) {		
		GameStage stage = (GameStage) this.getStage();
		
		// Don't place it in the ground
		Rectangle bounds = this.getBounds();
		bounds.width -= 2;
		bounds.height -= 2;
		bounds.x += 1;
		bounds.y += 1;
		
		if (!terrain.overlaps(bounds)) {
			return false;
		}
		
		int tileCount = (int) Math.floor(getWidth() / Tile.SIZE);
		bounds.width = Tile.SIZE - 2;
		for (int i = 0; i < tileCount; i++) {
			// Ensure no overhang for longer buildings
			if (!terrain.overlaps(bounds)) {
				return false;
			}
			bounds.x += Tile.SIZE;
		}
		
		// Two buildings can't occupy the same position
		bounds = this.getBounds();
		for (Building building: stage.getActors(Building.class)) {
			if (building.equals(this)) {
				// It'll always be colliding with itself, ya dingus
				continue;
			}
			if (building.getBounds().overlaps(bounds)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void collided(Entity other) {
		// Do nothing
	}
	
	public String getName() {
		return name;
	}
}
