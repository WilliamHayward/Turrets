package thisisxanderh.turrets.actors.enemies;

import thisisxanderh.turrets.graphics.SpriteList;

public class Creep extends Enemy {

	public Creep(Emitter parent) {
		super(SpriteList.SPIDER_GIANT, parent);
		maxHealth = 4;
		health = maxHealth;
	}

}
