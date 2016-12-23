package com.williamhayward.turrets.entities.players;

import com.williamhayward.turrets.entities.States;
import com.williamhayward.turrets.graphics.SpriteCache;
import com.williamhayward.turrets.graphics.SpriteList;

public class Engineer extends Player {
	public Engineer() {
		super(SpriteList.ENGINEER_STANDING);
		this.addState(States.FLYING, SpriteCache.loadSprite(SpriteList.ENGINEER_SHIP));
		
		speed = 8f;
		highDamage = 3f;
		lowDamage = 1f;
	}
}
