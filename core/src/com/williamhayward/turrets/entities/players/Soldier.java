package com.williamhayward.turrets.entities.players;

import com.williamhayward.turrets.entities.States;
import com.williamhayward.turrets.graphics.SpriteCache;
import com.williamhayward.turrets.graphics.SpriteList;

public class Soldier extends Player {
	public Soldier() {
		super(SpriteList.SOLDIER_STANDING);
		this.addState(States.FLYING, SpriteCache.loadSprite(SpriteList.HERO_SHIP));

		speed = 7f;
		highDamage = 8f;
		lowDamage = 4f;
	}
}
