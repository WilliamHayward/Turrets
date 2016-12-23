package com.williamhayward.turrets.entities.enemies;

import com.williamhayward.turrets.core.commands.Commander;
import com.williamhayward.turrets.entities.Modifiers;
import com.williamhayward.turrets.graphics.SpriteList;

public class Fly extends Enemy {
	// TODO: Creating FlyingEnemy superclass to allow for more enemies like this
	
	public Fly(Commander parent) {
		super(SpriteList.FLY, parent);
		this.applyModifier(Modifiers.GIANT);
		maxHealth = 1;
		health = maxHealth;
		speed = 15f;
		bounty = 5;
	}

}
