package thisisxanderh.turrets.actors.enemies;

import thisisxanderh.turrets.graphics.SpriteList;

public class Spider extends Enemy {
	
	public Spider(Emitter parent) {
		super(SpriteList.SPIDER, parent);
		maxHealth = 1;
		health = maxHealth;
	}

}
