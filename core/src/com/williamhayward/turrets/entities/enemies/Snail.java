package com.williamhayward.turrets.entities.enemies;

import com.williamhayward.turrets.core.commands.Commander;
import com.williamhayward.turrets.entities.Modifiers;
import com.williamhayward.turrets.entities.States;
import com.williamhayward.turrets.graphics.SpriteList;

public class Snail extends Enemy {
	public float hideTimer = 0;
	public Snail(Commander parent) {
		super(SpriteList.SNAIL, parent);
		this.applyModifier(Modifiers.GIANT);
		maxHealth = 1;
		health = maxHealth;
		speed = 2f;
		bounty = 10;
		this.addState(States.INVINCIBLE, SpriteList.SNAIL_HIDING);
	}
	
	@Override
	public void act(float delta) {
		hideTimer -= delta;
		if (hideTimer < 0) {
			if(this.isState(States.INVINCIBLE)) {
				this.setState(States.DEFAULT);
			}
			super.act(delta);
		}
	}
	
	@Override
	public void bop(float damage) {
		this.setState(States.INVINCIBLE);
		hideTimer = 1f;
		resize();
	}

}
