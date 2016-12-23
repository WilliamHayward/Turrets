package com.williamhayward.turrets.entities.enemies;

import com.williamhayward.turrets.core.commands.Commander;
import com.williamhayward.turrets.graphics.SpriteList;

public class Spider extends Enemy {
	
	public Spider(Commander parent) {
		super(SpriteList.SPIDER, parent);
		this.getSprite().setFrameDuration(0.2f);;
		maxHealth = 1;
		health = maxHealth;
		speed = 15f;
		bounty = 5;
	}
}
