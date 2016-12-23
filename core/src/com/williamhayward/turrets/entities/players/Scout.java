package com.williamhayward.turrets.entities.players;

import com.williamhayward.turrets.entities.States;
import com.williamhayward.turrets.graphics.SpriteCache;
import com.williamhayward.turrets.graphics.SpriteList;

public class Scout extends Player {
	public Scout() {
		super(SpriteList.SCOUT_STANDING);
		this.addState(States.FLYING, SpriteCache.loadSprite(SpriteList.SCOUT_SHIP));
		
		speed = 15f;
		highDamage = 2f;
		lowDamage = 0.5f;
	}
}
