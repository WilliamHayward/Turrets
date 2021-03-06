package com.williamhayward.turrets.entities.players;

import com.williamhayward.turrets.entities.States;
import com.williamhayward.turrets.graphics.SpriteCache;
import com.williamhayward.turrets.graphics.SpriteList;

public class Pilot extends Player {
	public Pilot() {
		super(SpriteList.PILOT_STANDING);

		this.addState(States.FLYING, SpriteCache.loadSprite(SpriteList.PILOT_SHIP));
		

		speed = 8f;
		highDamage = 3f;
		lowDamage = 1f;
	}
}
