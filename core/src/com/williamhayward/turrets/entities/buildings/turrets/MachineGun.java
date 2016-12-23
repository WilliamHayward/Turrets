package com.williamhayward.turrets.entities.buildings.turrets;

import com.williamhayward.turrets.entities.players.Player;
import com.williamhayward.turrets.graphics.SpriteCache;
import com.williamhayward.turrets.graphics.SpriteList;

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
