package thisisxanderh.turrets.entities.buildings.turrets;

import thisisxanderh.turrets.entities.players.Player;
import thisisxanderh.turrets.graphics.SpriteCache;
import thisisxanderh.turrets.graphics.SpriteList;

public class Cannon extends Turret {

	public Cannon(Player owner) {
		super(owner, SpriteList.BASE_CANNON);
		barrel = SpriteCache.loadSprite(SpriteList.BARREL_CANNON);
		cooldown = 2f;
		name = "Cannon";
		
		this.cost = 30;
	}

	@Override
	protected void reload() {
		bullet = new BigBullet(this);
	}

}
