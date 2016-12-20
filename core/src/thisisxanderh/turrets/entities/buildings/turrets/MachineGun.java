package thisisxanderh.turrets.entities.buildings.turrets;

import thisisxanderh.turrets.entities.players.Player;
import thisisxanderh.turrets.graphics.SpriteCache;
import thisisxanderh.turrets.graphics.SpriteList;

public class MachineGun extends Turret {

	public MachineGun(Player owner) {
		super(owner, SpriteList.BASE_MACHINE_GUN);
		barrel = SpriteCache.loadSprite(SpriteList.BARREL_MACHINE_GUN);
		cooldown = 0.2f;
		name = "Machine Gun";

		this.cost = 20;
	}

	@Override
	protected void reload() {
		bullet = new QuickBullet(this);
	}

}
