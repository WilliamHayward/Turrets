package thisisxanderh.turrets.entities.buildings.turrets;

import thisisxanderh.turrets.graphics.SpriteList;
import thisisxanderh.turrets.terrain.Tile;

public class BigBullet extends Bullet {

	public BigBullet() {
		super(SpriteList.PLACEHOLDER);
		speed = 5f;
		damage = 5f;
		this.setSize(Tile.SIZE, Tile.SIZE);
	}

}
