package com.williamhayward.turrets.entities.buildings.turrets;

import com.williamhayward.turrets.entities.players.Player;
import com.williamhayward.turrets.graphics.SpriteCache;
import com.williamhayward.turrets.graphics.SpriteList;

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
