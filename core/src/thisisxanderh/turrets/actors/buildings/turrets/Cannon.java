package thisisxanderh.turrets.actors.buildings.turrets;

import thisisxanderh.turrets.actors.enemies.Enemy;
import thisisxanderh.turrets.graphics.SpriteCache;
import thisisxanderh.turrets.graphics.SpriteList;

public class Cannon extends Turret {

	public Cannon() {
		super(SpriteList.BASE_CANNON);
		barrel = SpriteCache.loadSprite(SpriteList.BARREL_CANNON);
		cooldown = 2f;
		name = "Cannon";
		//this.setSize(Tile.SIZE * 2f, Tile.SIZE);
	}

	@Override
	protected void reload() {
		bullet = new BigBullet();
	}

}
