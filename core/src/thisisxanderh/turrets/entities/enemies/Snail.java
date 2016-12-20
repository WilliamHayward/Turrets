package thisisxanderh.turrets.entities.enemies;

import thisisxanderh.turrets.core.commands.Commander;
import thisisxanderh.turrets.graphics.SpriteList;

public class Snail extends Enemy {

	public Snail(Commander parent) {
		super(SpriteList.SNAIL, parent);
		this.applyModifier(EnemyModifiers.GIANT);
		maxHealth = 1;
		health = maxHealth;
		speed = 2f;
		bounty = 10;
	}

}
