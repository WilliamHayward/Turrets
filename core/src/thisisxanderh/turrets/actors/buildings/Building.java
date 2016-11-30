package thisisxanderh.turrets.actors.buildings;

import com.badlogic.gdx.graphics.g2d.Batch;

import thisisxanderh.turrets.actors.GameActor;
import thisisxanderh.turrets.terrain.Tile;

public abstract class Building extends GameActor {
	private boolean built = false;
	public Building() {
		super();
	}
	
	public void build(float x, float y) {
		x = (float) Math.floor(x / Tile.SIZE);
		y = (float) Math.floor(y / Tile.SIZE);
		this.setPosition(x, y);
		built = true;
	}

	@Override
	public void draw(Batch batch, float alpha) {
		if (!built) {
			alpha /= 2;
		}
		super.draw(batch, alpha);
	}
}
