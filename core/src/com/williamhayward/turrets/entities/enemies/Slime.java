package com.williamhayward.turrets.entities.enemies;

import com.williamhayward.turrets.core.commands.Commander;
import com.williamhayward.turrets.entities.Modifiers;
import com.williamhayward.turrets.graphics.SpriteList;

public class Slime extends Enemy {

	public Slime(Commander parent) {
		super(SpriteList.SLIME, parent);
		this.applyModifier(Modifiers.GIANT);
		maxHealth = 2;
		health = maxHealth;
		speed = 10f;
		bounty = 5;
	}

}
