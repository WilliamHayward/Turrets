package thisisxanderh.turrets.actors.buildings;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import thisisxanderh.turrets.core.GameActor;
import thisisxanderh.turrets.core.GameStage;
import thisisxanderh.turrets.graphics.SpriteList;
import thisisxanderh.turrets.terrain.Terrain;

public abstract class Building extends GameActor {
	protected boolean built = false;
	private boolean validPosition = false;
	protected String name;
	public Building(SpriteList texture) {
		super(texture);
	}
	
	public boolean build() {
		//x = (float) Math.floor(x / Tile.SIZE);
		//y = (float) Math.floor(y / Tile.SIZE);
		if (!validPosition) {
			return false;
		}
		solid = true;
		built = true;
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
			validPosition = validPosition();
		} else {
			
		}
	}
	
	protected abstract boolean validPosition();
	
	protected boolean validPosition(float yBoundShift) {
		GameStage stage = (GameStage) this.getStage();
		Terrain terrain = stage.getTerrain();
		Rectangle bounds = this.getBounds();
		if (terrain.overlaps(bounds)) {
			return false;
		}
		bounds.setY(bounds.getY() + yBoundShift);
		if (!terrain.overlaps(bounds)) {
			return false;
		}
		bounds = this.getBounds();
		for (Building building: stage.getBuildings()) {
			if (building.equals(this)) {
				continue;
			}
			if (building.getBounds().overlaps(bounds)) {
				return false;
			}
		}
		return true;
	}
	
	public String getName() {
		return name;
	}
}
