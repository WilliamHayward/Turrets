package com.williamhayward.turrets.entities.buildings.turrets;

import com.williamhayward.turrets.graphics.SpriteList;

public class QuickBullet extends Bullet {

	public QuickBullet(Turret parent) {
		super(parent, SpriteList.PLACEHOLDER);
		speed = 25f;
		damage = 0.3f;
	}

}
