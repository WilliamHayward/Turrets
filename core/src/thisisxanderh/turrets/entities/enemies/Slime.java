package thisisxanderh.turrets.entities.enemies;

import thisisxanderh.turrets.core.commands.Commander;
import thisisxanderh.turrets.graphics.SpriteList;

public class Slime extends Enemy {

	public Slime(Commander parent) {
		super(SpriteList.SLIME, parent);
		this.applyModifier(EnemyModifiers.GIANT);
		maxHealth = 2;
		health = maxHealth;
		speed = 10f;
		bounty = 5;
	}

}
