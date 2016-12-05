package thisisxanderh.turrets.actors.buildings;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import thisisxanderh.turrets.core.Coordinate;
import thisisxanderh.turrets.core.GameActor;
import thisisxanderh.turrets.core.GameStage;
import thisisxanderh.turrets.graphics.LayerList;
import thisisxanderh.turrets.graphics.SpriteList;
import thisisxanderh.turrets.terrain.Terrain;
import thisisxanderh.turrets.terrain.Tile;

public abstract class Building extends GameActor {
	protected boolean built = false;
	private boolean validPosition = false;
	protected String name;
	private Coordinate prevPosition = new Coordinate(0,0);
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
			if (prevPosition.getX() != getX() || prevPosition.getY() != getY()) {
				validPosition = validPosition();
			}
		} else {
			
		}
		prevPosition.set(getX(), getY());
	}
	
	protected abstract boolean validPosition();
	
	protected boolean validPosition(float yBoundShift) {
		GameStage stage = (GameStage) this.getStage();
		
		// Don't place it in the ground
		Terrain terrain = stage.getTerrain();
		Rectangle bounds = this.getBounds();
		if (terrain.overlaps(bounds)) {
			return false;
		}
		
		// Check that it's attached the something
		bounds.setY(bounds.getY() + yBoundShift);
		if (!terrain.overlaps(bounds)) {
			return false;
		}
		
		for (int i = 0; i < Math.floor(getWidth() / Tile.SIZE); i++) {
			// Ensure no overhang for longer buildings
			bounds = new Rectangle(getX() + i * Tile.SIZE, getY() + yBoundShift, Tile.SIZE, getWidth());
			if (!terrain.overlaps(bounds)) {
				return false;
			}
		}
		
		// Two buildings can't occupy the same position
		bounds = this.getBounds();
		for (Building building: stage.getBuildings()) {
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
	public void collided(GameActor other) {
		// Do nothing
	}
	
	public String getName() {
		return name;
	}
}
