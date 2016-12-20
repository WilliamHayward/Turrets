package thisisxanderh.turrets.entities.enemies;

import thisisxanderh.turrets.core.commands.Commander;
import thisisxanderh.turrets.graphics.SpriteList;

public class Spider extends Enemy {
	
	public Spider(Commander parent) {
		super(SpriteList.SPIDER, parent);
		this.getSprite().setFrameDuration(0.2f);;
		maxHealth = 1;
		health = maxHealth;
		speed = 15f;
	}
}
