package thisisxanderh.turrets.actors.buildings.turrets;

import thisisxanderh.turrets.graphics.SpriteCache;
import thisisxanderh.turrets.graphics.SpriteList;

public class MachineGun extends Turret {

	public MachineGun() {
		super(SpriteList.BASE_MACHINE_GUN);
		barrel = SpriteCache.loadSprite(SpriteList.BARREL_MACHINE_GUN);
		cooldown = 0.2f;
		name = "Machine Gun";
		//this.setSize(Tile.SIZE, Tile.SIZE / 0.5f);
	}

}
