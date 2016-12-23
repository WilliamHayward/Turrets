package com.williamhayward.turrets.entities.buildings.turrets;

import com.williamhayward.turrets.graphics.SpriteList;
import com.williamhayward.turrets.terrain.Tile;

public class BigBullet extends Bullet {

	public BigBullet(Turret parent) {
		super(parent, SpriteList.PLACEHOLDER);
		speed = 5f;
		damage = 5f;
		this.setSize(Tile.SIZE, Tile.SIZE);
	}

}
