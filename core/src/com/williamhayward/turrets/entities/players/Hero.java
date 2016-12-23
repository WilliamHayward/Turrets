package com.williamhayward.turrets.entities.players;

import com.williamhayward.turrets.entities.States;
import com.williamhayward.turrets.graphics.SpriteCache;
import com.williamhayward.turrets.graphics.SpriteList;

public class Hero extends Player {

	public Hero() {
		super(SpriteList.HERO_STANDING);
		this.addState(States.FLYING, SpriteCache.loadSprite(SpriteList.HERO_SHIP));
		this.addState(States.WALKING, SpriteCache.loadSprite(SpriteList.HERO_WALKING));
		this.addState(States.JUMPING, SpriteCache.loadSprite(SpriteList.HERO_JUMPING));
		this.addState(States.POUNDING, SpriteCache.loadSprite(SpriteList.HERO_POUNDING));
		
		speed = 10f;
		highDamage = 5f;
		lowDamage = 2f;
	}

}
